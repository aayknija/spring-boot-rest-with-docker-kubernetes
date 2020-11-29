package com.loginext.assignment.repository;

import com.loginext.assignment.domain.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BookingRepository extends MongoRepository<Booking,String> {

    List<Booking> findByDriverRefId(String driverRefId);

    List<Booking> findByCustomerRefId(String customerRefId);
}
