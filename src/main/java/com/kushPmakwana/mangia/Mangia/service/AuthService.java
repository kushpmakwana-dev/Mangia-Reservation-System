package com.kushPmakwana.mangia.Mangia.service;

import com.kushPmakwana.mangia.Mangia.dto.request.LoginRequestDTO;
import com.kushPmakwana.mangia.Mangia.dto.response.JwtResponse;
import com.kushPmakwana.mangia.Mangia.exceptions.InvalidRoleException;
import com.kushPmakwana.mangia.Mangia.model.User;
import com.kushPmakwana.mangia.Mangia.repository.UserRepository;
import com.kushPmakwana.mangia.Mangia.security.UserPrincipal;
import com.kushPmakwana.mangia.Mangia.security.jwt.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;


@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    public AuthService(AuthenticationManager authenticationManager, UserRepository repository, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = repository;
        this.jwtUtils = jwtUtils;
    }

//    public Authentication login(LoginRequestDTO req,
//                                HttpServletRequest request,
//                                HttpServletResponse response){
//
//        User user = userRepository.findByEmail(req.getEmail()).orElseThrow();
//
//        if(!user.isActive()){
//            throw new InvalidRoleException("USER IS NOT ACTIVE");
//        }
//
//
//
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        req.getEmail(),
//                        req.getPassword()
//                )
//        );
//
//        SecurityContext context = SecurityContextHolder.createEmptyContext();
//        context.setAuthentication(authentication);
//        SecurityContextHolder.setContext(context);
//
//        HttpSessionSecurityContextRepository repository =
//                new HttpSessionSecurityContextRepository();
//
//        repository.saveContext(context, request, response);
//
//        return authentication;
//    }

        public JwtResponse login(LoginRequestDTO req,
                                HttpServletRequest request,
                                HttpServletResponse response) {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtUtils.generateToken(authentication);

            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

            return new JwtResponse(
                    jwt,
                    userPrincipal.getUser()
                );

        }
}
