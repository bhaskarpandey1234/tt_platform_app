package com.project.irctc.dto;

public class UserBooking {
    private String username;
    private int seatsBooked;

    public UserBooking(String username, int seatsBooked) {
        this.username = username;
        this.seatsBooked = seatsBooked;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getSeatsBooked() {
        return seatsBooked;
    }

    public void setSeatsBooked(int seatsBooked) {
        this.seatsBooked = seatsBooked;
    }

    @Override
    public String toString() {
        return "UserBooking{" +
                "username='" + username + '\'' +
                ", seatsBooked=" + seatsBooked +
                '}';
    }
}

