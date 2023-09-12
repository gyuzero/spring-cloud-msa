package com.gyuzero.userservice.service;

import com.gyuzero.userservice.dto.UserDto;
import com.gyuzero.userservice.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<UserEntity> findAll();

    UserDto createUser(UserDto userDto);

    UserDto findByUserId(String userId);
}
