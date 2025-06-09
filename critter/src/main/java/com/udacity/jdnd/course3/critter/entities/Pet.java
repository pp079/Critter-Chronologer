package com.udacity.jdnd.course3.critter.entities;

import com.udacity.jdnd.course3.critter.pet.PetType;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table
@Data
@NoArgsConstructor
public class Pet implements Serializable {
    @ManyToOne(targetEntity = Customer.class, optional = false)
    private Customer customer;
    private LocalDate birthDate;
    private String notes;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private PetType type;
    private String name;
}