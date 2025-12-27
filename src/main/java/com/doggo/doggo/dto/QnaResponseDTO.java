package com.doggo.doggo.dto;

import com.doggo.doggo.entity.Qna;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class QnaResponseDTO {

    private Long id; // QnA ID
    private String memo; // 사용자 메모
    private String satisfaction; // 사용자 만족도 평가
    private String createdAt; // 생성일자
    private String updatedAt; // 수정일자

    public static QnaResponseDTO fromEntity(Qna qna){
        return QnaResponseDTO.builder()
                .id(qna.getId())
                .memo(qna.getMemo())
                .satisfaction(qna.getSatisfaction())
                .createdAt(qna.getCreatedAt() != null ? qna.getCreatedAt().toString() : null)
                .updatedAt(qna.getUpdateAt() != null ? qna.getUpdateAt().toString() : null)
                .build();

    }
}
