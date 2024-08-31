package com.api.password_recovery.controller;

import com.api.password_recovery.domain.Usuario;
import com.api.password_recovery.dtos.requests.LoginRequestDto;
import com.api.password_recovery.dtos.requests.RegisterRequestDto;
import com.api.password_recovery.dtos.requests.ResetRequestDto;
import com.api.password_recovery.dtos.responses.RegisterResponseDto;
import com.api.password_recovery.dtos.responses.TokenResponseDto;
import com.api.password_recovery.infra.security.SecurityConfigurations;
import com.api.password_recovery.infra.security.TokenService;
import com.api.password_recovery.service.EmailService;
import com.api.password_recovery.service.PasswordRecoveryService;
import com.api.password_recovery.service.UserService;
import com.api.password_recovery.util.BodyEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityConfigurations securityConfigurations;

    @Autowired
    private PasswordRecoveryService passwordRecoveryService;


    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody LoginRequestDto body){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(body.email(), body.password());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        String tokenJWT = this.tokenService.gerarToken((Usuario) authentication.getPrincipal());
        return ResponseEntity.ok(new TokenResponseDto(tokenJWT));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDto body){
        Usuario user = this.userService.register(body);
        String token = tokenService.gerarToken(user);
        return ResponseEntity.ok(new RegisterResponseDto(body.name(), token));
    }

    @PostMapping("/reset-password-request")
    public ResponseEntity<String> resetPasswordRequest(@RequestParam String email){
        this.passwordRecoveryService.requestPasswordReset(email);
        return ResponseEntity.ok("Código de redefinição de senha enviado.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetRequestDto resetRequestDto){
        Usuario usuario = this.userService.getUserByEmail(resetRequestDto.email());
        if(usuario.getUserPasswordReset().getPasswordCode().equals(resetRequestDto.resetCode()) &&
           usuario.getUserPasswordReset().getPasswordExpiry().isAfter(LocalDateTime.now())) {

            usuario.setPassword(securityConfigurations.passwordEncoder().encode(resetRequestDto.newPassword()));
            usuario.getUserPasswordReset().setPasswordCode(null);
            usuario.getUserPasswordReset().setPasswordExpiry(null);
            this.userService.saveUser(usuario);
            return ResponseEntity.ok("Senha redefinida com sucesso.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Código de redefinição inválido ou expirado.");
    };


}
