package com.kushPmakwana.mangia.Mangia.dto.update;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerUpdateDTO {
    @Email(message = "Enter the valid email") private String email;
    private String firstName;
    private String lastName;
    private String password;
    @Size(min = 10, max = 10) private String phoneNumber;
}
