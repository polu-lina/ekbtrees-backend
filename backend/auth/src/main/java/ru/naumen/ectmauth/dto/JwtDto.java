package ru.naumen.ectmauth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class JwtDto {
    private String accessToken;
    private String refreshToken;
}
