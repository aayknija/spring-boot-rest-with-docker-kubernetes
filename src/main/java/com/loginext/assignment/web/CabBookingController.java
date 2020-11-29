package com.loginext.assignment.web;

import com.loginext.assignment.domain.Booking;
import com.loginext.assignment.service.CabBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cab")
public class CabBookingController {

    @Autowired
    private CabBookingService cabBookingService;

    @GetMapping("/bookings")
    public List<Booking> allBookings(){
        return cabBookingService.getAllBookings();
    }

    @GetMapping("/driver/bookings")
    public List<Booking> driverBookings(@RequestParam("driverRefId") String driverRefId){
        return cabBookingService.getBookingByDriverRefId(driverRefId);
    }

    @GetMapping("/customer/bookings")
    public List<Booking> customerBookings(@RequestParam("customerRefId") String customerRefId){
        return cabBookingService.getBookingByCustomerRefId(customerRefId);
    }

    @PostMapping("/book")
    public Booking bookCab(@RequestBody Booking booking){
        return cabBookingService.bookCab(booking);
    }

    @PutMapping("/cancel")
    public Booking cancelBooking(@RequestBody Booking booking){
        return cabBookingService.cancelBooking(booking);
    }

    @PutMapping("/trip/start")
    public Booking startTrip(@RequestBody Booking booking){
        return cabBookingService.startTrip(booking);
    }

    @PutMapping("/trip/end")
    public Booking endTrip(@RequestBody Booking booking){
        return cabBookingService.endTrip(booking);
    }

}
