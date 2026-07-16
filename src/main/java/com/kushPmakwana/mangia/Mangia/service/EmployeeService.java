package com.kushPmakwana.mangia.Mangia.service;

import com.kushPmakwana.mangia.Mangia.dto.request.EmployeeRequestDTO;
import com.kushPmakwana.mangia.Mangia.dto.response.EmployeeResponseDTO;
import com.kushPmakwana.mangia.Mangia.dto.update.EmployeeUpdateDTO;
import com.kushPmakwana.mangia.Mangia.dto.update.UserUpdateDTO;
import com.kushPmakwana.mangia.Mangia.enums.Role;
import com.kushPmakwana.mangia.Mangia.exceptions.AlreadyExistsException;
import com.kushPmakwana.mangia.Mangia.exceptions.InvalidRoleException;
import com.kushPmakwana.mangia.Mangia.exceptions.ResourcesNotFoundException;
import com.kushPmakwana.mangia.Mangia.exceptions.UnmodifiableException;
import com.kushPmakwana.mangia.Mangia.model.Employee;
import com.kushPmakwana.mangia.Mangia.repository.EmployeeRepository;
import com.kushPmakwana.mangia.Mangia.utility.Utils;
import jakarta.transaction.Transactional;
import com.kushPmakwana.mangia.Mangia.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService extends BaseService<Employee, EmployeeRepository, EmployeeRequestDTO, EmployeeResponseDTO>{

    private final EmployeeMapper mapper;

    @Autowired
    private UserService userService;

    public EmployeeService(EmployeeRepository repository,  EmployeeMapper mapper){
        super(repository);
        this.mapper = mapper;
    }

    @Transactional
    public void create(EmployeeRequestDTO request){
        var user = Utils.getAuthenticatedOwner()
                            .orElseThrow(()-> new InvalidRoleException("Only Owner is allowed to add the Employee"));

        if(repository.existsByEmployeeEmail(request.getEmployeeEmail())){
            throw new AlreadyExistsException(getEntityName(), "Employee with email already exists");
        }

        if(repository.existsByEmployeePhone(request.getEmployeePhone())){
            throw new AlreadyExistsException(getEntityName(), "Phone number already in use");
        }

        userService.add(request.getEmployeeEmail(), request.getPassword(), Role.EMPLOYEE);

        Employee employee = toEntity(request);
        repository.save(employee);

    }

    @Transactional
    public void update(Long id, EmployeeUpdateDTO request){
        var user = Utils.getOwnerOrEmployee()
                .orElseThrow(()-> new InvalidRoleException("Only Owner is allowed to add the Employee"));

        boolean isEmployee = user.getRole() == Role.EMPLOYEE;

        if(isEmployee){
            if(!id.equals(user.getUser().getId())){
                throw new UnmodifiableException("Employee is only allowed to modify their own request", getEntityName());
            }
        }

        Employee employee = findEntityById(id);

        if(request.getEmployeeEmail() != null || request.getPassword() != null){
            UserUpdateDTO updates = new UserUpdateDTO(request.getEmployeeEmail(), request.getPassword());
            userService.update(employee.getEmployeeEmail(), updates);

            if(request.getEmployeeEmail() != null)
                employee.setEmployeeEmail(request.getEmployeeEmail());
        }

        if(request.getEmployeePhone() != null &&
            !employee.getEmployeePhone().equals(request.getEmployeePhone()) &&
                    repository.existsByEmployeePhone(request.getEmployeePhone())) {
            throw new AlreadyExistsException(getEntityName(), "Phone number already exists");
        }

        if(request.getEmployeeFirstName() != null) employee.setEmployeeFirstName(request.getEmployeeFirstName());
        if(request.getEmployeeLastName() != null) employee.setEmployeeLastName(request.getEmployeeLastName());
        if(request.getEmployeePhone() != null) employee.setEmployeePhone(request.getEmployeePhone());

        repository.save(employee);

    }

    @Override
    public EmployeeResponseDTO toResponse(Employee entity) {
        return mapper.toResponse(entity);
    }

    @Override
    public Employee toEntity(EmployeeRequestDTO request) {
        return mapper.toEntity(request);
    }

    @Override
    protected String getEntityName() {
        return "EMPLOYEE-SERVICE";
    }
}
