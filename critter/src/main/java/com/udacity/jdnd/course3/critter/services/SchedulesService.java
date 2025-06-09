package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import com.udacity.jdnd.course3.critter.repositories.CustomersRepository;
import com.udacity.jdnd.course3.critter.repositories.EmployeesRepository;
import com.udacity.jdnd.course3.critter.repositories.PetsRepository;
import com.udacity.jdnd.course3.critter.repositories.SchedulesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
public class SchedulesService {
    @Autowired
    private SchedulesRepository schedulesRepository;
    @Autowired
    private PetsRepository petsRepository;
    @Autowired
    private EmployeesRepository employeesRepository;
    @Autowired
    private CustomersRepository customersRepository;

    public List<Schedule> getAllSchedules() {
        List<Schedule> schedules = schedulesRepository.findAll();
        return schedules;
    }

    public List<Schedule> getAllSchedulesForEmployee(long employeeId) {
        Employee emp = employeesRepository.getOne(employeeId);
        List<Schedule> employeeSchedules = schedulesRepository.getAllByEmployeesContains(emp);
        return employeeSchedules;
    }

    public Schedule saveSchedule(Schedule schedule, List<Long> employeeIds, List<Long> petIds) {
        List<Employee> assignedEmployees = employeesRepository.findAllById(employeeIds);
        List<Pet> assignedPets = petsRepository.findAllById(petIds);
        schedule.setEmployees(assignedEmployees);
        schedule.setPets(assignedPets);
        Schedule savedSchedule = schedulesRepository.save(schedule);
        return savedSchedule;
    }

    public List<Schedule> getAllSchedulesForCustomer(long customerId) {
        Customer customer = customersRepository.getOne(customerId);
        List<Schedule> customerSchedules = schedulesRepository.getAllByPetsIn(customer.getPets());
        return customerSchedules;
    }

    public List<Schedule> getAllSchedulesForPet(long petId) {
        Pet pet = petsRepository.getOne(petId);
        List<Schedule> petSchedules = schedulesRepository.getAllByPetsContains(pet);
        return petSchedules;
    }

}
