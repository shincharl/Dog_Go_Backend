package com.doggo.doggo.controller;

import com.doggo.doggo.dto.FindAllReservationDTOResponse;
import com.doggo.doggo.dto.ReservationDTO;
import com.doggo.doggo.entity.Reservation;
import com.doggo.doggo.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class ReservationController {
    @Autowired
    private ReservationRepository reservationRepository;

    @PostMapping("/reservation")
    public String makeReservation(@RequestBody ReservationDTO reservation){
        System.out.println("날짜: " + reservation.getSelected_Calender());
        System.out.println("시간: " + reservation.getSelected_Clock());
        System.out.println("강아지 종류: " + reservation.getDogType());
        System.out.println("비밀번호: " + reservation.getPassword());
        System.out.println("견종 나이: " + reservation.getDogAge());
        System.out.println("예약자 이름: " + reservation.getName());
        System.out.println("전화번호: " + reservation.getPhone());
        System.out.println("위치: " + reservation.getLocation());
        System.out.println("거리: " + reservation.getTotalDistance());
        System.out.println("이벤트: " + reservation.getEvent());
        System.out.println("예약상태: " + reservation.getStatus());

        // 1. DTO를 엔티티로 변환
        Reservation reservations = reservation.toEntity();
        System.out.println(reservations.toString());

        // 2. 리파지터리로 엔티티를 DB에 저장
        Reservation saved = reservationRepository.save(reservations);
        System.out.println(saved.toString());

        return "예약 완료";
    }

    @GetMapping("/allReservation")
    public List<FindAllReservationDTOResponse> getAllReservation(){
        return reservationRepository.findAllProjectedBy()
                .stream()
                .map(p -> new FindAllReservationDTOResponse(
                        p.getId(), p.getCalender(), p.getClock(), p.getDogType(), p.getDogAge(),
                        p.getName(), p.getPhone(), p.getLocation(), p.getDistance(), p.getEvent(),
                        p.getStatus()
                )).toList(); // password 제외후 춫력
    }

    @PostMapping("/reservation/{id}")
    public ResponseEntity<String> checkPassword(@PathVariable Long id, @RequestBody Map<String, String> body){
        
        String password = body.get("password");  // JSON에서 꺼내오기

        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("예약 없음"));

        if(reservation.getPassword().equals(password)){
            return ResponseEntity.ok().body("OK");
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호 불일치");
        }

    }

    @GetMapping("/reservation/{id}")
    public ResponseEntity<FindAllReservationDTOResponse> getReservation(@PathVariable Long id){
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "예약 정보를 찾을 수 없습니다."
                ));

        return ResponseEntity.ok(FindAllReservationDTOResponse.fromEntity(reservation));

    }

    @PutMapping("/reservation/{id}")
    public ResponseEntity<String> modifyReservation(
                    @PathVariable Long id,
                    @RequestBody FindAllReservationDTOResponse dto){

        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new RuntimeException("예약 없음"));

        // DTO 필드로 엔티티 업데이트
        reservation.setCalender(dto.calender());
        reservation.setClock(dto.clock());
        reservation.setDogType(dto.dogType());
        reservation.setDogAge(dto.dogAge());
        reservation.setName(dto.name());
        reservation.setPhone(dto.phone());
        reservation.setLocation(dto.location());
        reservation.setDistance(dto.distance());
        reservation.setEvent(dto.event());

        reservationRepository.save(reservation);

        return ResponseEntity.ok("OK");

    }

    @PostMapping("/reservation/{id}/delete")
    public ResponseEntity<String> deleteReservation(@PathVariable Long id){

        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new RuntimeException("예약 없음"));

        // 해당 id값을 가진 데이터 삭제
        reservationRepository.delete(reservation);

        return ResponseEntity.ok("OK");
    }




}
