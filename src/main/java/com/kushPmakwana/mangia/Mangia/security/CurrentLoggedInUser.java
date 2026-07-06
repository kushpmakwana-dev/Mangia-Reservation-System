package com.kushPmakwana.mangia.Mangia.security;

import com.kushPmakwana.mangia.Mangia.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CurrentLoggedInUser {
    private Long id;
    private String email;
    private String name;
    private Role role;
}
