package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.user.CustomerService;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    ScheduleService scheduleService;

    @Autowired
    PetService petService;

    @Autowired
    EmployeeService employeeService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Long id = scheduleService.save(convertScheduleDTOToSchedule(scheduleDTO));
        return convertScheduleToScheduleDTO(scheduleService.findScheduleById(id));
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleService.getAllSchedules();
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        for (Schedule schedule:schedules){
            scheduleDTOS.add(convertScheduleToScheduleDTO(schedule));
        }
        return scheduleDTOS;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        Pet pet = petService.findPetById(petId);
        List<Schedule> schedules = scheduleService.getSchedulesForPet(pet);
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        for (Schedule schedule:schedules){
            scheduleDTOS.add(convertScheduleToScheduleDTO(schedule));
        }
        return scheduleDTOS;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.findEmployeeById(employeeId);
        List<Schedule> schedules = scheduleService.getSchedulesForEmployee(employee);
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        for (Schedule schedule:schedules){
            scheduleDTOS.add(convertScheduleToScheduleDTO(schedule));
        }
        return scheduleDTOS;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Pet> pets = petService.findAllPetsByOwnerId(customerId);
        Set<Schedule> schedules = new HashSet<>();
        for(Pet pet:pets){
            schedules.addAll(scheduleService.getSchedulesForPet(pet));

        }

        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        for (Schedule schedule:schedules){
            scheduleDTOS.add(convertScheduleToScheduleDTO(schedule));
        }
        Collections.sort(scheduleDTOS);
        return scheduleDTOS;
    }

    private Schedule convertScheduleDTOToSchedule(ScheduleDTO scheduleDTO){
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO,schedule);
        schedule.setPets(petService.findPetsByIds(scheduleDTO.getPetIds()));
        schedule.setEmployees(employeeService.findEmployeesByIds(scheduleDTO.getEmployeeIds()));
        return schedule;
    }

    private ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule,scheduleDTO);
        List<Long> employeeIds = new ArrayList<>();
        for(Employee employee:schedule.getEmployees()){
            employeeIds.add(employee.getId());
        }
        scheduleDTO.setEmployeeIds(employeeIds);
        List<Long> petIds=new ArrayList<>();
        for(Pet pet: schedule.getPets()){
            petIds.add(pet.getId());
        }
        scheduleDTO.setPetIds(petIds);
        return scheduleDTO;
    }
}