package com.kushPmakwana.mangia.Mangia.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OwnerRequestDTO {

    @NotBlank(message = "name cannot be blank")
    private String ownerName;

    @NotBlank(message = "phone cannot be blank")
    @Size(min = 10, max = 10)
    private String ownerPhone;

    @Email(message = "enter a valid email")
    @NotBlank(message = "email cannot be blank")
    private String ownerEmail;

    @NotBlank(message = "password cannot be blank")
    private String password;
}
