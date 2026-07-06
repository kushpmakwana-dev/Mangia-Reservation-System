package com.kushPmakwana.mangia.Mangia.model;

import com.kushPmakwana.mangia.Mangia.enums.TableStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Service;

@Entity
@Table(
        name = "restaurant_table"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String tableNumber;

    @Enumerated(EnumType.STRING)
    private TableStatus status;

    @Column(nullable = false)
    private int capacity;
}
