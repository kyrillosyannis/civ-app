package com.civiliansconnection.civ.controller;

import com.civiliansconnection.civ.dto.AuthenticationRequestDto;
import com.civiliansconnection.civ.dto.AuthenticationResponseDto;
import com.civiliansconnection.civ.service.AuthenticationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin("http://localhost:3000")
@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(value = "/api/authenticate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public AuthenticationResponseDto authenticate(@RequestBody AuthenticationRequestDto authenticationRequestDto,
                                                  HttpServletResponse response) throws JsonProcessingException {
        AuthenticationResponseDto authenticationResponseDto = authenticationService.authenticate(authenticationRequestDto);
        Cookie cookie = new Cookie("username", authenticationResponseDto.getJwt());
        cookie.setMaxAge(7 * 24 * 60 * 60); // expires in 7 days
        cookie.setSecure(true);
        response.addCookie(cookie);

        return authenticationResponseDto;
    }
}
