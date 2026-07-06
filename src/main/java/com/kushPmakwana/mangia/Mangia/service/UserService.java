package com.kushPmakwana.mangia.Mangia.service;

import com.kushPmakwana.mangia.Mangia.dto.update.UserUpdateDTO;
import com.kushPmakwana.mangia.Mangia.enums.Role;
import com.kushPmakwana.mangia.Mangia.exceptions.AlreadyExistsException;
import com.kushPmakwana.mangia.Mangia.exceptions.InvalidRoleException;
import com.kushPmakwana.mangia.Mangia.exceptions.ResourcesNotFoundException;
import com.kushPmakwana.mangia.Mangia.model.User;
import com.kushPmakwana.mangia.Mangia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void add(String email, String nonEncryptedPassword, Role role){
        if(userRepository.existsByEmail(email)){
            throw new AlreadyExistsException("User already exists", "USER-SERVICE");
        }

        if(!roleValidityChecker(role)){
            throw new InvalidRoleException("ROLE IS NOT VALID");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(nonEncryptedPassword));
        user.setRole(role);
        userRepository.save(user);
    }

    public void update(String email, UserUpdateDTO updates){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourcesNotFoundException("User Didn't Exists", "USER-SERVICE"));

        if(updates.getEmail() != null &&
                !updates.getEmail().equals(user.getEmail()) &&
                userRepository.existsByEmail(updates.getEmail())
        ){
            throw new AlreadyExistsException("USER EMAIL ALREADY IN USED", "USER-SERVICE");
        }

        if(updates.getPassword()!= null)
            user.setPassword(passwordEncoder.encode(updates.getPassword()));

        if(updates.getEmail() != null)
            user.setEmail(updates.getEmail());

        userRepository.save(user);
    }

    public User getUser(Long id){
        return userRepository.findById(id).orElseThrow(() -> new ResourcesNotFoundException("User Not Found", "USER-SERVICE"));
    }

    /*
    * Helper methods
    */

    private boolean roleValidityChecker(Role role){
        return role.equals(Role.CUSTOMER)
                || role.equals(Role.EMPLOYEE)
                || role.equals(Role.OWNER);
    }
}
