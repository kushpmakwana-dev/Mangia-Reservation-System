package com.kushPmakwana.mangia.Mangia.service;

import com.kushPmakwana.mangia.Mangia.dto.request.RestaurantTableRequestDTO;
import com.kushPmakwana.mangia.Mangia.dto.response.ListResponse;
import com.kushPmakwana.mangia.Mangia.dto.response.RestaurantTableResponseDTO;
import com.kushPmakwana.mangia.Mangia.dto.update.RestaurantTableUpdateDTO;
import com.kushPmakwana.mangia.Mangia.enums.TableStatus;
import com.kushPmakwana.mangia.Mangia.exceptions.AlreadyExistsException;
import com.kushPmakwana.mangia.Mangia.exceptions.CustomException;
import com.kushPmakwana.mangia.Mangia.model.RestaurantTable;
import com.kushPmakwana.mangia.Mangia.repository.RestaurantTableRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantTableService extends BaseService<RestaurantTable, RestaurantTableRepository, RestaurantTableRequestDTO, RestaurantTableResponseDTO>{

    public RestaurantTableService(RestaurantTableRepository repository){
        super(repository);
    }

    public void add(RestaurantTableRequestDTO request){
        if(repository.existsByTableNumber(request.getTableNumber())){
            throw new AlreadyExistsException(getEntityName(), "Table Number Already Exists");
        }
        RestaurantTable newTable = toEntity(request);
        repository.save(newTable);
    }

    public void update(Long id, RestaurantTableUpdateDTO updateDTO){

        RestaurantTable existingTable = findEntityById(id);

        if(updateDTO.getTableNumber() != null
                && !existingTable.getTableNumber().equals(updateDTO.getTableNumber())
                && repository.existsByTableNumber(updateDTO.getTableNumber())){
            throw new AlreadyExistsException(getEntityName(), "TABLE NUMBER ALREADY EXISTS");
        }

        if(updateDTO.getTableNumber() != null) existingTable.setTableNumber(updateDTO.getTableNumber());
        if(updateDTO.getCapacity() != null) existingTable.setCapacity(updateDTO.getCapacity());
        if(updateDTO.getStatus() != null) existingTable.setStatus(updateDTO.getStatus());

        repository.save(existingTable);

    }

    public void setBooked(Long id){
        updateStatus(id, TableStatus.BOOKED);
    }

    public void setAvailable(Long id){
        updateStatus(id, TableStatus.AVAILABLE);
    }

    public void setUnderMaintenance(Long id){
        updateStatus(id, TableStatus.UNDER_MAINTENANCE);
    }

    public RestaurantTable fetchAvailableTable(int noOfGuests){
        RestaurantTable table = repository.findFirstByCapacityGreaterThanEqualAndStatusOrderByCapacityAsc(noOfGuests, TableStatus.AVAILABLE)
                                    .orElseThrow(() -> new RuntimeException("No table are currently available"));
        setBooked(table.getId());
        return table;
    }

    public ListResponse<RestaurantTableResponseDTO> search(
            String search,
            TableStatus status,
            Integer capacity,
            Pageable pageable
    ){
        Page<RestaurantTable> page = repository.search(search, status, capacity, pageable);

        List<RestaurantTableResponseDTO> data = page.stream().map(this::toResponse).toList();

        return new ListResponse<RestaurantTableResponseDTO>(page, data);
    }

    /*
    * Helper Methods
    */

    private void updateStatus(Long id, TableStatus status){
        RestaurantTable existingTable = findEntityById(id);

        if(existingTable.getStatus() == status){
            throw new CustomException(getEntityName(), "TABLE IS ALREADY " + status);
        }

        existingTable.setStatus(status);
        repository.save(existingTable);
    }

    @Override
    public RestaurantTableResponseDTO toResponse(RestaurantTable entity) {
        return RestaurantTableResponseDTO.builder()
                .id(entity.getId())
                .capacity(entity.getCapacity())
                .tableNumber(entity.getTableNumber())
                .status(entity.getStatus())
                .build();
    }

    @Override
    public RestaurantTable toEntity(RestaurantTableRequestDTO request) {
        RestaurantTable table = new RestaurantTable();
        table.setTableNumber(request.getTableNumber());
        table.setCapacity(request.getCapacity());
        table.setStatus(request.getStatus());
        return table;
    }

    @Override
    protected String getEntityName() {
        return "RESTAURANT TABLE";
    }
}
