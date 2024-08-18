package com.api.password_recovery.controller;

import com.api.password_recovery.domain.Usuario;
import com.api.password_recovery.dtos.requests.RegisterRequestDto;
import com.api.password_recovery.dtos.responses.RegisterResponseDto;
import com.api.password_recovery.infra.security.TokenService;
import com.api.password_recovery.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;
    @GetMapping("/confirm")
    public ResponseEntity<String> corfirmUser (@RequestParam("token") String token){
       String email = this.tokenService.getSubject(token);
       Usuario user = this.userService.findUserByEmail(email);
       user.setConfirmed(true);
       this.userService.saveUserConfirmed(user);

       return ResponseEntity.ok("Email confirmado com sucesso");
    }
}
