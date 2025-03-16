package com.project.irctc.controller;

import com.project.irctc.dto.request.BookingRequestDTO;
import com.project.irctc.dto.request.TrainRequestDTO;
import com.project.irctc.dto.response.BookingResponseDTO;
import com.project.irctc.dto.response.TrainResponseDTO;
import com.project.irctc.service.BookingService;
import com.project.irctc.service.TrainService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/train")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")

public class TrainController {

    @Autowired
    private final TrainService trainService;
    private final BookingService bookingService;

    @PostMapping("/search-with-filter")
    public ResponseEntity<Page<TrainResponseDTO>> getTrains(@RequestBody TrainRequestDTO requestDTO) {
        Page<TrainResponseDTO> trains = trainService.getTrains(requestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(trains);
    }
    @PostMapping("/booking")
    public ResponseEntity<BookingResponseDTO> bookTickets(@RequestBody BookingRequestDTO requestDTO) {
        return bookingService.bookTickets(requestDTO);
    }

}









//package com.project.irctc.controller;
//
//import com.project.irctc.model.Route;
//import com.project.irctc.model.Schedule;
//import com.project.irctc.model.Station;
//import com.project.irctc.model.Train;
//import com.project.irctc.service.TrainService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.format.annotation.DateTimeFormat;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDate;
//import java.time.LocalTime;
//
//@RestController
//@RequestMapping("/api/train")
//@RequiredArgsConstructor
//public class TrainController {
//
//    @Autowired
//    private final TrainService trainService;
//
//    @GetMapping("/get")
//    public ResponseEntity<Page<Train>> getTrains(
//            @RequestParam(value = "page", defaultValue = "0") int page,
//            @RequestParam(value = "size", defaultValue = "10") int size,
//            @RequestParam(value = "fromStation", required = false) String fromStation,
//            @RequestParam(value = "toStation", required = false) String toStation,
//            @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
//
//        Page<Train> trains = trainService.getTrains(page, size, fromStation, toStation, date);
//        return ResponseEntity.ok(trains);
//    }
//
//    @PostMapping("/create-train")
//    public ResponseEntity<Train> createTrain(@RequestBody Train train) {
//        Train createdTrain = trainService.createTrain(train);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdTrain);
//    }
//    @PostMapping("/create-station")
//    public ResponseEntity<Station> createStation(@RequestBody Station station) {
//        Station createdStation = trainService.createStation(station);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdStation);
//    }
//    @PostMapping("/schedule")
//    public ResponseEntity<Schedule> createSchedule(
//            @RequestParam Long trainId,
//            @RequestParam Long routeId,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureDate,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime departureTime) {
//        Schedule createdSchedule = trainService.createSchedule(trainId, routeId, departureDate, departureTime);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdSchedule);
//    }
//    @PostMapping("/route")
//    public ResponseEntity<Route> createRoute(
//            @RequestParam Long stationFromId,
//            @RequestParam Long stationToId,
//            @RequestParam double distance) {
//        Route createdRoute = trainService.createRoute(stationFromId, stationToId, distance);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdRoute);
//    }
//
//}
//
