package com.loginext.assignment.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Location {
    private String type="Point";
    private List<Double> coordinates;
}
