package com.project.irctc.dto.request;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ScheduleRequestDTO {
    private Long trainId;
    private Long routeId;
    private LocalDate departureDate;
    private LocalTime departureTime;
}
