package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    @Autowired
    PetService petService;

    @Autowired
    CustomerService customerService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Long id = petService.save(convertPetDTOToPet(petDTO));
        Pet pet1=petService.findPetById(id);
        Customer owner = customerService.findCustomerById(pet1.getOwner().getId());
        Boolean bool = false;
        List<Pet> pets =owner.getPets();
        if(pets != null){
            for(Pet pet: pets){
                if(pet.getId()==id){
                    bool = true;
                    break;
                }
            }
        }
        if(!bool){
            if(pets == null){
                pets = new ArrayList<>();
            }
            pets.add(pet1);
            owner.setPets(pets);
            customerService.save(owner);
        }
        return convertPetToPetDTO(petService.findPetById(id));
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {

        return convertPetToPetDTO(petService.findPetById(petId));
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> pets = petService.getAllPets();
        List<PetDTO> petDTOS = new ArrayList<>();
        for (Pet pet:pets){
            petDTOS.add(convertPetToPetDTO(pet));
        }
        return petDTOS;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List <Pet> pets = petService.findAllPetsByOwnerId(ownerId);
        List<PetDTO> petDTOS = new ArrayList<>();
        for(Pet pet:pets){
            petDTOS.add(convertPetToPetDTO(pet));
        }
        return petDTOS;
    }

    private Pet convertPetDTOToPet(PetDTO petDTO){
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO,pet);
        pet.setOwner(customerService.findCustomerById(petDTO.getOwnerId()));
        return pet;
    }

    private PetDTO convertPetToPetDTO(Pet pet){
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet,petDTO);
        petDTO.setOwnerId(pet.getOwner().getId());
        return petDTO;
    }
}
