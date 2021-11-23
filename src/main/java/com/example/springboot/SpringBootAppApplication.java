package com.example.springboot;

import com.example.springboot.model.AppConfig;
import com.example.springboot.model.Role;
import com.example.springboot.model.RoleType;
import com.example.springboot.repository.AppConfigRepository;
import com.example.springboot.repository.RoleRepository;
import com.example.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableSwagger2
@EnableFeignClients
@EnableScheduling
public class SpringBootAppApplication implements CommandLineRunner {

  @Autowired
  private AppConfigRepository appConfigRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  private static final int NUMBER = 94;

  public static void main(final String[] args) {
    SpringApplication.run(SpringBootAppApplication.class, args);
  }

  @Override
  public void run(final String... args) throws Exception {

    AppConfig appConfig = new AppConfig();
    appConfigRepository.save(appConfig);




    Role role = new Role();
    role.setId((long) 2);
    role.setName(RoleType.SELLER);
    Role roleAdmin = new Role();
    roleAdmin.setId((long) 1);
    roleAdmin.setName(RoleType.ADMIN);
    roleRepository.save(roleAdmin);
    roleRepository.save(role);


  }
}
