package com.kushPmakwana.mangia.Mangia.controller;

import com.kushPmakwana.mangia.Mangia.dto.request.LoginRequestDTO;
import com.kushPmakwana.mangia.Mangia.dto.response.LoginResponse;
import com.kushPmakwana.mangia.Mangia.security.UserPrincipal;
import com.kushPmakwana.mangia.Mangia.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<?> login(
           @Valid @RequestBody LoginRequestDTO req,
           HttpServletRequest request,
           HttpServletResponse response
    ){
        Authentication authentication = authService.login(req, request, response);
        return ResponseEntity.ok(LoginResponse.build(authentication.getPrincipal()));
    }
}
