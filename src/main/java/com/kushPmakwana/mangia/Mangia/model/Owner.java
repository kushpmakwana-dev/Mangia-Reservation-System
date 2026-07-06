package com.kushPmakwana.mangia.Mangia.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "owner_table"
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ownerName;

    @Column(unique = true, nullable = false)
    private String ownerPhone;

    @Column(unique = true, nullable = false)
    private String ownerEmail;

//    @Column(nullable = false)
//    private String password;

}
