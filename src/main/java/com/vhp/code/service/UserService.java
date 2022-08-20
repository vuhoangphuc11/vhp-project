package com.vhp.code.service;

import com.vhp.code.entity.User;
import com.vhp.code.model.UserModel;
import com.vhp.code.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<UserModel> getAll() {

        List<User> users = userRepository.findAll();
        return users.stream().map(i -> {
            UserModel userModel = new UserModel();
            userModel.setEmail(i.getEmail());
            userModel.setUsername(i.getUsername());
            return userModel;
        }).collect(Collectors.toList());
    }


    public Optional<User> findByUsername(String param) {
        String username = param == null ? "" : param;
        return userRepository.findByUsername(username);
    }
}
