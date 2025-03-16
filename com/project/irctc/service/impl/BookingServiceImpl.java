package com.project.irctc.service.impl;

import com.project.irctc.dto.UserBooking;
import com.project.irctc.dto.response.BookingDetailsResponseDTO;
import com.project.irctc.dto.request.BookingRequestDTO;
import com.project.irctc.dto.response.BookingResponseDTO;
import com.project.irctc.enums.BookingStatus;
import com.project.irctc.exception.ResourceNotFoundException;
import com.project.irctc.model.*;
import com.project.irctc.model.appuserrole.AppUser;
import com.project.irctc.repository.BookingRepository;
import com.project.irctc.repository.ScheduleRepository;
import com.project.irctc.repository.AppUserRepository;
import com.project.irctc.repository.TrainRepository;
import com.project.irctc.service.BookingService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private TrainRepository trainRepository;

    @Transactional
    public ResponseEntity<BookingResponseDTO> bookTickets(BookingRequestDTO requestDTO) {
        BookingResponseDTO responseDTO = new BookingResponseDTO();

        // Validate the number of seats
        if (requestDTO.getNumberOfSeats() == null || requestDTO.getNumberOfSeats() <= 0) {
//            responseDTO.setStatus("Failure");
            responseDTO.setStatus(BookingStatus.FAILED); // Use enum here
            responseDTO.setMessage("Number of seats must be greater than zero.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
        }

        // Fetch the schedule
        Schedule schedule = scheduleRepository.findById(requestDTO.getScheduleId())
                .orElseThrow(() -> new ResourceNotFoundException("Schedule with ID " + requestDTO.getScheduleId() + " not found"));

        // Validate seat availability
        if (requestDTO.getNumberOfSeats() > schedule.getAvailableSeats()) {
//            responseDTO.setStatus("Failure");
            responseDTO.setStatus(BookingStatus.FAILED); // Use enum here
            responseDTO.setMessage("Not enough seats available");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
        }

        // Fetch the user
        AppUser user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + requestDTO.getUserId() + " not found"));

        // Create a new Booking entity
        Booking booking = new Booking();
        booking.setNumberOfSeatsBooked(requestDTO.getNumberOfSeats());
//        booking.setSeatNumbers(requestDTO.getSeatNumbers());
        booking.setBookingDate(LocalDateTime.now().toString());
//        booking.setStatus("Booked");
        booking.setStatus(BookingStatus.BOOKED); // Set status using enum
        booking.setSchedule(schedule);
        booking.setUser(user);

        // Save the booking
        bookingRepository.save(booking);

        // Update the available seats in the schedule
        schedule.setAvailableSeats(schedule.getAvailableSeats() - requestDTO.getNumberOfSeats());
        scheduleRepository.save(schedule);

        // Populate response
        responseDTO.setBookingId(booking.getId());
//        responseDTO.setStatus("Success");
        responseDTO.setStatus(BookingStatus.BOOKED); // Set status using enum
        responseDTO.setMessage("Booking successful");

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    public BookingDetailsResponseDTO getBookingDetailsByTrain(Long trainId) throws NoResourceFoundException {
        // Fetch the train
        Train train = trainRepository.findById(trainId)
                .orElseThrow(() -> new NoResourceFoundException(HttpMethod.GET, "Invalid Train ID"));

        // Fetch schedules associated with the train
        var schedules = scheduleRepository.findByTrainId(trainId);

        // Map to store non-booked seats per schedule
        Map<Long, Integer> nonBookedSeatsPerSchedule = new HashMap<>();
        // Map to store user bookings per schedule (Schedule ID -> List<UserBooking>)
        Map<Long, List<UserBooking>> userBookingsPerSchedule = new HashMap<>();

        int totalSeats = train.getTotalSeats();

        for (Schedule schedule : schedules) {
            // Fetch bookings for the schedule
            var bookings = bookingRepository.findByScheduleId(schedule.getId());
            int bookedSeats = 0;

            // List to store user bookings for this specific schedule
            List<UserBooking> userBookings = new ArrayList<>();

            for (var booking : bookings) {
                bookedSeats += booking.getNumberOfSeatsBooked();
                String username = booking.getUser().getUsername();

                // Add user booking to the list
                userBookings.add(new UserBooking(username, booking.getNumberOfSeatsBooked()));
            }

            // Calculate non-booked seats for the schedule
            int nonBookedSeats = totalSeats - bookedSeats;
            nonBookedSeatsPerSchedule.put(schedule.getId(), nonBookedSeats);

            // Store user bookings for this schedule
            userBookingsPerSchedule.put(schedule.getId(), userBookings);
        }

        // Prepare response DTO
        BookingDetailsResponseDTO responseDTO = new BookingDetailsResponseDTO();
        responseDTO.setTrainId(trainId);
        responseDTO.setTrainName(train.getTrainName());
        responseDTO.setNonBookedSeatsPerSchedule(nonBookedSeatsPerSchedule);
        responseDTO.setUserBookingsPerSchedule(userBookingsPerSchedule);

        return responseDTO;
    }











