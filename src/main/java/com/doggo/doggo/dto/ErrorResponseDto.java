package com.doggo.doggo.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ErrorResponseDto {
    private String message;

    public String getMessage(){
        return message;
    }
}
