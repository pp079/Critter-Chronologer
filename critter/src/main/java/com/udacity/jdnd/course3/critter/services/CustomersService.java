package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.repositories.CustomersRepository;
import com.udacity.jdnd.course3.critter.repositories.PetsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class CustomersService {
    @Autowired
    private CustomersRepository customersRepository;
    @Autowired
    private PetsRepository petsRepository;

    public Customer saveCustomer(Customer customer, List<Long> petIds) {
        if (petIds != null && !petIds.isEmpty()) {
            List<Pet> associatedPets = new ArrayList<>();
            for (Long id : petIds) {
                associatedPets.add(petsRepository.getOne(id));
            }
            customer.setPets(associatedPets);
        } else {
            customer.setPets(new ArrayList<>());
        }
        return customersRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customerList = customersRepository.findAll();
        return customerList;
    }

    public Customer getCustomerByPetId(long petId) {
        Pet pet = petsRepository.getOne(petId);
        return pet.getCustomer();
    }

}