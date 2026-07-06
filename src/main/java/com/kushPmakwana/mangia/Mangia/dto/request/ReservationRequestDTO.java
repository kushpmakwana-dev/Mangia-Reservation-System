package com.kushPmakwana.mangia.Mangia.dto.request;

import com.kushPmakwana.mangia.Mangia.enums.ReservationType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class ReservationRequestDTO {
    @NotBlank (message = "First Name is mandatory")
    private String firstName;

    @NotBlank (message = "Second Name is mandatory")
    private String secondName;

    @NotNull(message = "Reservation Type is mandatory")
    private ReservationType reservationType;

    @NotNull(message = "Please enter total number of people")
    private Integer totalNumberOfPeople;

    @Email(message = "Enter the valid Email Id")
    @NotBlank(message = "Email field cannot be null")
    private String emailId;

    @NotBlank(message = "Phone Number cannot be null")
    @Size(min = 10, max = 10, message = "Phone number should contain 10 digits")
    private String phoneNumber;

    @NotNull(message = "Reservation Date is mandatory")
    private LocalDate reservationDate;

    @NotNull(message = "Reservation Time is mandatory")
    private LocalTime reservationTime;


}
