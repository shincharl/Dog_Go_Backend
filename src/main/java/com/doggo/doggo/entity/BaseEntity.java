package com.doggo.doggo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass // 상속받는 엔티티가 이 필드를 컬럼으로 인식하도록 함
@EntityListeners(AuditingEntityListener.class) // 자동으로 시간값 설정
public class BaseEntity {

    @CreatedDate
    @Column(updatable = false) // 수정 불가 (한 번만 설정)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

}
