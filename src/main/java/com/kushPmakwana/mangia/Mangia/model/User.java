package com.kushPmakwana.mangia.Mangia.model;

import com.kushPmakwana.mangia.Mangia.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.type.TrueFalseConverter;

@Entity
@Table(
        name = "user_table",
        indexes = {
                @Index(name = "idx_role", columnList = "role")
        }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Convert(converter = TrueFalseConverter.class)
    private boolean isActive = true;
}
