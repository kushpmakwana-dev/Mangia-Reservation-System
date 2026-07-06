package com.kushPmakwana.mangia.Mangia.dto.response;


import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;

@Data
@Builder
public class ReservationSettingResponseDTO {
    private Long id;
    private LocalTime openingTime;
    private LocalTime closeTime;
    private Integer maxGuestsPerReservation;
    private String restaurantName;
    private String contactEmail;
    private String contactPhone;
}
