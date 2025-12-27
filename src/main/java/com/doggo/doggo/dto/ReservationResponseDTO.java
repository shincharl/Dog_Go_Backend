package com.doggo.doggo.dto;

import com.doggo.doggo.entity.Reservation;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationResponseDTO {

    private Long id;
    private String name;
    private String dogType;
    private String clock;
    private String status;

    public static ReservationResponseDTO fromEntity(Reservation reservation){
        return ReservationResponseDTO.builder()
                .id(reservation.getId())
                .name(reservation.getName())
                .dogType(reservation.getDogType())
                .clock(reservation.getClock())
                .status(reservation.getStatus())
                .build();
    }
}
