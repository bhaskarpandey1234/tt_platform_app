package com.project.irctc.model;

import com.project.irctc.enums.BookingStatus;
import com.project.irctc.model.appuserrole.AppUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer numberOfSeatsBooked;

//    @Column(nullable = false)
//    private List<String> seatNumbers; // e.g., A1, A2, A3...

    @Column(nullable = false)
    private String bookingDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status;

//    @Column(nullable = false)
//    private String status; // "Booked", "Cancelled", etc.

    @ManyToOne
    @JoinColumn(nullable = false)
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(nullable = false)
    private AppUser user;

}

