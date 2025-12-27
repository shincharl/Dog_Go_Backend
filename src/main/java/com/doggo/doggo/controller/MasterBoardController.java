package com.doggo.doggo.controller;

import com.doggo.doggo.Service.MemberTodayDateService;
import com.doggo.doggo.dto.QnaDTO;
import com.doggo.doggo.dto.ReservationCountDTO;
import com.doggo.doggo.dto.ReservationResponseDTO;
import com.doggo.doggo.dto.ReservationStatusUpdateDTO;
import com.doggo.doggo.entity.Qna;
import com.doggo.doggo.entity.Reservation;
import com.doggo.doggo.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class MasterBoardController {

    private final MemberTodayDateService memberTodayDateService;

    // Qna는 그냥 받기
    private final QnaRepository qnaRepository;

    // 오늘 예약 개수 알기
    // 만료된 예약 애수 알기 (1달전 데이터는 전부 만료 처리)
    @GetMapping("/masterpage/today")
    public ReservationCountDTO todayReservation(){

        Long todayCount = (long) memberTodayDateService.findTodayReservationMember().size();
        Long expiredCount = (long) memberTodayDateService.getExpiredReservations().size();
        Long eventChoiceMemberCount = (long) memberTodayDateService.getEventAppliedReservations().size();
        Long qnaCount = (long) qnaRepository.findAll().size();

        return new ReservationCountDTO(todayCount, expiredCount, eventChoiceMemberCount, qnaCount);

    }

    @GetMapping("/masterpage/today/list")
    public List<Reservation> getTodayList(){
        return memberTodayDateService.findTodayReservationMember();
    }

    @GetMapping("/masterpage/expired/list")
    public List<Reservation> getExpiredList(){
        return memberTodayDateService.getExpiredReservations();
    }

    @GetMapping("masterpage/event/list")
    public List<Reservation> getEventAppliedList(){
        return memberTodayDateService.getEventAppliedReservations();
    }

    @GetMapping("masterpage/master/qna")
    public List<QnaDTO> getQnaList(){
         return qnaRepository.findAll().stream()
                 .map(q -> new QnaDTO(
                    q.getId(),
                    q.getMemo(),
                    q.getSatisfaction(),
                    q.getCreatedAt() != null
                        ? q.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                        : null
                 )).toList();
    }

    // 예약 상태 변경 코드
    @PutMapping("masterpage/status/{id}")
    public ReservationResponseDTO updateStatus(
            @PathVariable Long id,
            @RequestBody ReservationStatusUpdateDTO dto
            ){

        Reservation reservation = memberTodayDateService.updateStatusService(id, dto);

        return ReservationResponseDTO.fromEntity(reservation);

    }





}
