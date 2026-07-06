package com.kushPmakwana.mangia.Mangia.model;

import com.kushPmakwana.mangia.Mangia.enums.ReservationStatus;
import com.kushPmakwana.mangia.Mangia.enums.ReservationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(
        name = "reservation_table"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Details of the person who books the table
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "customer_id"
    )
    private Customer bookedBy;

    @Enumerated(EnumType.STRING)
    private ReservationType reservationType;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String emailId;

    @Column(nullable = false)
    @Size(min = 10, max = 10)
    private String phoneNumber;

    @Column(nullable = false)
    private int totalNumberOfPeople;

    // Reservation related details
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "table_id"
    )
    private RestaurantTable table;

    @Column(nullable = false)
    private LocalDate reservationDate;

    @Column(nullable = false)
    private LocalTime reservationTime;

    // This will be valid till 30 mins from the reservation time
    private LocalDateTime reservationValidTill;

    private LocalDateTime createdOn;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status = ReservationStatus.PENDING;

    @PrePersist
    private void setCreationTime(){
        this.createdOn = LocalDateTime.now();
    }

}
