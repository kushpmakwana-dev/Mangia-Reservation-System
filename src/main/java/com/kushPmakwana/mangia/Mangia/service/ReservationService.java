package com.kushPmakwana.mangia.Mangia.service;

import com.kushPmakwana.mangia.Mangia.dto.request.ReservationRequestDTO;
import com.kushPmakwana.mangia.Mangia.dto.response.ListResponse;
import com.kushPmakwana.mangia.Mangia.dto.response.ReservationResponseDTO;
import com.kushPmakwana.mangia.Mangia.enums.ReservationStatus;
import com.kushPmakwana.mangia.Mangia.enums.ReservationType;
import com.kushPmakwana.mangia.Mangia.enums.TableStatus;
import com.kushPmakwana.mangia.Mangia.exceptions.*;
import com.kushPmakwana.mangia.Mangia.model.Customer;
import com.kushPmakwana.mangia.Mangia.model.RestaurantTable;
import com.kushPmakwana.mangia.Mangia.utility.Utils;
import com.kushPmakwana.mangia.Mangia.model.Reservation;
import com.kushPmakwana.mangia.Mangia.repository.ReservationRepository;
import jakarta.transaction.Transactional;
//import org.hibernate.query.Page;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
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

    @Transactional
    public void cancel(Long id){
        var user  = Utils.getAuthenticatedCustomer().orElseThrow(()-> new UnAuthorizedException("ONLY CUSTOMERS ARE ALLOWED TO PERFORM CANCELLATIONS"));

        Reservation reservation = repository.findById(id).orElseThrow(() -> new ResourcesNotFoundException("Resource Not found", getEntityName()));

        if(!reservation.getBookedBy().getId().equals(user.getUser().getId())){
            throw new UnAuthorizedException("YOU ARE NOT ALLOWED TO PERFORM CANCELLATION ON SOMEONE ELSE'S RESERVATIONS");
        }

        if(reservation.getStatus() == ReservationStatus.ARRIVED || reservation.getStatus() == ReservationStatus.EXPIRED || reservation.getStatus() == ReservationStatus.COMPLETED){
            throw new UnmodifiableException("YOU CANNOT CANCEL THE RESERVATIONS AT THIS STAGE", getEntityName());
        }

        if(reservation.getStatus() == ReservationStatus.CANCELLED){
            throw new UnmodifiableException("RESERVATION HAS ALREADY BEEN CANCELLED", getEntityName());
        }

        LocalDateTime currentTime = LocalDateTime.now(),
        reservationTimeAndDate = reservation.getReservationTime().atDate(reservation.getReservationDate());

        if(!currentTime.isBefore(reservationTimeAndDate.minusMinutes(30))){
            throw new UnmodifiableException("YOU CANNOT CANCEL THE RESERVATION 30 MINS BEFORE THE RESERVATION TIME", getEntityName());
        }

        reservation.setStatus(ReservationStatus.CANCELLED);
        repository.save(reservation);
    }

    @Transactional
    public void confirm(Long id){
        var user = Utils.getOwnerOrEmployee().orElseThrow(() -> new UnAuthorizedException("ONLY OWNER OR EMPLOYEE ARE ALLOWED TO CONFIRM"));

        Reservation reservation = findEntityById(id);

        LocalDateTime reservationTime = reservation.getReservationTime().atDate(reservation.getReservationDate());
        LocalDateTime currentTime = LocalDateTime.now();

        if(currentTime.isAfter(reservationTime)) {
            throw new UnmodifiableException("RESERVATION TIME HAS ALREADY PASSED", getEntityName());
        }

        if(reservation.getStatus()!=ReservationStatus.PENDING){
            throw new UnmodifiableException("You cannot confirm the booking at this stage", getEntityName());
        }

        reservation.setStatus(ReservationStatus.CONFIRMED);
        repository.save(reservation);
    }

    @Transactional
    public void markedArrived(Long id){
        var user = Utils.getOwnerOrEmployee().orElseThrow(() -> new UnAuthorizedException("ONLY OWNER OR EMPLOYEE ARE ALLOWED TO CONFIRM"));

        Reservation reservation = findEntityById(id);

        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime reservationValidity = reservation.getReservationValidTill();
        LocalDateTime reservationTime = reservation.getReservationTime().atDate(reservation.getReservationDate());

        if(currentTime.isAfter(reservationValidity) || currentTime.isBefore(reservationTime)){
            throw new UnmodifiableException("Reservation Validity has already been expired", getEntityName());
        }

        if(reservation.getStatus()!=ReservationStatus.CONFIRMED){
            throw new UnmodifiableException("YOU CANNOT MARK A UN-CONFIRMED TABLE AS ARRIVED", getEntityName());
        }

        reservation.setStatus(ReservationStatus.ARRIVED);
        repository.save(reservation);

    }

    @Transactional
    public void markedAsCompleted(Long id){
        var user = Utils.getOwnerOrEmployee().orElseThrow(() -> new UnAuthorizedException("ONLY OWNER OR EMPLOYEE ARE ALLOWED TO CONFIRM"));

        Reservation reservation = findEntityById(id);

        if(reservation.getStatus() != ReservationStatus.ARRIVED){
            throw new UnmodifiableException("YOU CANNOT MARK THE RESERVATION AS COMPLETE AT THIS STAGE", getEntityName());
        }

        reservation.setStatus(ReservationStatus.COMPLETED);
        repository.save(reservation);
    }

    public ListResponse<ReservationResponseDTO> search(
            String search,
            ReservationStatus reservationStatus,
            LocalDate date,
            int totalNumberOfPeople,
            Pageable pageable
    ){
        var user = Utils.getOwnerOrEmployee().orElseThrow(() -> new UnAuthorizedException("ONLY OWNER AND EMPLOYEE ARE ALLOWED TO PERFORM THIS ACTION"));

        Page<Reservation> page = repository.search(
                search,
                reservationStatus,
                date,
                totalNumberOfPeople,
                pageable
        );

        List<ReservationResponseDTO> data =
                page.getContent().stream()
                        .map(this::toResponse)
                        .toList();

        return new ListResponse<ReservationResponseDTO>(page, data);

    }

    /*
    * Helper Methods
    */

    private RestaurantTable fetchValidTable(
            List<RestaurantTable> tables,
            LocalDate reservationDate,
            LocalTime reservationTime
    ){
        if(tables.isEmpty()) throw new TableNotAvailableException("NO TABLE WITH REQUIRED CAPACITY IS AVAILABLE");

        for(RestaurantTable table : tables){
            boolean reserved = repository.existsByTableAndReservationDateAndReservationTimeAndStatusIn(
                    table,
                    reservationDate,
                    reservationTime,
                    List.of(
                            ReservationStatus.PENDING,
                            ReservationStatus.CONFIRMED,
                            ReservationStatus.ARRIVED
                    )
            );

            if(!reserved){
                return table;
            }
        }

        throw new TableNotAvailableException("No table is available for your time slot");
    }

    private Page<Reservation> searchAll(
            String search,
            ReservationStatus status,
            LocalDate date,
            int totalNumberOfPeople,
            Pageable pageable
    ){
        return repository.search(
                search,
                status,
                date,
                totalNumberOfPeople,
                pageable
        );
    }

    @Override
    protected ReservationResponseDTO toResponse(Reservation entity) {
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
    protected Reservation toEntity(ReservationRequestDTO request) {
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
