package com.kushPmakwana.mangia.Mangia.controller;

import com.kushPmakwana.mangia.Mangia.dto.request.ReservationRequestDTO;
import com.kushPmakwana.mangia.Mangia.dto.response.ReservationResponseDTO;
import com.kushPmakwana.mangia.Mangia.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService service;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<ReservationResponseDTO> reserve(
            @RequestBody ReservationRequestDTO request
    ){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.reserve(request));
    }

    @PostMapping("/cancel/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<?> cancel(
            @PathVariable Long id
    ){
        service.cancel(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("RESERVATION HAS BEEN CANCELLED");
    }

    @PostMapping("/confirm/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_OWNER', 'ROLE_EMPLOYEE')")
    public ResponseEntity<?> confirm(
            @PathVariable Long id
    ){
        service.confirm(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("RESERVATION HAS BEEN CONFIRMED");
    }

    @PostMapping("/arrived/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_OWNER', 'ROLE_EMPLOYEE')")
    public ResponseEntity<?> arrived(
            @PathVariable Long id
    ){
        service.markedArrived(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("RESERVATION HAS BEEN CONFIRMED");
    }

    @PostMapping("/complete/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_OWNER', 'ROLE_EMPLOYEE')")
    public ResponseEntity<?> complete(
            @PathVariable Long id
    ){
        service.markedAsCompleted(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("RESERVATION HAS BEEN CONFIRMED");
    }
}
