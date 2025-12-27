package com.doggo.doggo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public class TrackingResponseDTO {

    private  Long id;

    private String record;

    private List<String> photoPaths;

    private List<String> tags;

    private String ownerNickname;

    private int dogAge;

    private String dogType;

    private Map<String, List<String>> status;

    private String createdAt;
}
