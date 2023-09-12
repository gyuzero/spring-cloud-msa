package com.gyuzero.userservice.controller;

import com.gyuzero.userservice.dto.CreateUser;
import com.gyuzero.userservice.dto.ResponseUser;
import com.gyuzero.userservice.dto.User;
import com.gyuzero.userservice.entity.UserEntity;
import com.gyuzero.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;

    private final ModelMapper modelMapper;

    @PostMapping("/users")
    public ResponseEntity<ResponseUser> createUser(@RequestBody CreateUser createUser) {

        User user = modelMapper.map(createUser, User.class);

        User result = userService.createUser(user);

        ResponseUser responseUser = modelMapper.map(result, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }

    @GetMapping("/users")
    public ResponseEntity<List<ResponseUser>> getUsers() {

        List<UserEntity> users = userService.findAll();

        List<ResponseUser> result = new ArrayList<>();

        users.forEach(user -> {
            result.add(modelMapper.map(user, ResponseUser.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);

    }
}
