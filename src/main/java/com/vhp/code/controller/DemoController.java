package com.vhp.code.controller;

import com.vhp.code.entity.User;
import com.vhp.code.model.UserModel;
import com.vhp.code.payload.response.ResponseHeader;
import com.vhp.code.payload.response.RestResponse;
import com.vhp.code.repository.UserRepository;
import com.vhp.code.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("demo")
public class DemoController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/hi")
    public RestResponse<User> hi(@RequestParam String username) {
        if ("".equals(username)) {
            return new RestResponse<>(new ResponseHeader(HttpStatus.NOT_FOUND.value()), new User());
        }
        return new RestResponse(new ResponseHeader(HttpStatus.OK.value()), userService.findByUsername(username));
    }

    @GetMapping("/greeting")
    public ResponseEntity<String> greeting(@RequestHeader("accept-language") String language) {
        return new ResponseEntity<String>("test", HttpStatus.OK);
    }

    @GetMapping("/double")
    public ResponseEntity<String> doubleNumber(@RequestHeader("my-number") int myNumber) {
        return new ResponseEntity<String>(String.format("%d * 2 = %d",
                myNumber, (myNumber * 2)), HttpStatus.OK);
    }

    @GetMapping("/find-all")
    public RestResponse<List<UserModel>> getAllUser() {
        return new RestResponse<>(new ResponseHeader(HttpStatus.OK.value()), userService.getAll());
    }
}


