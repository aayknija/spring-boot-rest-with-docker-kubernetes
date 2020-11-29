package com.loginext.assignment.service;

import com.loginext.assignment.domain.Booking;

import java.util.List;

public interface CabBookingService {

    Booking bookCab(Booking booking);

    Booking cancelBooking(Booking booking);

    Booking startTrip(Booking booking);

    Booking endTrip(Booking booking);

    List<Booking> getAllBookings();

    List<Booking> getBookingByDriverRefId(String driverRefId);

    List<Booking> getBookingByCustomerRefId(String customerRefId);
}
