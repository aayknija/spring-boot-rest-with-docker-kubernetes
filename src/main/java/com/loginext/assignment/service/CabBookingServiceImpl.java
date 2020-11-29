package com.loginext.assignment.service;

import com.loginext.assignment.domain.Booking;
import com.loginext.assignment.domain.Customer;
import com.loginext.assignment.domain.Driver;
import com.loginext.assignment.domain.enums.BookingStatus;
import com.loginext.assignment.domain.enums.CustomerStatus;
import com.loginext.assignment.domain.enums.DriverStatus;
import com.loginext.assignment.exception.CabsNotAvailableException;
import com.loginext.assignment.exception.InvalidInputException;
import com.loginext.assignment.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CabBookingServiceImpl implements CabBookingService {

    @Autowired
    private UserService userService;

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public Booking bookCab(Booking booking) {
        Customer customer = userService.findCustomerById(booking.getCustomerRefId());
        if(customer == null){
            throw new InvalidInputException("Customer does not exist with provided details");
        }
        List<Driver> availableDrivers = userService.getAllAvailableDrivers();
        if(CollectionUtils.isEmpty(availableDrivers)){
            throw new CabsNotAvailableException("Sorry ! We do not have any cab available. Please try after sometime.");
        }
        bookCab(customer,booking);
        return booking;
    }

    private synchronized void bookCab(Customer customer, Booking booking) {
        Driver availableDriver = userService.getNearestDriver(booking);
        if(availableDriver == null){
            throw new CabsNotAvailableException("Sorry ! We do not have any cab available. Please try after sometime.");
        }
        booking.setCustomerRefId(customer.getId());
        booking.setDriverRefId(availableDriver.getId());
        booking.setStatus(BookingStatus.BOOKED);
        bookingRepository.save(booking);
        customer.setStatus(CustomerStatus.BOOKED);
        userService.updateCustomer(customer);
        availableDriver.setStatus(DriverStatus.BOOKED);
        userService.updateDriver(availableDriver);
    }

    @Override
    public Booking cancelBooking(Booking booking) {
        Optional<Booking> optionalBooking = bookingRepository.findById(booking.getId());
        if(optionalBooking.isPresent()){
            Booking bookingDB = optionalBooking.get();
            bookingDB.setReason(booking.getReason());
            bookingDB.setStatus(BookingStatus.CANCELLED);
            bookingRepository.save(bookingDB);
            userService.updateCustomerStatus(bookingDB.getCustomerRefId(),CustomerStatus.AVAILABLE);
            userService.updateDriverStatus(bookingDB.getDriverRefId(),DriverStatus.AVAILABLE);
        }
        throw new InvalidInputException("Booking does not exists with provided details");
    }

    @Override
    public Booking startTrip(Booking booking) {
        Optional<Booking> optionalBooking = bookingRepository.findById(booking.getId());
        if(optionalBooking.isPresent()){
            Booking bookingDB = optionalBooking.get();
            bookingDB.setReason(booking.getReason());
            bookingDB.setCancelledOn(new Date());
            bookingDB.setStatus(BookingStatus.IN_TRANSIT);
            bookingRepository.save(bookingDB);
            userService.updateCustomerStatus(bookingDB.getCustomerRefId(),CustomerStatus.IN_TRANSIT);
            userService.updateDriverStatus(bookingDB.getDriverRefId(),DriverStatus.IN_TRANSIT);
        }
        throw new InvalidInputException("Booking does not exists with provided details");
    }

    @Override
    public Booking endTrip(Booking booking) {
        Optional<Booking> optionalBooking = bookingRepository.findById(booking.getId());
        if(optionalBooking.isPresent()){
            Booking bookingDB = optionalBooking.get();
            bookingDB.setCompletedOn(new Date());
            bookingDB.setStatus(BookingStatus.COMPLETED);
            bookingRepository.save(bookingDB);
            userService.updateCustomerStatus(bookingDB.getCustomerRefId(),CustomerStatus.AVAILABLE);
            userService.updateDriverStatus(bookingDB.getDriverRefId(),DriverStatus.AVAILABLE);
        }
        throw new InvalidInputException("Booking does not exists with provided details");
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public List<Booking> getBookingByDriverRefId(String driverRefId) {
        return bookingRepository.findByDriverRefId(driverRefId);
    }

    @Override
    public List<Booking> getBookingByCustomerRefId(String customerRefId) {
        return bookingRepository.findByCustomerRefId(customerRefId);
    }
}
