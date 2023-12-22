package com.civiliansconnection.capp.convert;

import com.civiliansconnection.capp.domain.User;
import com.civiliansconnection.capp.dto.UserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToUserDtoConverter implements Converter<User, UserDto> {

    @Override
    public UserDto convert(User source) {
        return UserDto.builder()
                .id(source.getId())
                .username(source.getUsername())
                .password(source.getPassword())
                .build();
    }
}
