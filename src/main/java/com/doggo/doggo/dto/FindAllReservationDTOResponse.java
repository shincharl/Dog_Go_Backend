package com.doggo.doggo.dto;

import com.doggo.doggo.entity.Reservation;

public record FindAllReservationDTOResponse(
        Long id, String calender, String clock, String dogType, int dogAge,
        String name, String phone, String location, int distance, String event,
        String status
) {
    public static FindAllReservationDTOResponse fromEntity(Reservation reservation){
        return new FindAllReservationDTOResponse(
                reservation.getId(),
                reservation.getCalender(),
                reservation.getClock(),
                reservation.getDogType(),
                reservation.getDogAge(),
                reservation.getName(),
                reservation.getPhone(),
                reservation.getLocation(),
                reservation.getDistance(),
                reservation.getEvent(),
                reservation.getStatus()
        );
    }
}
