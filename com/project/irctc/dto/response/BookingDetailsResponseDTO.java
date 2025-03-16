package com.project.irctc.dto.response;

import com.project.irctc.dto.UserBooking;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class BookingDetailsResponseDTO {
    private Long trainId;
    private String trainName;
    private Map<Long, List<UserBooking>> userBookingsPerSchedule; // Updated field
    //    private Map<Long, Map<String, Integer>> userBookingsPerSchedule; // User bookings per schedule (Schedule ID -> (User -> Seats Booked))
    private Map<Long, Integer> nonBookedSeatsPerSchedule; // Available seats per schedule (Schedule ID -> Non-Booked Seats)


//    private Map<String, Integer> seatsBookedByUser; // Username -> Number of seats booked
//    private Integer nonBookedSeats; // Count of non-booked seats
//    private Map<Long, Integer> nonBookedSeatsPerSchedule; // Available seats per schedule (Schedule ID -> Non-Booked Seats)

}
