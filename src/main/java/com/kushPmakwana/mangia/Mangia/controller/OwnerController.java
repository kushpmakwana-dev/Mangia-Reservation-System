package com.kushPmakwana.mangia.Mangia.controller;

import com.kushPmakwana.mangia.Mangia.dto.request.OwnerRequestDTO;
import com.kushPmakwana.mangia.Mangia.dto.update.OwnerUpdateDTO;
import com.kushPmakwana.mangia.Mangia.service.OwnerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/owner")
@RequiredArgsConstructor
public class OwnerController {
    private final OwnerService service;

    @PostMapping
    public ResponseEntity<?> add(
            @Valid @RequestBody OwnerRequestDTO requestDTO
    ){
        service.add(requestDTO);
        return ResponseEntity.ok("Owner Added Successfully");
    }

    @PatchMapping("{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody OwnerUpdateDTO update
    ){
        service.update(id, update);
        return ResponseEntity.ok("Owner Updated Successfully");
    }
}
