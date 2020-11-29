package com.loginext.assignment.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.loginext.assignment.domain.enums.BookingStatus;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Document("bookings")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Booking extends BaseEntity {
    private String customerRefId;
    private Location location;
    private String driverRefId;
    private BookingStatus status;
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss",iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone = "IST")
    private Date completedOn;
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss",iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone = "IST")
    private Date cancelledOn;
    private String reason;
}
