package com.project.irctc.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class BookingRequestDTO {
    private Long scheduleId; // Reference to the schedule being booked
    private Long userId; // User making the booking
    private Integer numberOfSeats; // Number of seats to book
//    private List<String> seatNumbers; // List of seat numbers being booked
}
