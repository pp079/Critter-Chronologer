package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.repositories.CustomersRepository;
import com.udacity.jdnd.course3.critter.repositories.PetsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Transactional
@Service
public class PetsService {
    @Autowired
    private PetsRepository petsRepository;
    @Autowired
    private CustomersRepository customersRepository;

    public Pet savePet(Pet pet, long ownerId) {
        Customer owner = customersRepository.getOne(ownerId);
        pet.setCustomer(owner);
        Pet savedPet = petsRepository.save(pet);
        owner.insertPet(savedPet);
        customersRepository.save(owner);
        return savedPet;
    }

    public List<Pet> getPetsByCustomerId(long customerId) {
        List<Pet> pets = petsRepository.getAllByCustomerId(customerId);
        return pets;
    }

    public List<Pet> getAllPets() {
        List<Pet> allPets = petsRepository.findAll();
        return allPets;
    }

    public Pet getPetById(long petId) {
        Pet pet = petsRepository.getOne(petId);
        return pet;
    }

}
