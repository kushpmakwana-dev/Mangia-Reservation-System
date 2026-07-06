package com.kushPmakwana.mangia.Mangia.service;

import com.kushPmakwana.mangia.Mangia.dto.request.ReservationSettingRequestDTO;
import com.kushPmakwana.mangia.Mangia.dto.response.ReservationSettingResponseDTO;
import com.kushPmakwana.mangia.Mangia.exceptions.InvalidRoleException;
import com.kushPmakwana.mangia.Mangia.exceptions.UnmodifiableException;
import com.kushPmakwana.mangia.Mangia.model.ReservationSetting;
import com.kushPmakwana.mangia.Mangia.repository.ReservationRepository;
import com.kushPmakwana.mangia.Mangia.repository.ReservationSettingRepository;
import com.kushPmakwana.mangia.Mangia.utility.Utils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@Service
public class ReservationSettingService extends BaseService<ReservationSetting, ReservationSettingRepository, ReservationSettingRequestDTO, ReservationSettingResponseDTO>{

    public ReservationSettingService(ReservationSettingRepository repository){
        super(repository);
    }

    public void add(ReservationSettingRequestDTO requestDTO){
//        var user = Utils.getAuthenticatedOwner()
//                .orElseThrow(()-> new InvalidRoleException("ONLY OWNER IS ALLOWED TO ADD SETTINGS"));

        Optional<ReservationSetting> setting = repository.findFirstBy();

        if(setting.isPresent()){
            throw new UnmodifiableException("Setting can only be configured once", getEntityName());
        }

        // Close time cannot be before open time
        if(requestDTO.getOpeningTime().isAfter(requestDTO.getCloseTime())){
            throw new UnmodifiableException("Open time cannot be after closing time", getEntityName());
        }

        // If the setting already exists
        if(repository.existsByContactEmail(requestDTO.getContactEmail())){
            throw new UnmodifiableException("Setting is already added", getEntityName());
        }

        ReservationSetting settings = toEntity(requestDTO);
        repository.save(settings);
    }

    public boolean isValidTime(LocalTime bookingTime){
        ReservationSetting setting = repository.findFirstBy()
                .orElseThrow(() ->
                        new RuntimeException("Reservation settings not configured"));
        if(bookingTime.isAfter(setting.getCloseTime())
                || bookingTime.isBefore(setting.getOpeningTime())){
            return false;
        }
        return true;
    }

    @Override
    public ReservationSettingResponseDTO toResponse(ReservationSetting entity) {
        return ReservationSettingResponseDTO.builder()
                .id(entity.getId())
                .openingTime(entity.getOpeningTime())
                .closeTime(entity.getCloseTime())
                .contactEmail(entity.getContactEmail())
                .contactPhone(entity.getContactPhone())
                .restaurantName(entity.getRestaurantName())
                .maxGuestsPerReservation(entity.getMaxGuestsPerReservation())
                .build();
    }

    @Override
    public ReservationSetting toEntity(ReservationSettingRequestDTO request) {
        ReservationSetting entity = new ReservationSetting();
        entity.setRestaurantName(request.getRestaurantName());
        entity.setOpeningTime(request.getOpeningTime());
        entity.setCloseTime(request.getCloseTime());
        entity.setContactEmail(request.getContactEmail());
        entity.setContactPhone(request.getContactPhone());
        entity.setMaxGuestsPerReservation(request.getMaxGuestsPerReservation());
        return entity;
    }

    @Override
    protected String getEntityName() {
        return "RESERVATION SETTING";
    }
}
