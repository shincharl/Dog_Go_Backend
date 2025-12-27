package com.doggo.doggo.controller;

import com.doggo.doggo.dto.QnaDTO;
import com.doggo.doggo.entity.Qna;
import com.doggo.doggo.repository.QnaRepository;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
public class QnaController {

    @Autowired
    private QnaRepository qnaRepository;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/api/qna")
    public Qna saveQna(@RequestBody QnaDTO qnaDTO) {

        // DTO -> Entity 자동 매핑
        Qna qna = modelMapper.map(qnaDTO, Qna.class);

        // 리파지터리로 엔티티를 DB에 저장
        Qna saved = qnaRepository.save(qna);

        log.info("저장한 데이터.....");
        log.info(qnaDTO.getMemo() + " ," + qnaDTO.getSatisfaction());

        return qna;
    }
}
