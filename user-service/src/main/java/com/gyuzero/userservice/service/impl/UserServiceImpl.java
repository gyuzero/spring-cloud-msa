package com.gyuzero.userservice.service.impl;

import com.gyuzero.userservice.dto.CreateUser;
import com.gyuzero.userservice.dto.User;
import com.gyuzero.userservice.entity.UserEntity;
import com.gyuzero.userservice.repository.UserRepository;
import com.gyuzero.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        UserEntity userEntity = modelMapper.map(user, UserEntity.class);

        userRepository.save(userEntity);

        User result = modelMapper.map(userEntity, User.class);

        return result;
    }

    @Override
    public User findByUserId(String userId) {

        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null) {
            throw new UsernameNotFoundException("user not found");
        }

        User user = modelMapper.map(userEntity, User.class);

        return user;
    }
}
