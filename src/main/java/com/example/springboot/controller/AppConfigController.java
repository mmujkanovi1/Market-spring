package com.example.springboot.controller;

import com.example.springboot.mappers.MapStructMapper;
import com.example.springboot.model.AppConfig;
import com.example.springboot.repository.AppConfigRepository;
import com.example.springboot.request.UpdateAppConfigRequest;
import com.example.springboot.response.GetAppConfigResponse;
import com.example.springboot.response.UpdateAppConfigResponse;
import com.example.springboot.service.AppConfigService;
import com.example.springboot.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@Validated
@RequestMapping("/config")
@PreAuthorize("@securityService.hasRole(#request,'ADMIN')")
@CrossOrigin
public class AppConfigController {

  @Autowired
  private SecurityService securityService;

  @Autowired
  private MapStructMapper mapStructMapper;

  @Autowired
  private AppConfigRepository appConfigRepository;

  @Autowired
  private AppConfigService appConfigService;

  private Logger logger = LoggerFactory.getLogger(AppConfigController.class);

  private static final int NUMBER_FOR_LIST = 0;

  @GetMapping("")
  public ResponseEntity<GetAppConfigResponse> getAppConfig(final HttpServletRequest request) {
    AppConfig appConfig = appConfigService.getOneAppConfig();
    logger.info("Get app config:{}", appConfig);
    return new ResponseEntity<>(
            mapStructMapper.getAppConfigResponse(appConfig),
            HttpStatus.OK
    );
  }

  @PutMapping("")
  public ResponseEntity<UpdateAppConfigResponse> updateAppConfig(final HttpServletRequest request, @Valid @RequestBody final UpdateAppConfigRequest updateAppConfigRequest) {
    AppConfig appConfig = new AppConfig();
    appConfig = mapStructMapper.updateAppConfigRequest(updateAppConfigRequest);
    appConfig.setUserId(securityService.getIdFromSecurityService(request));
    appConfigRepository.save(appConfig);
    logger.info("Updated appConfig:{}", appConfig);
    return new ResponseEntity<>(
            mapStructMapper.updateAppConfigResponse(appConfig),
            HttpStatus.OK
    );
  }

}
