package com.loginext.assignment.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.loginext.assignment.domain.enums.CustomerStatus;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("customers")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Customer extends BaseEntity {
    private String name;
    private String mobile;
    private String email;
    private Location location;
    private CustomerStatus status=CustomerStatus.AVAILABLE;

}
