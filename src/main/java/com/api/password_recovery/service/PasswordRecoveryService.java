package com.api.password_recovery.service;

import com.api.password_recovery.domain.UserPasswordReset;
import com.api.password_recovery.domain.Usuario;
import com.api.password_recovery.infra.exception.UserNotFoundException;
import com.api.password_recovery.infra.exception.UserRecoveryCodeNotValid;
import com.api.password_recovery.repository.UserPasswordResetRepository;
import com.api.password_recovery.repository.UsuarioRepository;
import com.api.password_recovery.util.BodyEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class PasswordRecoveryService {
    @Autowired
    private UserService userService;

    @Autowired
    private UserPasswordResetRepository userPasswordResetRepository;

    @Autowired
    private EmailService emailService;

    private static final int CODE_LENGTH = 6;

    public Usuario requestPasswordReset(String email){

         Usuario usuario = this.userService.getUserByEmail(email);
         UserPasswordReset userPasswordReset = this.userPasswordResetRepository.findByUsuario(usuario).orElse(new UserPasswordReset());
         String resetCode = generateResetCode();

         userPasswordReset.setPasswordCode(resetCode);
         userPasswordReset.setPasswordExpiry(LocalDateTime.now().plusMinutes(15));
         userPasswordReset.setUsuario(usuario);

         this.userPasswordResetRepository.save(userPasswordReset);

         emailService.sendEmail(usuario.getEmail(), "Recuperção de senha", BodyEmail.PASSWORD_RECOV.getContent(resetCode, "pass_recov"));
         return this.userService.saveUser(usuario);
    }

    public void verifyPasswordRecoveIsNull(Usuario usuario){
        if (usuario.getUserPasswordReset().getPasswordCode() == null && usuario.getUserPasswordReset().getPasswordExpiry() == null){
            throw new UserRecoveryCodeNotValid("Código de redefinição inválido ou expirado.");
        }
    }

    private String generateResetCode() {
        return String.valueOf(new Random().nextInt(999999)).substring(0, 6);
    }
}
