package com.udacity.jdnd.course3.critter.entities;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.Set;

@Entity
@Table
@Data
@NoArgsConstructor
public class Employee implements Serializable {
    @ElementCollection
    private Set<DayOfWeek> daysAvailable;

    @ElementCollection
    private Set<EmployeeSkill> skills;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
}