package com.project.irctc.repository;

import com.project.irctc.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByScheduleId(Long scheduleId);
}
