package com.kushPmakwana.mangia.Mangia.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
