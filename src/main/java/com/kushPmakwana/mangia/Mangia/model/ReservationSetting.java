package com.kushPmakwana.mangia.Mangia.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Table(
        name = "reservation_setting"
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReservationSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalTime openingTime;

    private LocalTime closeTime;

    @Column(nullable = false)
    private int maxGuestsPerReservation;

    @Column(nullable = false)
    private String restaurantName;

    @Column(nullable = false)
    private String contactEmail;

    @Column(nullable = false)
    private String contactPhone;

//    @OneToOne
//    @JoinColumn(name = "user_id")
//    private User owner;
}
