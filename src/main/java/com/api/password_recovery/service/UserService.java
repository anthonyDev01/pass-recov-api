package com.api.password_recovery.service;

import com.api.password_recovery.domain.Usuario;
import com.api.password_recovery.dtos.requests.RegisterRequestDto;
import com.api.password_recovery.infra.exception.UserAlreadyExistsException;
import com.api.password_recovery.infra.security.SecurityConfigurations;
import com.api.password_recovery.repository.UsuarioRepository;
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

    public Usuario register(RegisterRequestDto body){
        Optional<UserDetails> user = this.usuarioRepository.findByEmail(body.email());

        if (user.isPresent()){
            throw new UserAlreadyExistsException("email já cadastrado");
        }

        Usuario newUser = new Usuario();
        BeanUtils.copyProperties(body, newUser);
        newUser.setPassword(this.securityConfigurations.passwordEncoder().encode(body.password()));
        return this.usuarioRepository.save(newUser);

    }
}
