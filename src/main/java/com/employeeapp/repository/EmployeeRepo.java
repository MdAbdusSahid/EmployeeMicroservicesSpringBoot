package com.employeeapp.repository;

import com.employeeapp.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepo extends JpaRepository<Employee,Integer> {
}
