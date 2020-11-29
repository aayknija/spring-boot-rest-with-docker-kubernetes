package com.loginext.assignment.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class BaseEntity implements Serializable {

    private static final long SerialVersionUID=1L;

    @Id
    String id;

    @CreatedDate
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss",iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone = "IST")
    private Date createdAt;

    @LastModifiedDate
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss",iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone = "IST")
    private Date lastModifiedAt;
}
