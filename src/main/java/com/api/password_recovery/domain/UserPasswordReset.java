package com.api.password_recovery.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "user_password_reset")
@Table(name = "user_password_reset")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPasswordReset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private Usuario usuario;

    @Column(name = "password_code")
    private String passwordCode;

    @Column(name = "password_expiry")
    private LocalDateTime passwordExpiry;

}
