package com.kushPmakwana.mangia.Mangia.security;

import com.kushPmakwana.mangia.Mangia.exceptions.InvalidRoleException;
import com.kushPmakwana.mangia.Mangia.model.Customer;
import com.kushPmakwana.mangia.Mangia.model.Employee;
import com.kushPmakwana.mangia.Mangia.model.Owner;
import com.kushPmakwana.mangia.Mangia.model.User;
import com.kushPmakwana.mangia.Mangia.repository.CustomerRepository;
import com.kushPmakwana.mangia.Mangia.repository.EmployeeRepository;
import com.kushPmakwana.mangia.Mangia.repository.OwnerRepository;
import com.kushPmakwana.mangia.Mangia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final OwnerRepository ownerRepository;
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User Name not found"));

        switch (user.getRole()){
            case CUSTOMER -> {
                Customer customer = customerRepository.findByEmail(user.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User Name not found"));

                return new UserPrincipal(
                        user.getId(),
                        user.getEmail(),
                        user.getPassword(),
                        user.getRole(),
                        new CurrentLoggedInUser(
                                customer.getId(),
                                username,
                                customer.getFirstName() + " " + customer.getSecondName(),
                                user.getRole()
                        )
                );
            }

            case OWNER -> {
                Owner owner = ownerRepository.findByOwnerEmail(user.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User Name not found"));
                return new UserPrincipal(
                        user.getId(),
                        user.getEmail(),
                        user.getPassword(),
                        user.getRole(),
                        new CurrentLoggedInUser(
                                owner.getId(),
                                username,
                                owner.getOwnerName(),
                                user.getRole()
                        )
                );
            }

            case EMPLOYEE -> {
                Employee employee = employeeRepository.findByEmployeeEmail(user.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User Name not found"));
                return new UserPrincipal(
                        user.getId(),
                        user.getEmail(),
                        user.getPassword(),
                        user.getRole(),
                        new CurrentLoggedInUser(
                                employee.getId(),
                                username,
                                employee.getEmployeeFirstName() + " " + employee.getEmployeeLastName(),
                                user.getRole()
                        )
                );
            }

            case ADMIN -> {
                return new UserPrincipal(
                        user.getId(),
                        user.getEmail(),
                        user.getPassword(),
                        user.getRole(),
                        new CurrentLoggedInUser(
                                null,
                                username,
                                "ADMINISTRATOR",
                                user.getRole()
                        )
                );
            }

            default -> throw new InvalidRoleException("NO " + user.getRole() + " SUCH ROLE");
        }
    }
}
