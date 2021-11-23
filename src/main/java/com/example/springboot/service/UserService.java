package com.example.springboot.service;

import com.example.springboot.mappers.MapStructMapper;
import com.example.springboot.model.Advertisement;
import com.example.springboot.model.User;
import com.example.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

  @Autowired
  private AppConfigService appConfigService;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private MapStructMapper mapStructMapper;

  @Autowired
  private CategoryService categoryService;

  public List<Advertisement> getAllAdvertisementsForUser(final Long id) {
    List<Advertisement> advertisements = userRepository.findById(id).get().getAdvertisements();
    return advertisements;
  }

  public void saveUser(final User user) {
    userRepository.save(user);
  }

  public void deleteAllUsers() {
    userRepository.deleteAll();
  }

  public User getUserById(final Long user) {
    return userRepository.findById(user).get();
  }

  public Long getUserIdByUsername(final String username) {
    return userRepository.findByUsername(username).getId();
  }

  public Page<User> getAllUsers(final Pageable pageable) {


    String newSortBy = null;
    Sort.Direction direction = null;

    for (Sort.Order order : pageable.getSort()) {
      newSortBy = order.getProperty();
      direction = order.getDirection();
    }
    if (newSortBy == null) {
      newSortBy = appConfigService.getOneAppConfig().getUserPageDefaultSortField();
    }
    if (direction == null) {
      direction = Sort.Direction.ASC;
    }
    Integer pageNo = pageable.getPageSize();
    if (pageNo == null) {
      pageNo = appConfigService.getOneAppConfig().getUserPageItemsNo();
    }


    Pageable paging = PageRequest.of(pageable.getPageNumber(), pageNo, Sort.by(direction, newSortBy));
    Page<User> pagedResult = userRepository.findAll(paging);

    return pagedResult;

  }

}
