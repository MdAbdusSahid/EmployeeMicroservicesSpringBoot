package com.employeeapp.response;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class EmployeeResponse {
    private int id;
    private String name;
    private String email;
    private String bloodgroup;
    private AddressResponse addressResponse;
}
