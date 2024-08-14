package com.api.password_recovery.controller;

import com.api.password_recovery.domain.Usuario;
import com.api.password_recovery.dtos.requests.LoginRequestDto;
import com.api.password_recovery.dtos.requests.RegisterRequestDto;
import com.api.password_recovery.dtos.responses.RegisterResponseDto;
import com.api.password_recovery.dtos.responses.TokenResponseDto;
import com.api.password_recovery.infra.security.TokenService;
import com.api.password_recovery.service.EmailService;
import com.api.password_recovery.service.UserService;
import com.api.password_recovery.util.BodyEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private EmailService emailService;

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
        String assunto = "Bem-vindo(a) " + user.getUsername() + "!";
        emailService.sendEmail(user.getEmail(), assunto, BodyEmail.NEW_USER.getContent());
        return ResponseEntity.ok(new RegisterResponseDto(body.name(), token));
    }


}
