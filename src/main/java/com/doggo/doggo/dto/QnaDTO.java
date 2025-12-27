package com.doggo.doggo.dto;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class QnaDTO {

    private Long id; // DB에서 자동 생성된 ID

    private String memo; // 사용자 메모 평가

    private String satisfaction; // 사용자 라디오 평가

    private String createdAt; // 작성일

    private String updateAt; // 수정일

    public QnaDTO(Long id, String memo, String satisfaction, String createdAt) {
        this.id = id;
        this.memo = memo;
        this.satisfaction = satisfaction;
        this.createdAt = createdAt;

    }
}
