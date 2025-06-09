package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.services.PetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    PetsService petsService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet newPet = new Pet();
        newPet.setType(petDTO.getType());
        newPet.setName(petDTO.getName());
        newPet.setBirthDate(petDTO.getBirthDate());
        newPet.setNotes(petDTO.getNotes());
        Pet savedPet = petsService.savePet(newPet, petDTO.getOwnerId());
        return convertToPetDTO(savedPet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet foundPet = petsService.getPetById(petId);
        return convertToPetDTO(foundPet);
    }

    @GetMapping
    public List<PetDTO> getPets() {
        List<Pet> petList = petsService.getAllPets();
        List<PetDTO> petDTOList = petList.stream().map(this::convertToPetDTO).collect(Collectors.toList());
        return petDTOList;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> ownerPets = petsService.getPetsByCustomerId(ownerId);
        List<PetDTO> ownerPetDTOs = ownerPets.stream().map(this::convertToPetDTO).collect(Collectors.toList());
        return ownerPetDTOs;
    }

    private PetDTO convertToPetDTO(Pet pet) {
        PetDTO dto = new PetDTO();
        dto.setId(pet.getId());
        dto.setName(pet.getName());
        dto.setType(pet.getType());
        dto.setOwnerId(pet.getCustomer().getId());
        dto.setBirthDate(pet.getBirthDate());
        dto.setNotes(pet.getNotes());
        return dto;
    }
}
