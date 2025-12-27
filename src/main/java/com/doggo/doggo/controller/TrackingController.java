package com.doggo.doggo.controller;

import com.doggo.doggo.Service.TrackingService;
import com.doggo.doggo.dto.TrackingRequestDTO;
import com.doggo.doggo.dto.TrackingResponseDTO;
import com.doggo.doggo.entity.TrackingTag;
import com.doggo.doggo.repository.TrackingRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "https://dog-go-frontend-roan.vercel.app")
@RequiredArgsConstructor
@RequestMapping("/api/tracking")
public class TrackingController {

    private final TrackingService trackingService;
    private final TrackingRepository trackingRepository;

    @PostMapping
    public ResponseEntity<?> saveTracking(
            @RequestPart("data") TrackingRequestDTO dto,
            @RequestPart(value = "photos", required = false) List<MultipartFile> photos
            ) throws Exception {
            trackingService.save(dto, photos);
            return ResponseEntity.ok().build();
    }

    @GetMapping
    public Page<TrackingResponseDTO> getTrackingList(
            @RequestParam int page,
            @RequestParam int size
    ){
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "id")
        );

        ObjectMapper mapper = new ObjectMapper();

        return  trackingRepository.findAll(pageable)
                .map(tracking -> {

                    Map<String, List<String>> statusMap = null;
                    try {
                        if(tracking.getStatusJSON() != null) {
                            statusMap = mapper.readValue(
                                    tracking.getStatusJSON(),
                                    new TypeReference<Map<String, List<String>>>() {
                                    }
                            );
                        }
                    }catch (Exception e) {
                        statusMap = Map.of();
                    }

                    return new TrackingResponseDTO(
                            tracking.getId(),
                            tracking.getRecord(),
                            tracking.getPhotos().stream()
                                    .map(photo -> "/uploads/tracking/" + photo.getSavedName())
                                    .toList(),
                            tracking.getTags().stream()
                                    .map(TrackingTag::getName)
                                    .toList(),
                            tracking.getReservation().getName(),
                            tracking.getReservation().getDogAge(),
                            tracking.getReservation().getDogType(),
                            statusMap,
                            tracking.getReservation().getCalender()
                    );
                });

    }
}
