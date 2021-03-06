package com.vhp.code.controller;

import com.vhp.code.common.ERole;
import com.vhp.code.entity.Role;
import com.vhp.code.entity.Token;
import com.vhp.code.entity.User;
import com.vhp.code.payload.request.LoginRequest;
import com.vhp.code.payload.request.MessageResponse;
import com.vhp.code.payload.request.SignupRequest;
import com.vhp.code.repository.RoleRepository;
import com.vhp.code.repository.TokenRepository;
import com.vhp.code.repository.UserRepository;
import com.vhp.code.security.JwtTokenUtil;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {

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
  @PostMapping("/login")
  public ResponseEntity<?> authenticateUser(@Validated @RequestBody LoginRequest loginRequest)
      throws Exception {

    Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    authenticate(loginRequest.getUsername(), loginRequest.getPassword());

    final UserDetails userDetails = userDetailsService.loadUserByUsername(
        loginRequest.getUsername());

    final String token = jwtTokenUtil.generateJwtToken(userDetails);

    Token tokenObj = new Token(userDetails.getUsername(), token, timestamp);
    tokenRepository.save(tokenObj);

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
