package com.udacity.jdnd.course3.critter.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
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

    public List<Employee> findEmployeesForService(EmployeeSkill skill, DayOfWeek daysAvailable){
        return employeeRepository.findEmployeeBySkillsAndDaysAvailable(skill, daysAvailable);
    }
}
