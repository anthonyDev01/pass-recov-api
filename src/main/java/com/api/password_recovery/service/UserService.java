package com.api.password_recovery.service;

import com.api.password_recovery.domain.Usuario;
import com.api.password_recovery.dtos.requests.RegisterRequestDto;
import com.api.password_recovery.infra.exception.UserAlreadyExistsException;
import com.api.password_recovery.infra.exception.UserNotConfirmedException;
import com.api.password_recovery.infra.exception.UserNotFoundException;
import com.api.password_recovery.infra.security.SecurityConfigurations;
import com.api.password_recovery.infra.security.TokenService;
import com.api.password_recovery.repository.UsuarioRepository;
import com.api.password_recovery.util.BodyEmail;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SecurityConfigurations securityConfigurations;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TokenService tokenService;

    public Usuario register(RegisterRequestDto body){
        Optional<Usuario> user = this.usuarioRepository.findByEmail(body.email());

        if (user.isPresent()){
            throw new UserAlreadyExistsException("email já cadastrado");
        }

        Usuario newUser = new Usuario();
        BeanUtils.copyProperties(body, newUser);
        newUser.setPassword(this.securityConfigurations.passwordEncoder().encode(body.password()));
        newUser.setConfirmed(false);

        String token = tokenService.gerarToken(newUser);
        String assunto = "Bem-vindo(a) " + newUser.getName() + "!";
        String confirmationURL = "http://localhost:8080/users/confirm?token=" + token;

        emailService.sendEmail(newUser.getEmail(), assunto, BodyEmail.NEW_USER.getContent("pass_recov", newUser.getName(), "pass_recov", confirmationURL));
        return this.usuarioRepository.save(newUser);
    }

    public Usuario saveUserConfirmed(Usuario user){
        if (!user.getConfirmed()){
            throw new UserNotConfirmedException("O email do usuario ainda não foi confirmado");
        }
        return this.usuarioRepository.save(user);
    }


    public Usuario findUserByEmail (String email){
        return this.usuarioRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("Usuario não encontrado"));
    }
}
