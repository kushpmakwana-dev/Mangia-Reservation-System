package com.kushPmakwana.mangia.Mangia.repository;

import com.kushPmakwana.mangia.Mangia.enums.ReservationStatus;
import com.kushPmakwana.mangia.Mangia.enums.ReservationType;
import com.kushPmakwana.mangia.Mangia.enums.TableStatus;
import com.kushPmakwana.mangia.Mangia.model.Customer;
import com.kushPmakwana.mangia.Mangia.model.Reservation;
import com.kushPmakwana.mangia.Mangia.model.RestaurantTable;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findFirstByBookedByOrderByCreatedOnDesc(Customer customer);

    boolean existsByBookedByAndReservationDateAndReservationTime(
            Customer customer,
            LocalDate date,
            LocalTime time
    );

    @Query(
            """
            SELECT r FROM Reservation r
            JOIN r.table t
            WHERE (:status IS NULL OR r.status = :status)
            AND (:date IS NULL OR r.reservationDate = :date)
            AND (:totalNumberOfPeople IS NULL OR r.table.capacity >= :totalNumberOfPeople)
            AND(:search IS NULL
                OR LOWER(CONCAT(r.firstName, ' ', r.lastName)) LIKE LOWER(CONCAT('%', :search, '%'))
                OR t.tableNumber LIKE CONCAT('%', :search, '%')
            )
            ORDER BY r.reservationDate DESC,
             r.reservationTime DESC
            """
    )
    Page<Reservation> search(
            @Param("search") String search,
            @Param("status") ReservationStatus status,
            @Param("date") LocalDate date,
            @Param("totalNumberOfPeople") int totalNumberOfPeople,
            Pageable pageable
    );

    @Query(
            """
            SELECT r FROM Reservation r
            WHERE r.bookedBy.id = :id
            AND (:status IS NULL OR r.status = :status)
            AND (:date IS NULL OR r.reservationDate = :date)
            ORDER BY r.reservationDate DESC, r.reservationTime DESC
            """
    )
    Page<Reservation> searchCustomerReservations(
            @Param("id") Long id,
            @Param("status") ReservationStatus status,
            @Param("date") LocalDate date,
            Pageable pageable
    );

    boolean existsByBookedByAndReservationDateAndReservationTimeAndReservationType(
            Customer customer,
            LocalDate date,
            LocalTime time,
            ReservationType reservationType
    );

    boolean existsByTableAndReservationDateAndReservationTimeAndStatusIn(
            RestaurantTable table,
            LocalDate reservationDate,
            LocalTime reservationTime,
            Collection<ReservationStatus> statuses
    );
}
