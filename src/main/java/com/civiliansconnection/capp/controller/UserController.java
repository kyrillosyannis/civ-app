package com.civiliansconnection.capp.controller;

import com.civiliansconnection.capp.dto.UserDto;
import com.civiliansconnection.capp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody UserDto userDto) {
        userDto = userService.create(userDto);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }
}
