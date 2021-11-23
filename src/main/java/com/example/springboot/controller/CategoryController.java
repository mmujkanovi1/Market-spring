package com.example.springboot.controller;

import com.example.springboot.mappers.MapStructMapper;
import com.example.springboot.model.Category;
import com.example.springboot.repository.CategoryRepository;
import com.example.springboot.request.CreateCategoryRequest;
import com.example.springboot.request.UpdateCategoryRequest;
import com.example.springboot.response.CreateCategoryResponse;
import com.example.springboot.response.DeleteCategoryResponse;
import com.example.springboot.response.GetCategoryResponse;
import com.example.springboot.response.UpdateCategoryResponse;
import com.example.springboot.service.CategoryService;
import com.example.springboot.service.SecurityService;
import com.example.springboot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@RestController
@Validated
@RequestMapping("/categories")
@CrossOrigin
public class CategoryController {

  private static final int TOKEN_SUBSTRING = 7;

  @Autowired
  private SecurityService securityService;

  @Autowired
  private UserService userService;

  @Autowired
  private CategoryService categoryService;


  private CategoryRepository categoryRepository;

  private MapStructMapper mapStructMapper;

  private Logger logger = LoggerFactory.getLogger(CategoryController.class);

  @Autowired
  public CategoryController(final CategoryRepository categoryRepository, final MapStructMapper mapStructMapper) {
    this.categoryRepository = categoryRepository;
    this.mapStructMapper = mapStructMapper;
  }

  @GetMapping("/{id}")
  public ResponseEntity<GetCategoryResponse> getCategoryById(@PathVariable final Long id) {
    Category findCategory = categoryRepository.findById(id).orElseThrow(() ->
            new NoSuchElementException("No such category with an id " + id));
    logger.info("Get category with id:{}", findCategory);
    return new ResponseEntity<>(
            mapStructMapper.getCategoryResponse(findCategory),
            HttpStatus.OK
    );

  }

  @GetMapping("")
  public ResponseEntity<List<GetCategoryResponse>> getCategories() {
    List<Category> categories = categoryService.getAllCategories();
    logger.info("Get list of categories:{}", categories);
    return new ResponseEntity<>(
            mapStructMapper.getListOfCategories(categories),
            HttpStatus.OK
    );
  }

  @PostMapping("")
  @PreAuthorize("@securityService.hasRole(#request,'ADMIN')")
  public ResponseEntity<CreateCategoryResponse> createCategory(final HttpServletRequest request, @Valid @RequestBody final CreateCategoryRequest oldCategory) {
    Category newCategory = mapStructMapper.createCategoryRequest(oldCategory, userService.getUserById(securityService.getIdFromSecurityService(request)));
    categoryRepository.save(newCategory);
    logger.info("Created new category: {}", newCategory);
    return new ResponseEntity<>(
            mapStructMapper.createCategoryResponse(newCategory),
            HttpStatus.OK
    );

  }

  @PutMapping("/{id}")
  @PreAuthorize("@securityService.hasRole(#request,'ADMIN')")
  public ResponseEntity<UpdateCategoryResponse> updateCategory(final HttpServletRequest request, @Valid @RequestBody final UpdateCategoryRequest oldCategory, @PathVariable final Long id) {

    oldCategory.setId(id);
    Category newCategory = mapStructMapper.updateCategoryRequest(oldCategory, userService.getUserById(securityService.getIdFromSecurityService(request)));
    categoryRepository.save(newCategory);
    logger.info("Updated new category: {}", newCategory);
    return new ResponseEntity<>(
            mapStructMapper.updateCategoryResponse(newCategory),
            HttpStatus.OK
    );
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("@securityService.hasId(#request,@categoryRepository.findById(#id).get().getUser().getId())")
  public ResponseEntity<DeleteCategoryResponse> deleteCategory(final HttpServletRequest request, @PathVariable final Long id) {
    Category deletedCategory = categoryRepository.findById(id).orElseThrow(() ->
            new NoSuchElementException("You cannot delete a category because a category with an id " + id + " does not exist "));
    categoryRepository.delete(deletedCategory);
    logger.info("Deleted category: {}", deletedCategory);
    return new ResponseEntity<>(
            mapStructMapper.deleteCategoryResponse(deletedCategory),
            HttpStatus.OK
    );
  }
}
