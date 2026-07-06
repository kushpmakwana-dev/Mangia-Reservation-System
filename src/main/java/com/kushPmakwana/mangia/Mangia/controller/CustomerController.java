package com.kushPmakwana.mangia.Mangia.controller;

import com.kushPmakwana.mangia.Mangia.dto.request.CustomerRequestDTO;
import com.kushPmakwana.mangia.Mangia.dto.update.UserUpdateDTO;
import com.kushPmakwana.mangia.Mangia.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService service;

        @PostMapping("register")
    public ResponseEntity<?> create(
          @Valid @RequestBody CustomerRequestDTO customerRequestDTO
    ){
        service.registerCustomer(customerRequestDTO);
        return ResponseEntity.ok("USER REGISTERED SUCCESSFULLY");
    }

    @PatchMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateDTO userUpdateDTO
    ){
        service.update(userUpdateDTO, id);
        return ResponseEntity.ok("USER UPDATED SUCCESSFULLY");
    }


}
