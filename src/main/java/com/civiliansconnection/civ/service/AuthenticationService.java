package com.civiliansconnection.civ.service;

import com.civiliansconnection.civ.dto.AuthenticationRequestDto;
import com.civiliansconnection.civ.dto.AuthenticationResponseDto;
import com.civiliansconnection.civ.dto.UserDto;
import com.civiliansconnection.civ.security.CivUserDetails;
import com.civiliansconnection.civ.security.JwtProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

    public AuthenticationResponseDto authenticate(AuthenticationRequestDto requestDto) throws UsernameNotFoundException, JsonProcessingException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestDto.getUsername(),
                requestDto.getPassword()));
        CivUserDetails userDetails = userDetailsService.loadUserByUsername(requestDto.getUsername());
        String jwt = jwtProvider.generateToken(userDetails);
//        UserDto userDto = userService.findByUsername(requestDto.getUsername());
//        Instant now = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant();
        AuthenticationResponseDto authenticationResponseDto = new AuthenticationResponseDto(jwt, userDetails.getUsername());
        return authenticationResponseDto;
    }
}
