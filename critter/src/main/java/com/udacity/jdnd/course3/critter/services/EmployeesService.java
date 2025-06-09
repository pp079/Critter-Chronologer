package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.repositories.EmployeesRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Transactional
@Service
public class EmployeesService {
    @Autowired
    private EmployeesRepository employeesRepository;

    public void setEmployeeAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {
        Employee existingEmployee = employeesRepository.getOne(employeeId);
        existingEmployee.setDaysAvailable(daysAvailable);
        employeesRepository.save(existingEmployee);
    }

    public Employee getEmployeeById(long employeeId) {
        Employee employee = employeesRepository.getOne(employeeId);
        return employee;
    }

    public Employee saveEmployee(Employee employee) {
        Employee savedEmployee = employeesRepository.save(employee);
        return savedEmployee;
    }

    public List<Employee> getEmployeesForService(LocalDate date, Set<EmployeeSkill> skills) {
        List<Employee> availableEmployees = employeesRepository.getAllByDaysAvailableContains(date.getDayOfWeek());
        List<Employee> matchingEmployees = new ArrayList<>();
        for (Employee employee : availableEmployees) {
            if (employee.getSkills().containsAll(skills)) {
                matchingEmployees.add(employee);
            }
        }
        return matchingEmployees;
    }

}
