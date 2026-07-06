package com.kushPmakwana.mangia.Mangia.dto.response;

import com.kushPmakwana.mangia.Mangia.enums.Role;
import com.kushPmakwana.mangia.Mangia.exceptions.InvalidRoleException;
import com.kushPmakwana.mangia.Mangia.security.UserPrincipal;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class LoginResponse {
    public static Map<String, Object> build(Object principal){
        Map<String, Object> map = new HashMap<>();

        if(principal instanceof UserPrincipal user){
            switch(user.getRole()){
                case CUSTOMER -> {
                    map.put(Role.CUSTOMER.toString(), user.getUser());
                }
                case OWNER -> {
                    map.put(Role.OWNER.toString(), user.getUser());
                }
                default -> throw new InvalidRoleException("Enter Valid role");
            }
        }
        return Map.of("authenticatedUsers", map);
    }
}