//    public BookingDetailsResponseDTO getBookingDetailsByTrain(Long trainId) throws NoResourceFoundException {
//        // Fetch the train
//        Train train = trainRepository.findById(trainId)
//                .orElseThrow(() -> new NoResourceFoundException(HttpMethod.GET, "Invalid Train ID"));
//
//        // Fetch schedules associated with the train
//        var schedules = scheduleRepository.findByTrainId(trainId);
//
//        // Map to store non-booked seats per schedule
//        Map<Long, Integer> nonBookedSeatsPerSchedule = new HashMap<>();
//        // Map to store user bookings per schedule (Schedule ID -> (User -> Seats Booked))
//        Map<Long, Map<String, Integer>> userBookingsPerSchedule = new HashMap<>();
//
//        int totalSeats = train.getTotalSeats(); // Total seats for the train
//
//        for (Schedule schedule : schedules) {
//            // Fetch bookings for the schedule
//            var bookings = bookingRepository.findByScheduleId(schedule.getId());
//            int bookedSeats = 0;
//
//            // Map for user bookings for this specific schedule
//            Map<String, Integer> userBookings = new HashMap<>();
//
//            for (var booking : bookings) {
//                bookedSeats += booking.getNumberOfSeatsBooked();
//                String username = booking.getUser().getUsername();
//
//                // Add or update user bookings for this schedule
//                userBookings.put(
//                        username,
//                        userBookings.getOrDefault(username, 0) + booking.getNumberOfSeatsBooked()
//                );
//            }
//
//            // Calculate non-booked seats for the schedule
//            int nonBookedSeats = totalSeats - bookedSeats;
//            nonBookedSeatsPerSchedule.put(schedule.getId(), nonBookedSeats);
//
//            // Store user bookings for this schedule
//            userBookingsPerSchedule.put(schedule.getId(), userBookings);
//        }
//
//        // Prepare response DTO
//        BookingDetailsResponseDTO responseDTO = new BookingDetailsResponseDTO();
//        responseDTO.setTrainId(trainId);
//        responseDTO.setTrainName(train.getTrainName());
//        responseDTO.setNonBookedSeatsPerSchedule(nonBookedSeatsPerSchedule);
//        responseDTO.setUserBookingsPerSchedule(userBookingsPerSchedule);
//
//        return responseDTO;
//    }




//    public BookingDetailsResponseDTO getBookingDetailsByTrain(Long trainId) throws NoResourceFoundException {
//        // Fetch the train
//        Train train = null;
//        try {
//            train = trainRepository.findById(trainId)
//                    .orElseThrow(() -> new NoResourceFoundException(HttpMethod.GET,"Invalid Train ID"));
//        } catch (NoResourceFoundException e) {
//            throw new NoResourceFoundException(HttpMethod.GET,e.getMessage());
//        }
//
//        // Fetch schedules associated with the train
//        var schedules = scheduleRepository.findByTrainId(trainId);
//
//        // Map to store seats booked by each user
//        Map<String, Integer> seatsBookedByUser = new HashMap<>();
//
//        int totalSeats = train.getTotalSeats();
//        int bookedSeats = 0;
//
//
//        for (Schedule schedule : schedules) {
//            // Fetch bookings for the schedule
//            var bookings = bookingRepository.findByScheduleId(schedule.getId());
//            for (var booking : bookings) {
//                bookedSeats += booking.getNumberOfSeatsBooked();
//                String username = booking.getUser().getUsername();
//
//                seatsBookedByUser.put(
//                        username,
//                        seatsBookedByUser.getOrDefault(username, 0) +
//                                booking.getNumberOfSeatsBooked()
//                );
//            }
//        }
//
//        // Calculate non-booked seats
//        int nonBookedSeats = totalSeats - bookedSeats;
//
//        // Prepare response DTO
//        BookingDetailsResponseDTO responseDTO = new BookingDetailsResponseDTO();
//        responseDTO.setTrainId(trainId);
//        responseDTO.setTrainName(train.getTrainName());
//        responseDTO.setSeatsBookedByUser(seatsBookedByUser);
//        responseDTO.setNonBookedSeats(nonBookedSeats);
//
//        return responseDTO;
//    }
}

