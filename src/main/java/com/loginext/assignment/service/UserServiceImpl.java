package com.loginext.assignment.service;

import com.loginext.assignment.domain.Booking;
import com.loginext.assignment.domain.Customer;
import com.loginext.assignment.domain.Driver;
import com.loginext.assignment.domain.enums.CustomerStatus;
import com.loginext.assignment.domain.enums.DriverStatus;
import com.loginext.assignment.exception.DataNotFoundException;
import com.loginext.assignment.exception.InvalidInputException;
import com.loginext.assignment.repository.CustomerRepository;
import com.loginext.assignment.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public Customer getCustomer(Customer customer) throws InvalidInputException {
        return customerRepository.findByMobileOrEmail(customer.getMobile(),customer.getEmail());
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer findCustomerById(String customerRefId) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerRefId);
        if(optionalCustomer.isPresent()){
            return optionalCustomer.get();
        }
        return null;
    }

    @Override
    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomerLocation(Customer customer) throws DataNotFoundException {
        Optional<Customer> optionalCustomer = customerRepository.findById(customer.getId());
        if(optionalCustomer.isPresent()){
            Customer customerDB = optionalCustomer.get();
            customerDB.setLocation(customer.getLocation());
            return customerRepository.save(customerDB);
        }
        throw new DataNotFoundException("Customer is not present with provided details");
    }

    @Override
    public Driver getDriver(Driver driver) {
        return driverRepository.findByMobileOrEmail(driver.getMobile(),driver.getEmail());
    }

    @Override
    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    @Override
    public List<Driver> getAllAvailableDrivers() {
        return driverRepository.findByStatus(DriverStatus.AVAILABLE);
    }

    @Override
    public Driver addDriver(Driver driver) {
        return driverRepository.save(driver);
    }

    @Override
    public Driver updateDriverLocation(Driver driver) {
        Optional<Driver> optionalDriver = driverRepository.findById(driver.getId());
        if(optionalDriver.isPresent()){
            Driver driverDB = optionalDriver.get();
            driverDB.setLocation(driver.getLocation());
            return driverRepository.save(driverDB);
        }
        throw new DataNotFoundException("Driver is not present with provided details");
    }

    @Override
    public Driver getNearestDriver(Booking booking) {
        NearQuery nearQuery = NearQuery.near(new Point(booking.getLocation().getCoordinates().get(0),booking.getLocation().getCoordinates().get(1)), Metrics.KILOMETERS);
        nearQuery.spherical(true);
        Aggregation aggregation = Aggregation.newAggregation(geoNear(nearQuery,"distance"),
                match(Criteria.where("status").is(DriverStatus.AVAILABLE)),
                sort(Sort.Direction.ASC,"distance"),
                limit(1));
        AggregationResults<Driver> aggregationResult = mongoTemplate.aggregate(aggregation, Driver.class, Driver.class);
        if(!CollectionUtils.isEmpty(aggregationResult.getMappedResults())){
            return aggregationResult.getMappedResults().get(0);
        }
        return null;
    }

    @Override
    public void updateCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public void updateDriver(Driver driver) {
        driverRepository.save(driver);
    }

    @Override
    public void updateCustomerStatus(String customerRefId, CustomerStatus status) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerRefId);
        if(optionalCustomer.isPresent()){
            Customer customer = optionalCustomer.get();
            customer.setStatus(status);
            customerRepository.save(customer);
        }
    }

    @Override
    public void updateDriverStatus(String driverRefId, DriverStatus status) {
        Optional<Driver> optionalDriver = driverRepository.findById(driverRefId);
        if(optionalDriver.isPresent()){
            Driver driver = optionalDriver.get();
            driver.setStatus(status);
            driverRepository.save(driver);
        }
    }
}
