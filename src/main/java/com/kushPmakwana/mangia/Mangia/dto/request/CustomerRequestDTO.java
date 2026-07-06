package com.kushPmakwana.mangia.Mangia.dto.request;

import com.kushPmakwana.mangia.Mangia.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequestDTO {

    @NotBlank(message = "first name cannot be blank")
    private String firstName;

    @NotBlank(message = "first name cannot be blank")
    private String secondName;

    @Email
    @NotBlank(message = "Email cannot be null")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, message = "Password must contain at least 6 character")
    private String password;

    @NotBlank(message = "Phone Number cannot be blank")
    @Size(min = 10, max = 10, message = "Phone number must contain 10 numbers")
    private String phoneNumber;

}
