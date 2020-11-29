package com.loginext.assignment.web;

import com.loginext.assignment.domain.Customer;
import com.loginext.assignment.domain.Driver;
import com.loginext.assignment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/customer/detail")
    public Customer customerDetails(@RequestParam(value = "mobile",required = false) String mobile,@RequestParam(value = "email",required = false) String email) {
        Customer customer = new Customer();
        customer.setMobile(mobile);
        customer.setEmail(email);
        return userService.getCustomer(customer);
    }

    @GetMapping("/customers")
    public List<Customer> allCustomers() {
        return userService.getAllCustomers();
    }

    @PostMapping("/customer/detail")
    public Customer customerDetails(@RequestBody Customer customer){
        return userService.addCustomer(customer);
    }

    @PutMapping("/customer/location")
    public Customer customerLocation(@RequestBody Customer customer) {
        return userService.updateCustomerLocation(customer);
    }

    @GetMapping("/driver/detail")
    public Driver driverDetails(@RequestParam(value = "mobile",required = false) String mobile, @RequestParam(value = "email",required = false) String email) {
        Driver driver = new Driver();
        driver.setMobile(mobile);
        driver.setEmail(email);
        return userService.getDriver(driver);
    }

    @GetMapping("/drivers")
    public List<Driver> driverDetails() {
        return userService.getAllDrivers();
    }

    @PostMapping("/driver/detail")
    public Driver driverDetails(@RequestBody Driver driver){
        return userService.addDriver(driver);
    }

    @PutMapping("/driver/location")
    public Driver driverLocation(@RequestBody Driver driver) {
        return userService.updateDriverLocation(driver);
    }




}
