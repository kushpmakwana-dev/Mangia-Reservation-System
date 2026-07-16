package com.kushPmakwana.mangia.Mangia.service;

import com.kushPmakwana.mangia.Mangia.dto.request.OwnerRequestDTO;
import com.kushPmakwana.mangia.Mangia.dto.response.OwnerResponseDTO;
import com.kushPmakwana.mangia.Mangia.dto.update.OwnerUpdateDTO;
import com.kushPmakwana.mangia.Mangia.dto.update.CustomerUpdateDTO;
import com.kushPmakwana.mangia.Mangia.dto.update.UserUpdateDTO;
import com.kushPmakwana.mangia.Mangia.enums.Role;
import com.kushPmakwana.mangia.Mangia.exceptions.AlreadyExistsException;
import com.kushPmakwana.mangia.Mangia.exceptions.CustomException;
import com.kushPmakwana.mangia.Mangia.exceptions.InvalidRoleException;
import com.kushPmakwana.mangia.Mangia.model.Owner;
import com.kushPmakwana.mangia.Mangia.repository.OwnerRepository;
import com.kushPmakwana.mangia.Mangia.utility.Utils;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class OwnerService extends BaseService<Owner, OwnerRepository, OwnerRequestDTO, OwnerResponseDTO> {

    private final UserService userService;

    public OwnerService(OwnerRepository repository, UserService userService) {
        super(repository);
        this.userService = userService;
    }

    @Transactional
    public void add(OwnerRequestDTO request){
        var user = Utils.getAuthenticatedAdmin()
                .orElseThrow(() -> new InvalidRoleException("ONLY ADMIN IS ALLOWED TO ADD OWNER"));

        if(repository.existsByOwnerEmail(request.getOwnerEmail())){
            throw new AlreadyExistsException("Owner with email already exists", getEntityName());
        }

        if(repository.existsByOwnerPhone(request.getOwnerPhone())) {
            throw new AlreadyExistsException("Owner with phone already exists", getEntityName());
        }

        userService.add(request.getOwnerEmail(), request.getPassword(), Role.OWNER);
        Owner newOwner = toEntity(request);
        repository.save(newOwner);
    }

    @Transactional
    public void update(Long id, OwnerUpdateDTO update){
        var user = Utils.getAuthenticatedOwnerOrAdmin()
                .orElseThrow(() -> new InvalidRoleException("ONLY OWNER IS ALLOWED TO UPDATE"));

        Owner owner = findEntityById(id);

        String oldEmail = owner.getOwnerEmail();

        if(!user.getUsername().equals(owner.getOwnerEmail())){
            throw new CustomException(getEntityName(), "You cannot modify others detail");
        }

        if(update.getOwnerEmail() != null
                && !owner.getOwnerEmail().equals(update.getOwnerEmail())
                && repository.existsByOwnerEmail(update.getOwnerEmail())){
            throw new AlreadyExistsException(getEntityName(), "Email already in use");
        }

        if(update.getOwnerPhone() != null
                && !owner.getOwnerPhone().equals(update.getOwnerPhone())
                && repository.existsByOwnerPhone(update.getOwnerPhone())){
            throw new AlreadyExistsException(getEntityName(), "Phone already in use");
        }

        if(update.getOwnerEmail() != null || update.getOwnerPhone() != null){
            CustomerUpdateDTO updateDTO = new CustomerUpdateDTO();
            updateDTO.setEmail(update.getOwnerEmail());
            updateDTO.setPhoneNumber(update.getOwnerPhone());
            userService.update(oldEmail, new UserUpdateDTO(update.getOwnerEmail(), updateDTO.getPassword()));
        }

        if(update.getOwnerName() != null)
            owner.setOwnerName(update.getOwnerName());

        if(update.getOwnerEmail() != null)
            owner.setOwnerEmail(update.getOwnerEmail());

        if(update.getOwnerPhone() != null)
            owner.setOwnerPhone(update.getOwnerPhone());

        repository.save(owner);
    }

    @Override
    public OwnerResponseDTO toResponse(Owner entity) {
       return OwnerResponseDTO.builder()
               .id(entity.getId())
               .ownerName(entity.getOwnerName())
               .ownerEmail(entity.getOwnerEmail())
               .build();
    }

    @Override
    public Owner toEntity(OwnerRequestDTO request) {
        Owner owner = new Owner();
        owner.setOwnerName(request.getOwnerName());
        owner.setOwnerEmail(request.getOwnerEmail());
        owner.setOwnerPhone(request.getOwnerPhone());
        return owner;
    }

    @Override
    protected String getEntityName() {
        return "OWNER-SERVICE";
    }
}
