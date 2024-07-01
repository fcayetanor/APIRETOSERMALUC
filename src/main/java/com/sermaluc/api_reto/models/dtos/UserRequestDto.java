package com.sermaluc.api_reto.models.dtos;

import com.sermaluc.api_reto.validation.ValidateEmail;
import com.sermaluc.api_reto.validation.ValidatePassword;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UserRequestDto {

                private String id;
                private String name;
                @NotBlank
                @ValidateEmail
                private String email;
                @NotBlank
                @ValidatePassword
                private String password;
                private List<PhoneDto> phones;
}
