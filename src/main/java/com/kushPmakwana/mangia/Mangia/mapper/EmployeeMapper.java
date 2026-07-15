package com.kushPmakwana.mangia.Mangia.mapper;

import com.kushPmakwana.mangia.Mangia.dto.request.EmployeeRequestDTO;
import com.kushPmakwana.mangia.Mangia.dto.response.EmployeeResponseDTO;
import com.kushPmakwana.mangia.Mangia.model.Employee;
import com.kushPmakwana.mangia.Mangia.utility.Mappers;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper extends Mappers<Employee, EmployeeRequestDTO, EmployeeResponseDTO> {
}
