package com.vhp.code.controller;

import com.vhp.code.common.ERole;
import com.vhp.code.entity.Role;
import com.vhp.code.entity.Token;
import com.vhp.code.entity.User;
import com.vhp.code.payload.request.MessageResponse;
import com.vhp.code.payload.request.SigninRequest;
import com.vhp.code.payload.request.SignupRequest;
import com.vhp.code.repository.RoleRepository;
import com.vhp.code.repository.TokenRepository;
import com.vhp.code.repository.UserRepository;
import com.vhp.code.security.JwtTokenUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private static Logger logger = LogManager.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Secured("ALL")
    @PostMapping("/sign-in")
    public ResponseEntity<?> authenticateUser(@Validated @RequestBody SigninRequest signinRequest)
            throws Exception {

        logger.info("-----START SIGN-IN-----");

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        authenticate(signinRequest.getUsername(), signinRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(
                signinRequest.getUsername());

        final String token = jwtTokenUtil.generateJwtToken(userDetails);

        Token tokenObj = new Token(userDetails.getUsername(), token, timestamp);
        tokenRepository.save(tokenObj);

        logger.info("-----END SIGN-IN-----");

        return ResponseEntity.ok(token);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> registerUser(@Validated @RequestBody SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: Email is already taken!"));
        }

        User user = new User(signupRequest.getUsername(), signupRequest.getEmail(),
                encoder.encode(signupRequest.getPassword()));

        Set<Role> roles = new HashSet<>();

        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

        roles.add(userRole);

        user.setRoles(roles);

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully"));
    }

    @PutMapping("/update-user")
    public ResponseEntity<?> updateUser(@Validated @RequestBody SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            User user = new User(signupRequest.getUsername(), signupRequest.getEmail(),
                    encoder.encode(signupRequest.getPassword()));
            userRepository.save(user);
            return ResponseEntity.ok(new MessageResponse("User registered successfully"));
        } else {
            return (ResponseEntity<?>) ResponseEntity.notFound();
        }
    }


    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
