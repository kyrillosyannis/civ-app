package com.civiliansconnection.civ.convert;

import com.civiliansconnection.civ.domain.User;
import com.civiliansconnection.civ.dto.UserDto;
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
