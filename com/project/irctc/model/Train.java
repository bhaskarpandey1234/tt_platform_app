package com.project.irctc.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Train {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String trainName;

    @Column(nullable = false, unique = true)
    private String trainNumber;

    @Column(nullable = false)
    private Integer totalSeats;

    @OneToMany(mappedBy = "train", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Schedule> schedules;

    @OneToMany(mappedBy = "train", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TrainRoute> trainRoutes;

}









//package com.project.irctc.model;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.List;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//public class Train {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false)
//    private String trainName;
//
//    @Column(nullable = false, unique = true)
//    private String trainNumber;
//
//    @Column(nullable = false)
//    private Integer totalSeats;
//
//
//
////    @OneToMany(mappedBy = "train", cascade = CascadeType.ALL, orphanRemoval = true)
////    private List<Seat> seats; // Fixed seats for this train
//
//
//
//
//
////    @ManyToOne
////    @JoinColumn(name = "route_id", nullable = false)
////    private Route route;
//
//
//
////    @OneToMany(mappedBy = "train", cascade = CascadeType.ALL, orphanRemoval = true)
////    private List<Schedule> schedules;
////
////    @OneToMany(mappedBy = "train", cascade = CascadeType.ALL, orphanRemoval = true)
////    private List<Seat> seats;
//
//
//
////    @OneToMany(mappedBy = "train", cascade = CascadeType.ALL, orphanRemoval = true)
////    private List<Booking> bookings;
//
//}
//
