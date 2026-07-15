package com.kushPmakwana.mangia.Mangia.dto.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmployeeUpdateDTO {

    private String employeeFirstName;
    private String employeeLastName;
    private String password;
    private String employeeEmail;
    private String employeePhone;
}
