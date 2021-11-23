package com.example.springboot.controller;

import com.example.springboot.mappers.MapStructMapper;
import com.example.springboot.model.Advertisement;
import com.example.springboot.response.CategoryStatisticsResponse;
import com.example.springboot.response.GetAdvertisementResponse;
import com.example.springboot.response.GetSellerStatisticForCategoryResponse;
import com.example.springboot.service.AdvertisementService;
import com.example.springboot.service.CategoryService;
import com.example.springboot.service.SecurityService;
import com.example.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/statistics")
@CrossOrigin
public class StatisticsController {

  @Autowired
  private SecurityService securityService;

  @Autowired
  private UserService userService;

  @Autowired
  private CategoryService categoryService;


  @Autowired
  private MapStructMapper mapStructMapper;

  @Autowired
  private AdvertisementService advertisementService;

  @GetMapping("/advertisements")
  public ResponseEntity<List<GetAdvertisementResponse>> getAllAdvertisementsForUser(final HttpServletRequest request) {
    if (securityService.hasRole(request, "ADMIN")) {
      return new ResponseEntity<>(
              mapStructMapper.getListOfAdvertisemens(advertisementService.getAllAdvertisementsForAdmin()),
              HttpStatus.OK
      );
    }
    List<Advertisement> advertisements = userService.getAllAdvertisementsForUser(securityService.getIdFromSecurityService(request));
    return new ResponseEntity<>(
            mapStructMapper.getListOfAdvertisemens(advertisements),
            HttpStatus.OK
    );
  }

  @GetMapping("/categories")
  public ResponseEntity<CategoryStatisticsResponse> getCategoryStatisticForSeller(final HttpServletRequest request) {
    if (securityService.hasRole(request, "ADMIN")) {
      return new ResponseEntity<>(
              categoryService.categoryStatisticsForSeller(null),
              HttpStatus.OK
      );
    }

    return new ResponseEntity<>(
            categoryService.categoryStatisticsForSeller(securityService.getIdFromSecurityService(request)),
            HttpStatus.OK
    );
  }

  @GetMapping("/categories/{id}")
  public ResponseEntity<GetSellerStatisticForCategoryResponse> getSpecificCategoryStatisticForSeller(@PathVariable final Long id, final HttpServletRequest request) {
    if (securityService.hasRole(request, "ADMIN")) {
      return new ResponseEntity<>(
              categoryService.specificCategoryStatisticForSeller(id, null),
              HttpStatus.OK
      );
    }

    return new ResponseEntity<>(
            categoryService.specificCategoryStatisticForSeller(id, securityService.getIdFromSecurityService(request)),
            HttpStatus.OK
    );
  }


}
