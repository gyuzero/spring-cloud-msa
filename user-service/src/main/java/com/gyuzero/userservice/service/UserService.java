package com.gyuzero.userservice.service;

import com.gyuzero.userservice.dto.CreateUser;
import com.gyuzero.userservice.dto.User;
import com.gyuzero.userservice.entity.UserEntity;

import java.util.List;

public interface UserService {

    List<UserEntity> findAll();

    User createUser(User createUser);
}
