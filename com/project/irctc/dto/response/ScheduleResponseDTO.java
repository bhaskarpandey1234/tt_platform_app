package com.project.irctc.dto.response;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ScheduleResponseDTO {
    private Long scheduleId;
    private String trainName;
    private String routeDetails;
    private LocalDate departureDate;
    private LocalTime departureTime;
}

