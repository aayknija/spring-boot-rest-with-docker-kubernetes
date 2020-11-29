package com.loginext.assignment.repository;

import com.loginext.assignment.domain.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends MongoRepository<Customer,String> {

    Customer findByMobileOrEmail(String mobile, String email);
}
