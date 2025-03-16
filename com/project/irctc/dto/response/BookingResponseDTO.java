package com.project.irctc.dto.response;

import com.project.irctc.enums.BookingStatus;
import lombok.Data;

@Data
public class BookingResponseDTO {
    private Long bookingId;
//    private String status; // "Success" or "Failure"
  private BookingStatus status; // Use enum here
    private String message; // Message detailing success or error
}
