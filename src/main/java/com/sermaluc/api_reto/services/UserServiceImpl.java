package com.sermaluc.api_reto.services;

import com.sermaluc.api_reto.configuration.JwtService;
import com.sermaluc.api_reto.models.Phone;
import com.sermaluc.api_reto.models.Token;
import com.sermaluc.api_reto.models.User;
import com.sermaluc.api_reto.models.dtos.PhoneDto;
import com.sermaluc.api_reto.models.dtos.UserRequestDto;
import com.sermaluc.api_reto.models.dtos.UserResponseDto;
import com.sermaluc.api_reto.models.enums.TokenType;
import com.sermaluc.api_reto.repositories.PhoneRepository;
import com.sermaluc.api_reto.repositories.TokenRepository;
import com.sermaluc.api_reto.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PhoneRepository phoneRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Autowired
    private ModelMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public Optional<UserResponseDto> findById(String id) {
        return userRepository.findById(id)
                .map( user -> mapper.map(user, UserResponseDto.class));

    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDto> findAll() {
        List<User> users = (List<User>) userRepository.findAll();
        return users.stream()
                .map( user -> mapper.map(user, UserResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Optional<UserResponseDto> save(UserRequestDto requestDto) {

        User user = User.builder()
        .name(requestDto.getName())
        .email(requestDto.getEmail())
        .password(passwordEncoder.encode(requestDto.getPassword()))
        .build();

        user.setInitialValues();


        var jwtToken = jwtService.generateToken( user);
        if (!validateEmail(user.getEmail())){
            return Optional.empty();
        }
        user.setToken(jwtToken);
        User userResult = userRepository.save(user);

        registerPhones(userResult, requestDto.getPhones());
        saveUserToken(userResult, jwtToken);

        return Optional.of(mapper.map(userResult, UserResponseDto.class));

    }


    @Override
    public void deleteById(String id) {
        userRepository.deleteById(id);
    }

    private void registerPhones(User user, List<PhoneDto> phones) {
        phoneRepository.saveAll(phones.stream()
            .map(dto -> {
                Phone phone = mapper.map(dto, Phone.class);
                phone.setUser(user);
                return phone;
            })
            .collect(Collectors.toList()));
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }
    private Boolean validateEmail(String email){
        return userRepository.findByEmail(email).isEmpty();
    }
}
