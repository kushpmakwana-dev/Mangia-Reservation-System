package com.kushPmakwana.mangia.Mangia.repository;

import com.kushPmakwana.mangia.Mangia.enums.ReservationType;
import com.kushPmakwana.mangia.Mangia.model.Customer;
import com.kushPmakwana.mangia.Mangia.model.Reservation;
import com.kushPmakwana.mangia.Mangia.model.RestaurantTable;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findFirstByBookedByOrderByCreatedOnDesc(Customer customer);

    boolean existsByBookedByAndReservationDateAndReservationTime(
            Customer customer,
            LocalDate date,
            LocalTime time
    );

    boolean existsByBookedByAndReservationDateAndReservationTimeAndReservationType(
            Customer customer,
            LocalDate date,
            LocalTime time,
            ReservationType reservationType
    );

    boolean existsByTableAndReservationDateAndReservationTime(
            RestaurantTable table,
            LocalDate reservationDate,
            LocalTime reservationTime
    );
}
