package com.doggo.doggo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Qna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String memo;

    @Column
    private String satisfaction;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt; // 생성일자 자동 기록

    @LastModifiedDate
    private LocalDateTime updateAt; // 수정일자 자동 기록
    
    

}
