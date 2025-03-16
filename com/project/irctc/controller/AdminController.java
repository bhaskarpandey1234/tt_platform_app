package com.project.irctc.controller;


import com.project.irctc.dto.TrainDTO;
import com.project.irctc.dto.request.*;
import com.project.irctc.dto.response.*;

import com.project.irctc.service.BookingService;
import com.project.irctc.service.TrainService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.resource.NoResourceFoundException;


@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequiredArgsConstructor
public class AdminController {

    @Autowired
    private final BookingService bookingService;
    private final TrainService trainService;

    @GetMapping("/booking-detail")
    public ResponseEntity<BookingDetailsResponseDTO> getBookingDetailsByTrain(@RequestParam Long trainId) throws NoResourceFoundException {
        BookingDetailsResponseDTO responseDTO= bookingService.getBookingDetailsByTrain(trainId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }
    @PostMapping("/create-train")
    public ResponseEntity<TrainDTO> createTrain(@RequestBody TrainDTO trainRequest) {
        TrainDTO createdTrain = trainService.createTrain(trainRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTrain);
    }

    @PostMapping("/route")
    public ResponseEntity<RouteResponseDTO> createRoute(@RequestBody RouteRequestDTO routeRequest) {
        RouteResponseDTO createdRoute = trainService.createRoute(routeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRoute);
    }

    @PostMapping("/train-route")
    public ResponseEntity<TrainRouteResponseDTO> createTrainRoute(@RequestBody TrainRouteRequestDTO trainRouteRequest) {
        TrainRouteResponseDTO createdTrainRoute = trainService.createTrainRoute(trainRouteRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTrainRoute);
    }

    @PostMapping("/schedule")
    public ResponseEntity<ScheduleResponseDTO> createSchedule(@RequestBody ScheduleRequestDTO scheduleRequest) {
        ScheduleResponseDTO createdSchedule = trainService.createSchedule(scheduleRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSchedule);
    }

    @PostMapping("/create-station")
    public ResponseEntity<StationResponseDTO> createStation(@RequestBody StationRequestDTO stationRequest) {
        StationResponseDTO createdStation = trainService.createStation(stationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStation);
    }
//    @PostMapping("/schedule")
//    public ResponseEntity<ScheduleDTO> createSchedule(
//            @RequestParam Long trainId,
//            @RequestParam Long routeId,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureDate,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime departureTime) {
//        ScheduleDTO createdSchedule = trainService.createSchedule(trainId, routeId, departureDate, departureTime);
//        return ResponseEntity.status(HttpStatus.OK).body(createdSchedule);
//    }
//
//    @PostMapping("/train-route")
//    public ResponseEntity<String> createTrainRoute(
//            @RequestParam Long trainId,
//            @RequestParam Long routeId) {
//        TrainRoute createdTrainRoute = trainService.createTrainRoute(trainId, routeId);
//        return ResponseEntity.status(HttpStatus.CREATED).body("created successfully");
//    }
//    @PostMapping("/create-train")
//    public ResponseEntity<TrainResponseDTO> createTrain(@RequestBody TrainRequestDTO trainRequest) {
//        TrainResponseDTO createdTrain = trainService.createTrain(trainRequest);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdTrain);
//    }
//    @PostMapping("/create-station")
//    public ResponseEntity<Station> createStation(@RequestBody Station station) {
//        Station createdStation = trainService.createStation(station);
//        return ResponseEntity.status(HttpStatus.OK).body(createdStation);
//    }
//
//    @PostMapping("/route")
//    public ResponseEntity<Route> createRoute(
//            @RequestParam Long stationFromId,
//            @RequestParam Long stationToId,
//            @RequestParam double distance) {
//        Route createdRoute = trainService.createRoute(stationFromId, stationToId, distance);
//        return ResponseEntity.status(HttpStatus.OK).body(createdRoute);
//    }
}





//package com.project.hospital.controller;
//
//import com.project.hospital.dto.HospitalResponseDTO;
//import com.project.hospital.dto.PatientResponseDTO;
//import com.project.hospital.dto.StaffResponseDTO;
//import com.project.hospital.service.HospitalService;
//import com.project.hospital.service.PatientService;
//import com.project.hospital.service.StaffService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/admin")
//@PreAuthorize("hasRole('ROLE_ADMIN')")
//@RequiredArgsConstructor
//public class AdminController {
//
//    private final HospitalService hospitalService;
//    private final PatientService patientService;
//    private final StaffService staffService;
//
//    @GetMapping("/hospitals")
//    public ResponseEntity<Page<HospitalResponseDTO>> getHospitals(
//            @RequestParam(required = false) String hospitalName,
//            @RequestParam(required = false) String address,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size,
//            @RequestParam(defaultValue = "hospitalName") String sortBy) {
//
//        PageImpl<HospitalResponseDTO> hospitals = hospitalService.getHospitals(hospitalName, address, page, size, sortBy);
//        return ResponseEntity.ok(hospitals);
//    }
//
//    // API to fetch patients with optional filters (name, disease, hospital name)
//    @GetMapping("/patients")
//    public ResponseEntity<PageImpl<PatientResponseDTO>> getPatients(
//            @RequestParam(required = false) String patientName,
//            @RequestParam(required = false) String disease,
//            @RequestParam(required = false) String hospitalName,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size,
//            @RequestParam(defaultValue = "id") String sortBy) {
//
//        PageImpl<PatientResponseDTO> patientPage = patientService.getPatientsWithFilters(
//                patientName, disease, hospitalName, page, size, sortBy);
//
//        return ResponseEntity.ok(patientPage);
//    }
//    @GetMapping("/staff")
//    public ResponseEntity<PageImpl<StaffResponseDTO>> getStaffWithFilters(
//            @RequestParam(required = false) String hospitalName,
//            @RequestParam(required = false) String staffName,
//            @RequestParam(required = false) String role,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size,
//            @RequestParam(defaultValue = "staffName") String sortBy) {
//
//        // Validate sortBy parameter
//        if (!sortBy.equalsIgnoreCase("staffName") && !sortBy.equalsIgnoreCase("hospitalName") && !sortBy.equalsIgnoreCase("id")) {
//            // Return BadRequest if sortBy is invalid
//            return ResponseEntity.badRequest().body(null); // Return empty body with a 400 status
//        }
//
//        // Fetch staff based on filters
//        PageImpl<StaffResponseDTO> staffPage = staffService.getStaffWithFilters(hospitalName, staffName, role, page, size, sortBy);
//
//        // Return paginated results as a valid ResponseEntity
//        return ResponseEntity.ok(staffPage);
//    }
//}




////package com.project.hospital.controller;
////
////import com.project.hospital.dto.HospitalResponseDTO;
////import com.project.hospital.dto.PatientResponseDTO;
////import com.project.hospital.dto.StaffResponseDTO;
////import com.project.hospital.service.HospitalService;
////import com.project.hospital.service.PatientService;
////import com.project.hospital.service.StaffService;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.data.domain.Page;
////import org.springframework.http.HttpStatus;
////import org.springframework.http.ResponseEntity;
////import org.springframework.security.access.prepost.PreAuthorize;
////import org.springframework.web.bind.annotation.*;
////
////@RestController
////@RequestMapping("/api/admin")
////@PreAuthorize("hasRole('ROLE_ADMIN')")
////public class AdminController {
////
////    @Autowired
////    private HospitalService hospitalService;
////
////    @Autowired
////    private PatientService patientService;
////
////    @Autowired
////    private StaffService staffService;
////
////    // API to fetch hospitals with optional filters (name, address)
////    @GetMapping("/hospitals")
////    public ResponseEntity<?> getHospitals(
////            @RequestParam(required = false) String hospitalName,
////            @RequestParam(required = false) String address,
////            @RequestParam(defaultValue = "0") int page,
////            @RequestParam(defaultValue = "10") int size,
////            @RequestParam(defaultValue = "hospitalName") String sortBy) {
////
////        try {
////            // Fetch hospitals based on parameters, handling null or empty values
////            Page<HospitalResponseDTO> hospitalPage = hospitalService.getHospitals(hospitalName, address, page, size, sortBy);
////            return ResponseEntity.ok(hospitalPage);
////        } catch (Exception e) {
////            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
////                    .body("An error occurred while fetching hospitals: " + e.getMessage());
////        }
////    }
////
////    // API to fetch patients with optional filters (name, disease, hospital name)
////    @GetMapping("/patients")
////    public ResponseEntity<?> getPatients(
////            @RequestParam(required = false) String patientName,
////            @RequestParam(required = false) String disease,
////            @RequestParam(required = false) String hospitalName,
////            @RequestParam(defaultValue = "0") int page,
////            @RequestParam(defaultValue = "10") int size,
////            @RequestParam(defaultValue = "id") String sortBy) {
////
////        try {
////            // Fetch patients based on parameters
////            Page<PatientResponseDTO> patients = patientService.getPatientsWithFilters(patientName, disease, hospitalName, page, size, sortBy);
////            return ResponseEntity.ok(patients);
////        } catch (Exception e) {
////            return ResponseEntity.status(500).body("Error occurred: " + e.getMessage());
////        }
////    }
////
////    // API to fetch staff with optional filters (hospital name, staff name, role)
////    @GetMapping("/staff")
////    public ResponseEntity<?> getStaffWithFilters(
////            @RequestParam(required = false) String hospitalName,
////            @RequestParam(required = false) String staffName,
////            @RequestParam(required = false) String role,
////            @RequestParam(defaultValue = "0") int page,
////            @RequestParam(defaultValue = "10") int size,
////            @RequestParam(defaultValue = "staffName") String sortBy) {
////
////        try {
////            // Validate sortBy parameter
////            if (!sortBy.equalsIgnoreCase("staffName") && !sortBy.equalsIgnoreCase("hospitalName") && !sortBy.equalsIgnoreCase("id")) {
////                return new ResponseEntity<>("Invalid sort parameter", HttpStatus.BAD_REQUEST);
////            }
////
////            // Fetch staff based on filters
////            Page<StaffResponseDTO> staffPage = staffService.getStaffWithFilters(hospitalName, staffName, role, page, size, sortBy);
////            return ResponseEntity.ok(staffPage);
////
////        } catch (Exception e) {
////            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
////        }
////    }
////}
//package com.project.hospital.controller;
//
//import com.project.hospital.dto.HospitalResponseDTO;
//import com.project.hospital.dto.PatientResponseDTO;
//import com.project.hospital.dto.StaffResponseDTO;
//import com.project.hospital.entity.Hospital;
//import com.project.hospital.service.HospitalService;
////import com.project.hospital.service.PatientService;
////import com.project.hospital.service.StaffService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/admin")
//@PreAuthorize("hasRole('ROLE_ADMIN')")
//@RequiredArgsConstructor
//public class AdminController {
//
//    private final HospitalService hospitalService;
////    private final PatientService patientService;
////    private final StaffService staffService;
//
//    // API to fetch hospitals with optional filters (name, address)
//    @GetMapping("/hospitals")
//    public Page<Hospital> getHospitals(
//            @RequestParam(required = false) String hospitalName,
//            @RequestParam(required = false) String address,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size,
//            @RequestParam(defaultValue = "hospitalName") String sortBy) {
//
//        return hospitalService.getHospitals(hospitalName, address, page, size, sortBy);
//
//    }
//
//    // API to fetch patients with optional filters (name, disease, hospital name)
////    @GetMapping("/patients")
////    public ResponseEntity<Page<PatientResponseDTO>> getPatients(
////            @RequestParam(required = false) String patientName,
////            @RequestParam(required = false) String disease,
////            @RequestParam(required = false) String hospitalName,
////            @RequestParam(defaultValue = "0") int page,
////            @RequestParam(defaultValue = "10") int size,
////            @RequestParam(defaultValue = "id") String sortBy) {
////
////        Page<PatientResponseDTO> patients = patientService.getPatientsWithFilters(patientName, disease, hospitalName, page, size, sortBy);
////        return ResponseEntity.ok(patients);
////    }
////
////    // API to fetch staff with optional filters (hospital name, staff name, role)
////    @GetMapping("/staff")
////    public ResponseEntity<Page<StaffResponseDTO>> getStaffWithFilters(
////            @RequestParam(required = false) String hospitalName,
////            @RequestParam(required = false) String staffName,
////            @RequestParam(required = false) String role,
////            @RequestParam(defaultValue = "0") int page,
////            @RequestParam(defaultValue = "10") int size,
////            @RequestParam(defaultValue = "staffName") String sortBy) {
////
////        Page<StaffResponseDTO> staffPage = staffService.getStaffWithFilters(hospitalName, staffName, role, page, size, sortBy);
////        return ResponseEntity.ok(staffPage);
////    }
//}
