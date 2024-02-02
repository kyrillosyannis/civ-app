package com.civiliansconnection.civ.service;

import com.civiliansconnection.civ.domain.User;
import com.civiliansconnection.civ.dto.UserDto;
import com.civiliansconnection.civ.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final ConversionService conversionService;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, ConversionService conversionService,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.conversionService = conversionService;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto findByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        UserDto userDto = user.map(user1 -> conversionService.convert(user1, UserDto.class))
                .orElse(null);
        return userDto;
    }

    public UserDto create(UserDto userDto) {
        log.info("Creating user: {}", userDto.getUsername());
        String hashedPass = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(hashedPass);
        User user = conversionService.convert(userDto, User.class);
        userRepository.save(user);
        userDto = conversionService.convert(user, UserDto.class);
        return userDto;
    }
}
