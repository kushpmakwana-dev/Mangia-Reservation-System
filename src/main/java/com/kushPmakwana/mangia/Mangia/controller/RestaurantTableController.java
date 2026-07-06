package com.kushPmakwana.mangia.Mangia.controller;

import com.kushPmakwana.mangia.Mangia.dto.request.RestaurantTableRequestDTO;
import com.kushPmakwana.mangia.Mangia.dto.update.RestaurantTableUpdateDTO;
import com.kushPmakwana.mangia.Mangia.enums.TableStatus;
import com.kushPmakwana.mangia.Mangia.service.RestaurantTableService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/management")
public class RestaurantTableController {
    private final RestaurantTableService restaurantTableService;

    @PostMapping
    public ResponseEntity<?> add(
            @Valid @RequestBody RestaurantTableRequestDTO requestDTO
    ){
        restaurantTableService.add(requestDTO);
        return ResponseEntity.ok("TABLE ADDED SUCCESSFULLY");
    }

    @PatchMapping("{id}")
    public ResponseEntity<?> update(
            @RequestBody RestaurantTableUpdateDTO updateDTO,
            @PathVariable Long id
    ){
        restaurantTableService.update(id, updateDTO);
        return ResponseEntity.ok("TABLE HAS BEEN UPDATED");
    }

    @PatchMapping("/booked/{id}")
    public ResponseEntity<?> booked(
            @PathVariable Long id
    ){
        restaurantTableService.setBooked(id);
        return ResponseEntity.ok("TABLE IS BOOKED");
    }

    @PatchMapping("/available/{id}")
    public ResponseEntity<?> available(
            @PathVariable Long id
    ){
        restaurantTableService.setAvailable(id);
        return ResponseEntity.ok("TABLE IS AVAILABLE");
    }

    @PatchMapping("/maintenance/{id}")
    public ResponseEntity<?> underMaintenance(
            @PathVariable Long id
    ){
        restaurantTableService.setUnderMaintenance(id);
        return ResponseEntity.ok("TABLE IS UNDER MAINTENANCE");
    }

    @GetMapping
    public ResponseEntity<?> search(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) TableStatus status,
            @RequestParam(required = false) Integer capacity,
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize
    ){
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

        return ResponseEntity.ok(restaurantTableService.search(
                search,
                status,
                capacity,
                pageable
        ));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> get(
            @PathVariable Long id
    ){
        return ResponseEntity.ok(restaurantTableService.getById(id));
    }


}
