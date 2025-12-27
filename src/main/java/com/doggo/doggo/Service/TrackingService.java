package com.doggo.doggo.Service;

import com.doggo.doggo.dto.TrackingRequestDTO;
import com.doggo.doggo.entity.Reservation;
import com.doggo.doggo.entity.Tracking;
import com.doggo.doggo.entity.TrackingPhoto;
import com.doggo.doggo.entity.TrackingTag;
import com.doggo.doggo.repository.ReservationRepository;
import com.doggo.doggo.repository.TrackingRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TrackingService {

    private final ReservationRepository reservationRepository;
    private final TrackingRepository trackingRepository;

    @Transactional
    public void save(TrackingRequestDTO dto, List<MultipartFile> photos) throws Exception{

        // 1. DTO -> Entity
        Reservation reservation = reservationRepository.findById(dto.getReservationId())
                .orElseThrow(() -> new IllegalArgumentException("예약 없음"));

        // 2. Tracking 생성
        Tracking tracking = new Tracking();
        tracking.setReservation(reservation);
        tracking.setRecord(dto.getRecord());

        // 3. status Map -> JSON 문자열
        ObjectMapper mapper = new ObjectMapper();
        tracking.setStatusJSON(mapper.writeValueAsString(dto.getStatus()));
        
        // 4. 태그 저장
        for (String tagName : dto.getTags()) {
            TrackingTag tag = new TrackingTag();
            tag.setName(tagName);
            tracking.addTag(tag);
        }
        
        // 5. 사진 저장
        if(photos != null && !photos.isEmpty()){

            String uploadDir = System.getProperty("user.dir") + "/uploads/tracking";
            File dir = new File(uploadDir);

            if (!dir.exists()) {
                dir.mkdirs();
            }

            for (MultipartFile file : photos) {

                String savedName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                String fullPath = uploadDir + "/" + savedName;

                file.transferTo(new File(fullPath));

                TrackingPhoto photo = new TrackingPhoto();
                photo.setOriginalName(file.getOriginalFilename());
                photo.setSavedName(savedName);
                photo.setFilePath("/uploads/tracking/" + savedName);

                tracking.addPhoto(photo);
            }
        }

        trackingRepository.save(tracking);

    }


}
