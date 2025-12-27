package com.doggo.doggo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class TrackingRequestDTO {

    private Long reservationId;

    private String record;

    private List<String> tags;

    private Map<String, List<String>> status;
}
