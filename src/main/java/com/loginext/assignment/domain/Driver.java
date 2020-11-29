package com.loginext.assignment.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.loginext.assignment.domain.enums.DriverStatus;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("drivers")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Driver extends BaseEntity {
    private String name;
    private String mobile;
    private String email;
    private Location location;
    private DriverStatus status=DriverStatus.AVAILABLE;
    private Double distance;
}
