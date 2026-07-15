package com.kushPmakwana.mangia.Mangia.service;

import com.kushPmakwana.mangia.Mangia.dto.request.CustomerRequestDTO;
import com.kushPmakwana.mangia.Mangia.dto.response.CustomerResponseDTO;
import com.kushPmakwana.mangia.Mangia.dto.update.CustomerUpdateDTO;
import com.kushPmakwana.mangia.Mangia.dto.update.UserUpdateDTO;
import com.kushPmakwana.mangia.Mangia.enums.Role;
import com.kushPmakwana.mangia.Mangia.exceptions.AlreadyExistsException;
import com.kushPmakwana.mangia.Mangia.exceptions.InvalidRoleException;
import com.kushPmakwana.mangia.Mangia.exceptions.ResourcesNotFoundException;
import com.kushPmakwana.mangia.Mangia.exceptions.UnmodifiableException;
import com.kushPmakwana.mangia.Mangia.utility.Utils;
import com.kushPmakwana.mangia.Mangia.model.Customer;
import com.kushPmakwana.mangia.Mangia.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CustomerService extends BaseService<Customer, CustomerRepository, CustomerRequestDTO, CustomerResponseDTO>{

    private final UserService userService;


    public CustomerService(CustomerRepository customerRepository,  UserService userService){
        super(customerRepository);
        this.userService = userService;
    }

    @Transactional
    public void registerCustomer(CustomerRequestDTO customerRequestDTO){
        if(repository.existsByEmail(customerRequestDTO.getEmail())){
            throw new AlreadyExistsException(getEntityName(), "EMAIL ALREADY EXISTS");
        }

        if(repository.existsByPhoneNumber(customerRequestDTO.getPhoneNumber())){
            throw new AlreadyExistsException(getEntityName(), "PHONE NUMBER ALREADY EXISTS");
        }

        Customer customer = toEntity(customerRequestDTO);
        userService.add(customer.getEmail(), customerRequestDTO.getPassword(), Role.CUSTOMER);
        repository.save(customer);
    }

    @Transactional
    public void update(CustomerUpdateDTO customerUpdateDTO, Long id){

        var user = Utils.getAuthenticatedCustomer()
                .orElseThrow(() -> new InvalidRoleException("Only Customer are allowed to update"));

        Customer customer = findEntityById(id);

        if(!user.getUsername().equals(customer.getEmail())){
            throw new UnmodifiableException("Cannot modify other's request", getEntityName());
        }

        if(customerUpdateDTO.getEmail() != null &&
                !customer.getEmail().equals(customerUpdateDTO.getEmail()) && repository.existsByEmail(customerUpdateDTO.getEmail())){
            throw new AlreadyExistsException(getEntityName(), "EMAIL ALREADY IN USE");
        }

        if(customerUpdateDTO.getPhoneNumber() != null &&
                !customer.getPhoneNumber().equals(customerUpdateDTO.getPhoneNumber())
                && repository.existsByPhoneNumber(customerUpdateDTO.getPhoneNumber())){
            throw new AlreadyExistsException(getEntityName(), "PHONE NUMBER ALREADY IN USE");
        }

        userService.update(customer.getEmail(), new UserUpdateDTO(customer.getEmail(), customerUpdateDTO.getPassword()));

        if(customerUpdateDTO.getEmail()!=null)
            customer.setEmail(customerUpdateDTO.getEmail());
        if(customerUpdateDTO.getFirstName()!=null)
            customer.setFirstName(customerUpdateDTO.getFirstName());
        if(customerUpdateDTO.getLastName()!=null)
            customer.setSecondName(customerUpdateDTO.getLastName());
        if(customerUpdateDTO.getPhoneNumber()!=null)
            customer.setPhoneNumber(customerUpdateDTO.getPhoneNumber());

        repository.save(customer);

    }

    public Customer retrieveCustomerById(Long id){
        return repository.findById(id).orElseThrow(() -> new ResourcesNotFoundException("Customer not found", getEntityName()));
    }

    @Override
    public CustomerResponseDTO toResponse(Customer entity) {
        return CustomerResponseDTO.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .firstName(entity.getFirstName())
                .secondName(entity.getSecondName())
                .role(Role.CUSTOMER)
                .build();
    }

    @Override
    public Customer toEntity(CustomerRequestDTO request) {
        Customer customer = new Customer();
        customer.setEmail(request.getEmail());
        customer.setFirstName(request.getFirstName());
        customer.setSecondName(request.getSecondName());
        customer.setPhoneNumber(request.getPhoneNumber());
        return customer;
    }

    @Override
    protected String getEntityName() {
        return "CUSTOMER-SERVICE";
    }
}
