package com.loginext.assignment.repository;

import com.loginext.assignment.domain.Driver;
import com.loginext.assignment.domain.enums.DriverStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DriverRepository extends MongoRepository<Driver,String> {

    Driver findByMobileOrEmail(String mobile, String email);

    List<Driver> findByStatus(DriverStatus status);
}
