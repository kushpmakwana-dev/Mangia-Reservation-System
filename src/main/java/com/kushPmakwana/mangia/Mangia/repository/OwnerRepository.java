package com.kushPmakwana.mangia.Mangia.repository;

import com.kushPmakwana.mangia.Mangia.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
    Optional<Owner> findByOwnerEmail(String email);

    boolean existsByOwnerEmail(String email);

    boolean existsByOwnerPhone(String phone);
}
