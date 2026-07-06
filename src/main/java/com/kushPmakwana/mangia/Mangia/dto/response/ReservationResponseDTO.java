package com.kushPmakwana.mangia.Mangia.dto.response;

import com.kushPmakwana.mangia.Mangia.enums.ReservationType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
public class ReservationResponseDTO {
    private Long id;
    private String firstName;
    private String secondName;
    private ReservationType reservationType;
    private Integer totalNumberOfPeople;
    private String emailId;
    private String phoneNumber;
    private LocalDate reservationDate;
    private LocalTime reservationTime;
    private LocalDateTime validTill;
    private String tableNumber;
    private String bookedBy;
}
