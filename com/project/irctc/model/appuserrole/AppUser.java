package com.project.irctc.model.appuserrole;

import com.project.irctc.model.Booking;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

//    @Column(nullable = false, unique = true)
    private String phoneNumber;

    private boolean isActive = true;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy="user", fetch=FetchType.EAGER)
    private List<AppUserRole> userRoles;

}








//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Booking> bookings;

