package com.kushPmakwana.mangia.Mangia.dto.response;

import com.kushPmakwana.mangia.Mangia.security.CurrentLoggedInUser;

public record JwtResponse (
        String jwtToken,
        CurrentLoggedInUser login
){
}
