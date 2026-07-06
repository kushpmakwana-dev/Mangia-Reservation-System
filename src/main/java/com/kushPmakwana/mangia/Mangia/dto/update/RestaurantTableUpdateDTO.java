package com.kushPmakwana.mangia.Mangia.dto.update;

import com.kushPmakwana.mangia.Mangia.enums.TableStatus;
import lombok.Data;

@Data
public class RestaurantTableUpdateDTO {
    private String tableNumber;
    private TableStatus status;
    private Integer capacity;
}
