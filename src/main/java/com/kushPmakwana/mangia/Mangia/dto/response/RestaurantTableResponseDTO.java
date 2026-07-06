package com.kushPmakwana.mangia.Mangia.dto.response;

import com.kushPmakwana.mangia.Mangia.enums.TableStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RestaurantTableResponseDTO {
    private Long id;
    private String tableNumber;
    private TableStatus status;
    private int capacity;
}
