package com.kushPmakwana.mangia.Mangia.dto.response;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OwnerResponseDTO {
    private Long id;
    private String ownerName;
//    private String ownerPhone;
    private String ownerEmail;
}
