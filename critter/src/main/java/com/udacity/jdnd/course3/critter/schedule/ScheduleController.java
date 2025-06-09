package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import com.udacity.jdnd.course3.critter.services.SchedulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    private SchedulesService schedulesService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule newSchedule = new Schedule();
        newSchedule.setDate(scheduleDTO.getDate());
        newSchedule.setActivities(scheduleDTO.getActivities());
        Schedule createdSchedule = schedulesService.saveSchedule(newSchedule, scheduleDTO.getEmployeeIds(),
                scheduleDTO.getPetIds());
        return mapScheduleToDTO(createdSchedule);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> allSchedules = schedulesService.getAllSchedules();
        return allSchedules.stream().map(this::mapScheduleToDTO).collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> petSchedules = schedulesService.getAllSchedulesForPet(petId);
        return petSchedules.stream().map(this::mapScheduleToDTO).collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> employeeSchedules = schedulesService.getAllSchedulesForEmployee(employeeId);
        return employeeSchedules.stream().map(this::mapScheduleToDTO).collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> customerSchedules = schedulesService.getAllSchedulesForCustomer(customerId);
        return customerSchedules.stream().map(this::mapScheduleToDTO).collect(Collectors.toList());
    }

    private ScheduleDTO mapScheduleToDTO(Schedule schedule) {
        ScheduleDTO dto = new ScheduleDTO();
        dto.setId(schedule.getId());
        dto.setDate(schedule.getDate());
        dto.setActivities(schedule.getActivities());
        List<Long> employeeIds = schedule.getEmployees().stream().map(Employee::getId).collect(Collectors.toList());
        List<Long> petIds = schedule.getPets().stream().map(Pet::getId).collect(Collectors.toList());
        dto.setEmployeeIds(employeeIds);
        dto.setPetIds(petIds);
        return dto;
    }
}
