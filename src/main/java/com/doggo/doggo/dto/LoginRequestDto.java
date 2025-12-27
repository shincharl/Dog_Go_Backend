package com.doggo.doggo.dto;

import lombok.*;

// 요청 DTO
@ToString
@Getter
@AllArgsConstructor
public class LoginRequestDto{
    private String email;
    private String password;
}

