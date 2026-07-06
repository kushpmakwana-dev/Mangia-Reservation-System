package com.kushPmakwana.mangia.Mangia.dto.request;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalTime;

@Data
public class ReservationSettingRequestDTO {

    @NotNull(message = "Opening time cannot be null")
    private LocalTime openingTime;

    @NotNull(message = "Closing time cannot be null")
    private LocalTime closeTime;

    @NotNull(message = "Please mention the Max Guest field")
    private Integer maxGuestsPerReservation;

    @NotBlank(message = "Restaurant name cannot be empty")
    private String restaurantName;

    @Email(message = "Email is not valid")
    @NotBlank(message = "Email cannot be empty")
    private String contactEmail;

    @NotBlank(message = "Phone Number cannot be empty")
    @Size(min = 10, max = 10, message = "Phone number must contain 10 digits")
    private String contactPhone;
}
