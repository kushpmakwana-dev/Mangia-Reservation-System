package com.kushPmakwana.mangia.Mangia.service;

import com.kushPmakwana.mangia.Mangia.dto.request.ReservationRequestDTO;
import com.kushPmakwana.mangia.Mangia.dto.response.ReservationResponseDTO;
import com.kushPmakwana.mangia.Mangia.enums.ReservationType;
import com.kushPmakwana.mangia.Mangia.exceptions.InvalidRoleException;
import com.kushPmakwana.mangia.Mangia.exceptions.UnmodifiableException;
import com.kushPmakwana.mangia.Mangia.model.RestaurantTable;
import com.kushPmakwana.mangia.Mangia.utility.Utils;
import com.kushPmakwana.mangia.Mangia.model.Reservation;
import com.kushPmakwana.mangia.Mangia.repository.ReservationRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ReservationService extends BaseService<Reservation, ReservationRepository, ReservationRequestDTO, ReservationResponseDTO>{

    private final RestaurantTableService tableService;
    private final ReservationSettingService settings;
    private final CustomerService customerService;

    public ReservationService(ReservationRepository repository, RestaurantTableService tableService, ReservationSettingService settings, CustomerService customerService){
        super(repository);
        this.tableService = tableService;
        this.settings = settings;
        this.customerService = customerService;
    }

    @Transactional
    public ReservationResponseDTO reserve(ReservationRequestDTO request){
        var user = Utils.getAuthenticatedCustomer()
                .orElseThrow(() -> new InvalidRoleException("ONLY CUSTOMER ARE AUTHORIZED TO PERFORM THIS ACTION"));

        // Reservation time must be between the opening and closing time
        boolean isValid = settings.isValidTime(request.getReservationTime());
        if(!isValid){
            throw new UnmodifiableException("RESTAURANT IS UNAVAILABLE DURING " + request.getReservationTime(), getEntityName());
        }

        RestaurantTable table = tableService.fetchAvailableTable(request.getTotalNumberOfPeople());

        Reservation newReservation = toEntity(request);
        newReservation.setTable(table);

        newReservation.setBookedBy(customerService.retrieveCustomerById(user.getUser().getId()));
        Reservation reservation = repository.save(newReservation);

        return toResponse(reservation);

    }

    @Override
    public ReservationResponseDTO toResponse(Reservation entity) {
        return ReservationResponseDTO.builder()
                .id(entity.getId())
                .emailId(entity.getEmailId())
                .phoneNumber(entity.getPhoneNumber())
                .firstName(entity.getFirstName())
                .secondName(entity.getLastName())
                .reservationTime(entity.getReservationTime())
                .reservationDate(entity.getReservationDate())
                .reservationType(entity.getReservationType())
                .validTill(entity.getReservationValidTill())
                .totalNumberOfPeople(entity.getTotalNumberOfPeople())
                .bookedBy(entity.getBookedBy().getFirstName() + " " + entity.getBookedBy().getSecondName())
                .tableNumber(entity.getTable().getTableNumber())
                .build();
    }

    @Override
    public Reservation toEntity(ReservationRequestDTO request) {
        Reservation reservation = new Reservation();
        reservation.setFirstName(request.getFirstName());
        reservation.setLastName(request.getSecondName());
        reservation.setEmailId(request.getEmailId());
        reservation.setReservationDate(request.getReservationDate());
        reservation.setReservationTime(request.getReservationTime());
        reservation.setPhoneNumber(request.getPhoneNumber());
        reservation.setTotalNumberOfPeople(request.getTotalNumberOfPeople());
        reservation.setReservationValidTill(request.getReservationTime().plusMinutes(30).atDate(request.getReservationDate()));
        reservation.setReservationType(request.getReservationType());
        return reservation;
    }

    @Override
    protected String getEntityName() {
        return "RESERVATION ENTITY";
    }
}
