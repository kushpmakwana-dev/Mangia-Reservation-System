package com.kushPmakwana.mangia.Mangia.service;

import com.kushPmakwana.mangia.Mangia.dto.request.ReservationRequestDTO;
import com.kushPmakwana.mangia.Mangia.dto.response.ReservationResponseDTO;
import com.kushPmakwana.mangia.Mangia.enums.ReservationType;
import com.kushPmakwana.mangia.Mangia.exceptions.InvalidRoleException;
import com.kushPmakwana.mangia.Mangia.exceptions.TableNotAvailableException;
import com.kushPmakwana.mangia.Mangia.exceptions.UnAuthorizedException;
import com.kushPmakwana.mangia.Mangia.exceptions.UnmodifiableException;
import com.kushPmakwana.mangia.Mangia.model.Customer;
import com.kushPmakwana.mangia.Mangia.model.RestaurantTable;
import com.kushPmakwana.mangia.Mangia.utility.Utils;
import com.kushPmakwana.mangia.Mangia.model.Reservation;
import com.kushPmakwana.mangia.Mangia.repository.ReservationRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

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
        var user = Utils.getAuthenticatedCustomer().orElseThrow(() -> new UnAuthorizedException("Only Customer can reserve the table"));

        Customer customer = customerService.retrieveCustomerById(user.getUser().getId());

        // User is allowed to book only during the Restaurant timing
        if(!settings.isValidTime(request.getReservationTime())){
            throw new UnmodifiableException("Restaurant Reservation are close for that timing", getEntityName());
        }

        // Self reservation with same date and same time shouldn't be existing
        if(repository.existsByBookedByAndReservationDateAndReservationTimeAndReservationType(
                customer,
                request.getReservationDate(),
                request.getReservationTime(),
                ReservationType.SELF
        )){
            throw new UnmodifiableException("You already have Reservation " + request.getReservationDate() + "-" + request.getReservationTime(), getEntityName());
        }

        // If the maximum number of guest in setting exceed number of guest in request then throw an exceptions
        if(settings.maxAllowedOfGuests() < request.getTotalNumberOfPeople()){
            throw new UnmodifiableException("Apologies! We don't have enough space for extra guests", getEntityName());
        }

        List<RestaurantTable> availableTable = tableService.fetchAllTableByCapacity(request.getTotalNumberOfPeople());

        RestaurantTable table = fetchValidTable(availableTable, request.getReservationDate(), request.getReservationTime());

        Reservation reservation = toEntity(request);
        reservation.setBookedBy(customer);
        reservation.setTable(table);
        Reservation newReservation = repository.save(reservation);

        return toResponse(newReservation);
    }


    /*
    * Helper Methods
    * */

    private RestaurantTable fetchValidTable(
            List<RestaurantTable> tables,
            LocalDate reservationDate,
            LocalTime reservationTime
    ){
        if(tables.isEmpty()) throw new TableNotAvailableException("NO TABLE WITH REQUIRED CAPACITY IS AVAILABLE");

        for(RestaurantTable table : tables){
            boolean reserved = repository.existsByTableAndReservationDateAndReservationTime(
                    table,
                    reservationDate,
                    reservationTime
            );

            if(!reserved){
                return table;
            }
        }

        throw new TableNotAvailableException("No table is available for your time slot");
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
