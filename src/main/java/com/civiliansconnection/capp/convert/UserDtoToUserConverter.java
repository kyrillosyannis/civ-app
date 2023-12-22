package com.civiliansconnection.capp.convert;

import com.civiliansconnection.capp.domain.User;
import com.civiliansconnection.capp.dto.UserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserDtoToUserConverter implements Converter<UserDto, User> {

    @Override
    public User convert(UserDto source) {
        return User.builder()
                .id(source.getId())
                .username(source.getUsername())
                .password(source.getPassword())
                .build();
    }
}
