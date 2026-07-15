package com.kushPmakwana.mangia.Mangia.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmployeeRequestDTO {
    @NotBlank(message = "first name cannot be blank")
    private String employeeFirstName;

    @NotBlank(message = "last name cannot be blank")
    private String employeeLastName;

    @NotBlank(message = "password cannot be blank")
    private String password;

    @NotBlank(message = "email cannot be blank")
    private String employeeEmail;

    @NotBlank(message = "Phone number cannot be blank")
    @Size(min = 10, max = 10, message = "Phone number must contain 10 number")
    private String employeePhone;
}
