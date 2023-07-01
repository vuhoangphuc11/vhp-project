package com.vhp.code.service;

import com.vhp.code.entity.User;
import com.vhp.code.model.UserModel;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public List<UserModel> getAll();
    public Optional<User> findByUsername(String param);
}
