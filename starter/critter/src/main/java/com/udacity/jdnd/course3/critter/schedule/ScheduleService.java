package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {
    @Autowired
    ScheduleRepository scheduleRepository;

    public Long save(Schedule schedule){
        return scheduleRepository.save(schedule).getId();
    }

    public Schedule findScheduleById(Long id){
        return scheduleRepository.findById(id).orElse(null);
    }

    public List<Schedule> getAllSchedules(){
        return scheduleRepository.findAll();
    }

    public List<Schedule> getSchedulesForPet(Pet pet){
        return scheduleRepository.findAllByPets(pet);
    }

    public List<Schedule> getSchedulesForEmployee(Employee employee){
        return scheduleRepository.findAllByEmployees(employee);
    }
}
