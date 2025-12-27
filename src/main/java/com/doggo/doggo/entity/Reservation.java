package com.doggo.doggo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reservation extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String calender;

    @Column
    private String clock;

    @Column
    private String dogType;

    @Column
    private String password;

    @Column
    private int dogAge;

    @Column
    private String name;

    @Column
    private String phone;

    @Column
    private String location;

    @Column
    private int distance;

    @Column
    private String event;

    @Column
    private String status;

    @PrePersist
    public void prePersist() {
        if(this.status == null) {
            this.status = "대기";
        }
    }


}
