package com.vhp.code.service.user;

import com.vhp.code.entity.User;
import com.vhp.code.model.UserModel;
import com.vhp.code.repository.UserRepository;
import com.vhp.code.service.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserModel> getAll() {

        List<User> users = userRepository.findAll();
        return users.stream().map(i -> {
            UserModel userModel = new UserModel();
            userModel.setEmail(i.getEmail());
            userModel.setUsername(i.getUsername());
            return userModel;
        }).collect(Collectors.toList());
    }

    @Override
    public Optional<User> findByUsername(String param) {
        String username = param == null ? "" : param;
        return userRepository.findByUsername(username);
    }
}
