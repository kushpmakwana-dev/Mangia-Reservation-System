package com.kushPmakwana.mangia.Mangia.controller;

import com.kushPmakwana.mangia.Mangia.dto.request.ReservationSettingRequestDTO;
import com.kushPmakwana.mangia.Mangia.service.ReservationSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservation-setting")
@RequiredArgsConstructor
public class ReservationSettingController {

    private final ReservationSettingService service;

    @PostMapping
    public ResponseEntity<?> add(
            @RequestBody ReservationSettingRequestDTO request
    ){
        service.add(request);
        return ResponseEntity.ok("Setting added successfully");
    }
}
