package com.kushPmakwana.mangia.Mangia.repository;

import com.kushPmakwana.mangia.Mangia.model.ReservationSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReservationSettingRepository extends JpaRepository<ReservationSetting, Long> {
    boolean existsByContactEmail(String email);
    Optional<ReservationSetting> findFirstBy();
}
