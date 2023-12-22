package com.civiliansconnection.capp.service;

import com.civiliansconnection.capp.dto.AuthenticationRequestDto;
import com.civiliansconnection.capp.dto.UserDto;
import com.civiliansconnection.capp.security.CivUserDetails;
import com.civiliansconnection.capp.security.JwtProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final CivUserDetailsService userDetailsService;
    private final JwtProvider jwtProvider;
    private final UserService userService;

    public AuthenticationService(AuthenticationManager authenticationManager, CivUserDetailsService userDetailsService,
                                 JwtProvider jwtProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtProvider = jwtProvider;
        this.userService = userService;
    }

    public String authenticate(AuthenticationRequestDto requestDto) throws UsernameNotFoundException, JsonProcessingException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestDto.getUsername(),
                requestDto.getPassword()));
        CivUserDetails userDetails = userDetailsService.loadUserByUsername(requestDto.getUsername());
        String jwt = jwtProvider.generateToken(userDetails);
        UserDto userDto = userService.findByUsername(requestDto.getUsername());
        Instant now = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant();
        return jwt;
    }
}
