package com.kushPmakwana.mangia.Mangia.repository;

import com.kushPmakwana.mangia.Mangia.enums.TableStatus;
import com.kushPmakwana.mangia.Mangia.model.RestaurantTable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Long> {
    boolean existsByTableNumber(String tableNumber);

    @Query(
            """
            SELECT r FROM RestaurantTable r
            WHERE  (:status IS NULL OR :status = r.status)
            AND (:capacity IS NULL OR :capacity <= r.capacity)
            AND (
                :search IS NULL
                OR LOWER(r.tableNumber) LIKE LOWER(CONCAT('%', :search, '%'))
            )
            """
    )
    Page<RestaurantTable> search(
            @Param("search") String search,
            @Param("status") TableStatus status,
            @Param("capacity") Integer capacity,
            Pageable pageable
    );

    Optional<RestaurantTable> findFirstByCapacityGreaterThanEqualAndStatusOrderByCapacityAsc(int capacity, TableStatus status);

    List<RestaurantTable> findAllByCapacity(int capacity);
}
