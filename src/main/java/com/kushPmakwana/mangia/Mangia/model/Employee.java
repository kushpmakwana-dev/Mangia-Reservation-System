package com.kushPmakwana.mangia.Mangia.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "employee_table"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String employeeFirstName;

    @Column(nullable = false)
    private String employeeLastName;

    @Column(nullable = false, unique = true)
    private String employeeEmail;

    @Column(nullable = false, unique = true)
    private String employeePhone;
//
//    @Column(nullable = false)
//    private String password;
}
