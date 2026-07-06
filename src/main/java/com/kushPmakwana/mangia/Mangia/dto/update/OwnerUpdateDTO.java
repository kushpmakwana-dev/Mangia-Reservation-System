package com.kushPmakwana.mangia.Mangia.dto.update;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class OwnerUpdateDTO {
    private String ownerName;
    private String ownerPhone;
    private String ownerEmail;
}
