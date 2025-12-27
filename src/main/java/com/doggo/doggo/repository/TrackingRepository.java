package com.doggo.doggo.repository;

import com.doggo.doggo.entity.Tracking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackingRepository extends JpaRepository<Tracking, Long> {
}
