package com.kushPmakwana.mangia.Mangia.controller;

import com.kushPmakwana.mangia.Mangia.dto.request.EmployeeRequestDTO;
import com.kushPmakwana.mangia.Mangia.dto.update.EmployeeUpdateDTO;
import com.kushPmakwana.mangia.Mangia.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService service;

    @PostMapping
    public ResponseEntity<?> create(
            @Valid @RequestBody EmployeeRequestDTO request
    ){
        service.create(request);
        return ResponseEntity.ok("Employee Added");
    }

    @PatchMapping("{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody EmployeeUpdateDTO update
    ){
        service.update(id, update);
        return ResponseEntity.ok("User has been updated successfully");
    }
}
