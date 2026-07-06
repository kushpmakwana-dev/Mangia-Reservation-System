package com.kushPmakwana.mangia.Mangia.utility;

import com.kushPmakwana.mangia.Mangia.enums.Role;
import com.kushPmakwana.mangia.Mangia.security.UserPrincipal;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@NoArgsConstructor
public class Utils {
    public static Optional<UserPrincipal> getPrincipal(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || !(authentication.getPrincipal() instanceof UserPrincipal)){
            return Optional.empty();
        }

        return Optional.of((UserPrincipal)authentication.getPrincipal());
    }

    public static Optional<UserPrincipal> getAuthenticatedCustomer(){
        return getPrincipal()
                .filter(principal ->
                        Role.CUSTOMER.equals(principal.getRole()));
    }


    public static Optional<UserPrincipal> getAuthenticatedOwner() {
        return getPrincipal()
                .filter(principal ->
                        Role.OWNER.equals(principal.getRole()));
    }

    public static Optional<UserPrincipal> getOwnerOrEmployee(){
        return getPrincipal()
                .filter(principal ->
                        Role.OWNER.equals(principal.getRole()) ||
                                Role.EMPLOYEE.equals(principal.getRole()));
    }

    public static Optional<UserPrincipal> getAuthenticatedAdmin(){
        return getPrincipal()
                .filter(principal ->
                        Role.ADMIN.equals(principal.getRole()));
    }
}
