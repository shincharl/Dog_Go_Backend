package com.doggo.doggo.entity;

import com.doggo.doggo.enums.Role;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import jakarta.persistence.*;
import lombok.*;

import java.util.Optional;

@Entity // 해당 클래스가 엔티티임을 선언, 클래스 필드를 바탕으로 DB에 테이블 생성
@Getter // 각 필드 값을 조회할 수 있는 getter 메서드 자동 생성
@Setter // 회원 가입시 입력하는 setter
@ToString // 모든 필드를 출력할 수 있는 toString 메서드 자동 생성
@AllArgsConstructor // 모든 필드를 매개변수로 갖는 생성자 자동 생성
@NoArgsConstructor // 매개변수가 아예없는 기본 생성자 자동 생성
@Builder
public class Member {
    @Id // 대표키 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB가 자동으로 1씩 증가
    private Long id; // 대표키 , 아이디
    @Column(unique = true)
    private String email; // 일반 컬럼 , 비밀번호
    @Column
    private String password;
    @Column
    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;

}