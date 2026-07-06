package com.kushPmakwana.mangia.Mangia.controller;

import com.kushPmakwana.mangia.Mangia.dto.request.ReservationRequestDTO;
import com.kushPmakwana.mangia.Mangia.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class Reservation {

    private final ReservationService service;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<?> reserve(
            @Valid @RequestBody ReservationRequestDTO request
    ){
        return ResponseEntity.ok(service.reserve(request));
    }
}
