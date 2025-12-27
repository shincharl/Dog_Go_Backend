package com.doggo.doggo.dto;

import com.doggo.doggo.entity.Reservation;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationDTO {
    private String selected_Calender;
    private String selected_Clock;
    private String dogType;
    private String password;
    private int dogAge;
    private String name;
    private String phone;
    private String location;
    private int totalDistance;
    private String event;
    private String status;

    public Reservation toEntity() {
        return new Reservation(null, selected_Calender, selected_Clock, dogType, password, dogAge, name, phone, location, totalDistance, event, status);
    }
}
