package com.doggo.doggo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Tracking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @Lob
    private String record;

    @Lob
    private String statusJSON;

    @OneToMany(mappedBy = "tracking", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<TrackingPhoto> photos = new ArrayList<>();

    @OneToMany(mappedBy = "tracking", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<TrackingTag> tags = new ArrayList<>();

    /* 연관관계 편의 메서드 */
    public void addPhoto(TrackingPhoto photo){
        photos.add(photo);
        photo.setTracking(this);
    }

    public void addTag(TrackingTag tag) {
        tags.add(tag);
        tag.setTracking(this);
    }
}
