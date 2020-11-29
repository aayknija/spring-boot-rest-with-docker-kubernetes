package com.loginext.assignment.service;

import com.loginext.assignment.domain.Booking;
import com.loginext.assignment.domain.Customer;
import com.loginext.assignment.domain.Driver;
import com.loginext.assignment.domain.enums.CustomerStatus;
import com.loginext.assignment.domain.enums.DriverStatus;
import com.loginext.assignment.exception.DataNotFoundException;
import com.loginext.assignment.exception.InvalidInputException;

import java.util.List;

public interface UserService {

    Customer getCustomer(Customer customer) throws InvalidInputException;

    List<Customer> getAllCustomers();

    Customer findCustomerById(String customerRefId);

    Customer addCustomer(Customer customer);

    Customer updateCustomerLocation(Customer customer) throws DataNotFoundException;

    Driver getDriver(Driver driver);

    List<Driver> getAllDrivers();

    List<Driver> getAllAvailableDrivers();

    Driver addDriver(Driver driver);

    Driver updateDriverLocation(Driver driver) throws DataNotFoundException;

    Driver getNearestDriver(Booking booking);

    void updateCustomer(Customer customer);

    void updateDriver(Driver driver);

    void updateCustomerStatus(String customerRefId, CustomerStatus status);

    void updateDriverStatus(String driverRefId, DriverStatus status);
}
