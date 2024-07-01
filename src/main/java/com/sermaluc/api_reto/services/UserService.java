package com.sermaluc.api_reto.services;

import com.sermaluc.api_reto.models.dtos.UserRequestDto;
import com.sermaluc.api_reto.models.dtos.UserResponseDto;

import java.util.Optional;

public interface UserService extends GenericService<UserResponseDto, String> {
    Optional<UserResponseDto> save(UserRequestDto requestDto);

}
