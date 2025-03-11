package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    public List<Schedule> findAllByPets(Pet pet);

    public List<Schedule> findAllByEmployees(Employee employee);

    public List<Schedule> findAllByPets(List<Pet> pets);
}
