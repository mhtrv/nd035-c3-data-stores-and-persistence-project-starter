package com.udacity.jdnd.course3.critter.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    public Long save(Employee employee){
        return employeeRepository.save(employee).getId();
    }

    public Employee findEmployeeById(Long id){
        return employeeRepository.findById(id).orElse(null);
    }

    public List<Employee> findEmployeesByIds(List<Long> ids){
        return employeeRepository.findAllById(ids);
    }
}
