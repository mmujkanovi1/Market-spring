package com.example.springboot.service;

import com.example.springboot.model.Advertisement;
import com.example.springboot.repository.AdvertisementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdvertisementService {

  @Autowired
  private AdvertisementRepository advertisementRepository;

  @Autowired
  private AppConfigService appConfigService;

  public void saveAdvertisement(final Advertisement advertisement) {
    advertisementRepository.save(advertisement);
  }

  public void deleteAdvertisements() {
    advertisementRepository.deleteAll();
  }

  public Advertisement getAdvertisementById(final Long id) {
    return advertisementRepository.findById(id).get();
  }

  public List<Advertisement> getAllAdvertisementsForAdmin() {
    List<Advertisement> advertisements = advertisementRepository.findAll();
    return advertisements;
  }

  public Page<Advertisement> getAllAdvertisements(final Pageable pageable) {


    String newSortBy = null;
    Sort.Direction direction = null;


    for (Sort.Order order : pageable.getSort()) {
      newSortBy = order.getProperty();
      direction = order.getDirection();
    }
    if (newSortBy == null) {
      newSortBy = appConfigService.getOneAppConfig().getAdvertisementPageDefaultSortField();
    }
    if (direction == null) {
      direction = Sort.Direction.ASC;
    }
    Integer pageNo = pageable.getPageSize();
    if (pageNo == null) {
      pageNo = appConfigService.getOneAppConfig().getAdvertisementPageItemsNo();
    }


    Pageable paging = PageRequest.of(pageable.getPageNumber(), pageNo, Sort.by(direction, newSortBy));
    Page<Advertisement> pagedResult = advertisementRepository.findAll(paging);

    return pagedResult;

  }
}
