package com.doggo.doggo.repository;

import com.doggo.doggo.dto.FindAllReservationDTO;
import com.doggo.doggo.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Override
    ArrayList<Reservation> findAll();

    List<FindAllReservationDTO> findAllProjectedBy();

    List<Reservation> findAllByCreatedDateBetween(LocalDateTime start, LocalDateTime end);

    // 한 달 이전 예약
    List<Reservation> findAllByCreatedDateBefore(LocalDateTime dateTime);

    // 이벤트 신청 회원만 조회 (event 컬럼에 값이 있는 경우)
    List<Reservation> findAllByEventIsNotNullAndEventNot(String empty);
}
