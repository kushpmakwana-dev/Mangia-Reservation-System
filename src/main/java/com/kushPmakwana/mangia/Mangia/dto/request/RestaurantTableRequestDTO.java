package com.kushPmakwana.mangia.Mangia.dto.request;

import com.kushPmakwana.mangia.Mangia.enums.TableStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RestaurantTableRequestDTO {
    @NotBlank(message = "Table null cannot be empty")
    private String tableNumber;

    @NotNull(message = "Table Status needed to be mentioned")
    private TableStatus status;

    @NotNull(message = "Table Capacity must be mentioned")
    private int capacity;
}
