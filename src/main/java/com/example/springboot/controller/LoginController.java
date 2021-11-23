package com.example.springboot.controller;

import com.example.springboot.config.JwtTokenUtil;
import com.example.springboot.request.LoginRequest;
import com.example.springboot.response.JwtResponse;
import com.example.springboot.service.JwtUserDetailService;
import com.example.springboot.service.RoleService;
import com.example.springboot.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@CrossOrigin
@RestController
@Validated
@RequestMapping("/login")
public class LoginController {

  private Logger logger = LoggerFactory.getLogger(UserController.class);

  private static final int TOKEN_SUBSTRING = 7;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  private JwtUserDetailService userDetailsService;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private RoleService roleService;

  @Autowired
  private SecurityService securityService;

  @PostMapping("")
  public ResponseEntity<JwtResponse> loginUser(@Valid @RequestBody final LoginRequest user) throws Exception {
    authenticate(user.getUsername(), user.getPassword());

    final UserDetails userDetails = userDetailsService
            .loadUserByUsername(user.getUsername());

    final String token = jwtTokenUtil.generateToken(userDetails);
    JwtResponse jwtResponse = new JwtResponse(token, securityService.getIdFromToken(token));
    logger.info("Login user with token:{}", jwtResponse.getToken());
    logger.info("Login user with id:{}", securityService.getIdFromToken(token));
    return new ResponseEntity<>(
            jwtResponse,
            HttpStatus.OK
    );
  }

  private void authenticate(final String username, final String password) throws Exception {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    } catch (DisabledException e) {
      throw new Exception("USER_DISABLED", e);
    } catch (BadCredentialsException e) {
      throw new Exception("INVALID_CREDENTIALS", e);
    }
  }

}
