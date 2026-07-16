package com.kushPmakwana.mangia.Mangia.repository;

import com.kushPmakwana.mangia.Mangia.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmployeeEmail(String email);

    boolean existsByEmployeeEmail(String email);
    boolean existsByEmployeePhone(String phoneNumber);
}
