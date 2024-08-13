package com.api.password_recovery.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class teste {
    @GetMapping
    public ResponseEntity<String> teste (){
        return ResponseEntity.ok("authenticado");
    }
}
