package com.kushPmakwana.mangia.Mangia.security;

import com.kushPmakwana.mangia.Mangia.enums.Role;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserPrincipal implements UserDetails {

    private final Long userId;
    private final String username;
    private final String password;
    private final Role role;
    private final CurrentLoggedInUser user;

    public UserPrincipal(Long userId, String username, String password, Role role, CurrentLoggedInUser user){
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_"+role));
    }

    @Override
    public @Nullable String getPassword() {
        return password;
    }

    public Long getUserId(){
        return userId;
    }

    public CurrentLoggedInUser getUser(){
        return user;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public Role getRole(){
        return role;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
