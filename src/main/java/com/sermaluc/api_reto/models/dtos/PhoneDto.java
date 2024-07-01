package com.sermaluc.api_reto.models.dtos;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class PhoneDto {

        private String number;
        private String cityCode;
        private String countryCode;
}
