package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

    @Autowired
    PetRepository petRepository;

    public Long save(Pet pet){
        return petRepository.save(pet).getId();
    }

    public Pet findPetById(Long id){
        return petRepository.findById(id).orElse(null);
    }

    public List<Pet> getAllPets(){
        return petRepository.findAll();
    }

    public List<Pet> findAllPetsByOwnerId(Long ownerId){
        return petRepository.findAllByOwnerId(ownerId);
    }

    public List<Pet> findPetsByIds(List<Long> ids){
        return petRepository.findAllById(ids);
    }
}
