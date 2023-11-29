package com.civiliansconnection.capp.service;

import com.civiliansconnection.capp.domain.User;
import com.civiliansconnection.capp.dto.UserDto;
import com.civiliansconnection.capp.repository.UserRepository;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ConversionService conversionService;

    public UserService(UserRepository userRepository, ConversionService conversionService) {
        this.userRepository = userRepository;
        this.conversionService = conversionService;
    }

    public UserDto findByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        UserDto userDto = user.map(user1 -> conversionService.convert(user1, UserDto.class))
                .orElse(null);
        return userDto;
    }
}
