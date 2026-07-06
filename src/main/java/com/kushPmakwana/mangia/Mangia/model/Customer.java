package com.kushPmakwana.mangia.Mangia.model;

import com.kushPmakwana.mangia.Mangia.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "customer_table"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String secondName;

    @Column(nullable = false, unique = true)
    private String email;

//    @Column(nullable = false)
//    private String password;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

}
