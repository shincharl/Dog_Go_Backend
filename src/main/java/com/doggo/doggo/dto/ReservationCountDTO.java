package com.doggo.doggo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReservationCountDTO {
    private Long todayCount;
    private Long expiredCount;
    private Long EventChoiceMemberCount;
    private Long qnaCount;
}
