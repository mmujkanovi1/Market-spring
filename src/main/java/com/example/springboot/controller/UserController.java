package com.example.springboot.controller;

import com.example.springboot.mappers.MapStructMapper;
import com.example.springboot.model.User;
import com.example.springboot.repository.UserRepository;
import com.example.springboot.request.CreateUserRequest;
import com.example.springboot.request.UpdateUserRequest;
import com.example.springboot.response.CreateUserResponse;
import com.example.springboot.response.DeleteUserResponse;
import com.example.springboot.response.GetUserResponse;
import com.example.springboot.response.UpdateUserResponse;
import com.example.springboot.service.AppConfigService;
import com.example.springboot.service.RoleService;
import com.example.springboot.service.SecurityService;
import com.example.springboot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {


  private static final int PARAMETER = 1;
  private MapStructMapper mapStructMapper;

  private UserRepository userRepository;

  private Logger logger = LoggerFactory.getLogger(UserController.class);


  @Autowired
  private UserService userService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private RoleService roleService;

  @Autowired
  private SecurityService securityService;

  @Autowired
  private AppConfigService appConfigService;

  @Autowired
  private PagedResourcesAssembler pagedResourcesAssembler;


  @Autowired
  public UserController(final MapStructMapper mapStructMapper, final UserRepository userRepository) {
    this.mapStructMapper = mapStructMapper;
    this.userRepository = userRepository;
  }


  @GetMapping("")
  @PreAuthorize("@securityService.hasRole(#request,'ADMIN')")
  public ResponseEntity<PagedModel<EntityModel<GetUserResponse>>> getAllUsers(final HttpServletRequest request,
                                                                              final Pageable pageable) {
    Page<User> getAllUsers = userService.getAllUsers(pageable);
    Page<GetUserResponse> userResponsePage = getAllUsers.map(user -> {
      return mapStructMapper.getUserResponse(user);
    });
    PagedModel<EntityModel<GetUserResponse>> pagedModel = pagedResourcesAssembler.toModel(userResponsePage);


    logger.info("Get all users:{}", getAllUsers);
    return new ResponseEntity<>(
            pagedModel,
            HttpStatus.OK
    );
  }

  @GetMapping("/{id}")
  @PreAuthorize("@securityService.hasId(#request,#id)")
  public ResponseEntity<GetUserResponse> getUserById(final HttpServletRequest request, @PathVariable final Long id) {
    User findUser = userRepository.findById(id).orElseThrow(() ->
            new NoSuchElementException("No such user with an id " + id));
    logger.info("Get user with id:{}", findUser);
    return new ResponseEntity<>(
            mapStructMapper.getUserResponse(
                    findUser
            ),
            HttpStatus.OK
    );
  }

  @CrossOrigin(origins = "http://localhost:4200")
  @PostMapping("")
  public ResponseEntity<CreateUserResponse> postUser(@Valid @RequestBody final CreateUserRequest user) {
    User newUser = new User();
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    newUser = mapStructMapper.createUserRequest(user, roleService.findRoleById(user.getRoleId()));
    userRepository.save(newUser);
    logger.info("Created new user:{}", newUser);
    return new ResponseEntity<>(
            mapStructMapper.createUserResponse(newUser),
            HttpStatus.OK
    );
  }


  @PutMapping("/{id}")
  @PreAuthorize("@securityService.hasId(#request,#id)")
  public ResponseEntity<UpdateUserResponse> updateUser(final HttpServletRequest request, @PathVariable final Long id, @Valid @RequestBody final UpdateUserRequest oldUser) {
    oldUser.setPassword(passwordEncoder.encode(oldUser.getPassword()));
    oldUser.setId(id);
    User updateUser = mapStructMapper.updateUserRequest(oldUser, roleService.findRoleById(oldUser.getRoleId()));
    userRepository.save(updateUser);
    logger.info("Updated user:{}", updateUser);
    return new ResponseEntity<>(
            mapStructMapper.updateUserResponse(updateUser),
            HttpStatus.OK
    );

  }

  @DeleteMapping("/{id}")
  @PreAuthorize("@securityService.hasIdOrRole(#request,'ADMIN',#id)")
  public ResponseEntity<DeleteUserResponse> deleteUser(final HttpServletRequest request, @PathVariable final Long id) {
    User newUser = userRepository.findById(id).orElseThrow(() ->
            new NoSuchElementException("You cannot delete a user because a user with an id " + id + " does not exist "));
    userRepository.delete(newUser);
    logger.info("Deleted user:{}", newUser);
    return new ResponseEntity<>(
            mapStructMapper.deleteUserResponse(newUser),
            HttpStatus.OK
    );
  }

}
