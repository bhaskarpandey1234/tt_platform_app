package com.project.irctc.service;

import com.project.irctc.dto.response.BookingDetailsResponseDTO;
import com.project.irctc.dto.request.BookingRequestDTO;
import com.project.irctc.dto.response.BookingResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.resource.NoResourceFoundException;

    public interface BookingService {

        ResponseEntity<BookingResponseDTO> bookTickets(BookingRequestDTO requestDTO);
        BookingDetailsResponseDTO getBookingDetailsByTrain(Long trainId) throws NoResourceFoundException;
    }

