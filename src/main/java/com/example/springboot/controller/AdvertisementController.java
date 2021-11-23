package com.example.springboot.controller;

import com.example.springboot.clients.CurrencyClient;
import com.example.springboot.mappers.MapStructMapper;
import com.example.springboot.model.Advertisement;
import com.example.springboot.repository.AdvertisementRepository;
import com.example.springboot.request.CreateAdvertisementRequest;
import com.example.springboot.request.UpdateAdvertisementRequest;
import com.example.springboot.response.CreateAdvertisementResponse;
import com.example.springboot.response.DeleteAdvertisementResponse;
import com.example.springboot.response.GetAdvertisementResponse;
import com.example.springboot.response.GetAdvertisementWithUsdResponse;
import com.example.springboot.response.UpdateAdvertisementResponse;
import com.example.springboot.service.AdvertisementService;
import com.example.springboot.service.CategoryService;
import com.example.springboot.service.ImageService;
import com.example.springboot.service.ScheduledConversionService;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.NoSuchElementException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@RestController
@Validated
@RequestMapping("/advertisements")
@CrossOrigin
public class AdvertisementController {

  @Autowired
  private ImageService imageService;

  @Autowired
  private UserService userService;

  @Autowired
  private SecurityService securityService;

  @Autowired
  private AdvertisementService advertisementService;

  @Autowired
  private CategoryService categoryService;

  @Autowired
  private CurrencyClient client;

  @Autowired
  private ScheduledConversionService scheduledConversionService;

  private AdvertisementRepository advertisementRepository;

  private MapStructMapper mapStructMapper;

  private Logger logger = LoggerFactory.getLogger(AdvertisementController.class);

  @Autowired
  private PagedResourcesAssembler pagedResourcesAssembler;

  @Autowired
  public AdvertisementController(final AdvertisementRepository advertisementRepository, final MapStructMapper mapStructMapper) {
    this.advertisementRepository = advertisementRepository;
    this.mapStructMapper = mapStructMapper;
  }

  @GetMapping("")
  public ResponseEntity<PagedModel<EntityModel<GetAdvertisementResponse>>> getAdvertisements(final Pageable pageable) {
    Page<Advertisement> getAllAdvertisements = advertisementService.getAllAdvertisements(pageable);
    Page<GetAdvertisementResponse> advertisementResponsePage = getAllAdvertisements.map(advertisement -> {
      return mapStructMapper.getAdvertisementResponse(advertisement);
    });
    PagedModel<EntityModel<GetAdvertisementResponse>> pagedModel = pagedResourcesAssembler.toModel(advertisementResponsePage);

    logger.info("Get list of advertisements: {}", getAllAdvertisements);
    return new ResponseEntity<>(
            pagedModel,
            HttpStatus.OK
    );
  }

  @GetMapping("/{id}")
  public ResponseEntity<GetAdvertisementWithUsdResponse> getAdvertisementById(@PathVariable final Long id) throws ParseException {
    Advertisement findAdvertisement = advertisementRepository.findById(id).orElseThrow(() ->
            new NoSuchElementException("No such advertisement with an id " + id));
    BigDecimal usdPrice = scheduledConversionService.eurIntoUsd(findAdvertisement.getPricelnEuro());
    logger.info("Get advertisement with id: {}", findAdvertisement);
    return new ResponseEntity<>(
            mapStructMapper.getAdvertisementWithUsdResponse(findAdvertisement, usdPrice, findAdvertisement.getCategory().getId()),
            HttpStatus.OK
    );
  }

  @PostMapping("")
  @PreAuthorize("@securityService.hasRole(#request,'SELLER')")
  public ResponseEntity<CreateAdvertisementResponse> postAdvertizer(final HttpServletRequest request, @Valid @RequestBody final CreateAdvertisementRequest advertisement) throws IOException {
    Advertisement newAdvertisement = mapStructMapper.createAdvertisementRequest(advertisement, advertisement.getPricelnEuro(), userService.getUserById(securityService.getIdFromSecurityService(request)), categoryService.getCategoryById(advertisement.getCategoryId()));
    advertisementRepository.save(newAdvertisement);
    //imageService.saveImages(file, newAdvertisement);
    logger.info("Created new advertisement: {}", newAdvertisement);
    return new ResponseEntity<>(
            mapStructMapper.createAdvertisementResponse(newAdvertisement),
            HttpStatus.OK
    );
  }

  // @PutMapping("/{id}")
  // @PreAuthorize("@userService.getUserById(@securityService.getIdFromSecurityService(#request)).getAdvertisements().contains(#newAdvertisement)")
  //@PreAuthorize("@securityService.hasId(#request,#oldAdvertisement.getUserId())")
  @PutMapping("/{id}")
  @PreAuthorize("@userService.getUserById(@securityService.getIdFromSecurityService(#request)).getAdvertisements().contains(@advertisementService.getAdvertisementById(#oldAdvertisement.getId()))")
  public ResponseEntity<UpdateAdvertisementResponse> updateAdvertisement(final HttpServletRequest request, @RequestBody final UpdateAdvertisementRequest oldAdvertisement, @PathVariable final Long id) {
    // userService.getUserById(securityService.getIdFromSecurityService(request)).getAdvertisements().contains(newAdvertisement)
    //advertisementService.getAdvertisementById(oldAdvertisement.getId())
    oldAdvertisement.setId(id);
    Advertisement newAdvertisement = mapStructMapper.updateAdvertisementRequest(oldAdvertisement, userService.getUserById(securityService.getIdFromSecurityService(request)), categoryService.getCategoryById(oldAdvertisement.getCategoryId()));
    advertisementRepository.save(newAdvertisement);
    logger.info("Updated advertisement: {}", newAdvertisement);
    return new ResponseEntity<>(
            mapStructMapper.updateAdvertisementResponse(newAdvertisement),
            HttpStatus.OK
    );
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("@securityService.hasId(#request,@advertisementRepository.findById(#id).get().getUser().getId())")
  public ResponseEntity<DeleteAdvertisementResponse> deleteAdvertisement(final HttpServletRequest request, @PathVariable final Long id) {
    Advertisement deletedAdvertisement = advertisementRepository.findById(id).orElseThrow(() ->
            new NoSuchElementException("You cannot delete a advertisement because a advertisement with an id " + id + " does not exist "));
    advertisementRepository.delete(deletedAdvertisement);
    logger.info("Deleted advertisement: {}", deletedAdvertisement);
    return new ResponseEntity<>(
            mapStructMapper.deleteAdvertisementResponse(deletedAdvertisement),
            HttpStatus.OK
    );
  }
}
