package com.kushPmakwana.mangia.Mangia.dto.update;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserUpdateDTO {
    private String email;
    private String password;
}
