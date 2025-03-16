package com.project.irctc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDTO {
    private Long id;
    private LocalDate departureDate;
    private LocalTime departureTime;
    private LocalDateTime arrivalTime;
    private TrainDTO train;
    private RouteDTO route;
    private Long availableSeats;
}
//response
//private List<String> daysOfOperation;
