package com.civiliansconnection.capp.controller;

import com.civiliansconnection.capp.dto.AuthenticationRequestDto;
import com.civiliansconnection.capp.service.AuthenticationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(value = "/authenticate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String authenticate(@RequestBody AuthenticationRequestDto authenticationRequestDto) throws JsonProcessingException {
        return authenticationService.authenticate(authenticationRequestDto);
    }
}
