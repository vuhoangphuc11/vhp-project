package com.vhp.code.controller;

import com.vhp.code.common.ERole;
import com.vhp.code.entity.Role;
import com.vhp.code.entity.User;
import com.vhp.code.payload.request.MessageResponse;
import com.vhp.code.payload.request.SignupRequest;
import com.vhp.code.payload.response.ResponseHeader;
import com.vhp.code.payload.response.RestResponse;
import com.vhp.code.repository.RoleRepository;
import com.vhp.code.repository.TokenRepository;
import com.vhp.code.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private TokenRepository tokenRepository;

    @GetMapping("/get-all")
    private RestResponse<List<User>> getAllUser() {
        List<User> listUser = userRepository.findAll();
        return new RestResponse<>(new ResponseHeader(HttpStatus.OK.value()), listUser);
    }

    @GetMapping("/find-user")
    private Optional<User> findByUsername(@RequestParam String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user;
    }

    @GetMapping("/delete-user")
    private String deleteUsername(@RequestParam String username) {
        Integer flag = userRepository.deleteByUsername(username);
        if (flag == 1) {
            return "Successfully";
        } else {
            return "failed";
        }
    }


//    @PutMapping("/update-user")
//    private ResponseEntity<?> updateUser(@Validated @RequestBody UserUpdateRequest request) {
//        if (userRepository.existsByUsername(request.getUsername())) {
//            User user = new User(request.getUsername(), request.getEmail(), encoder.encode(request.getPassword()));
//            userRepository.save(user);
//            return ResponseEntity.ok(new MessageResponse("Update successfully!"));
//        } else {
//            return (ResponseEntity<?>) ResponseEntity.notFound();
//
//        }
//    }

    @PostMapping("/create-user")
    public ResponseEntity<?> registerUser(@Validated @RequestBody SignupRequest createRequest) {

        if (userRepository.existsByUsername(createRequest.getUsername())) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(createRequest.getEmail())) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: Email is already taken!"));
        }

        User user = new User(createRequest.getUsername(), createRequest.getEmail(),
                encoder.encode(createRequest.getPassword()));

        Set<String> strRoles = createRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "ROLE_ADMIN":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "ROLE_MODERATOR":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);

                        break;
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully"));
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {

        final String requestTokenHeader = request.getHeader("Authorization");

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            tokenRepository.deleteByToken(requestTokenHeader.substring(7));
        }

        return request.getHeader("Authorization");
    }
}
