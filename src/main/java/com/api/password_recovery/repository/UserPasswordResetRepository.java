package com.api.password_recovery.repository;

import com.api.password_recovery.domain.UserPasswordReset;
import com.api.password_recovery.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPasswordResetRepository extends JpaRepository<UserPasswordReset, Long> {
    Optional<UserPasswordReset> findByUsuario(Usuario usuario);
}
