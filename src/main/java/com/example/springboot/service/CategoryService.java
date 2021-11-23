package com.example.springboot.service;

import com.example.springboot.controller.AdvertisementController;
import com.example.springboot.interfaces.CategoryStatisticView;
import com.example.springboot.interfaces.SumAndCountView;
import com.example.springboot.model.Category;
import com.example.springboot.repository.AdvertisementRepository;
import com.example.springboot.repository.CategoryRepository;
import com.example.springboot.response.CategoryStatisticsResponse;
import com.example.springboot.response.GetSellerStatisticForCategoryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {


  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private AdvertisementRepository advertisementRepository;

  private Logger logger = LoggerFactory.getLogger(AdvertisementController.class);


  public Category getCategoryById(final Long category) {
    return categoryRepository.findById(category).get();
  }

  public List<Category> getAllCategories() {
    return categoryRepository.findAll();

  }

  public GetSellerStatisticForCategoryResponse specificCategoryStatisticForSeller(final Long categoryId, final Long userId) {
    CategoryStatisticView categoryStatisticView = null;
    GetSellerStatisticForCategoryResponse getSellerStatisticForCategoryResponse = new GetSellerStatisticForCategoryResponse();
    if (userId == null) {
      categoryStatisticView = advertisementRepository.getNumberOfAdvertisementsPerSpecificCategoryForAdmin(categoryId);
    } else {
      categoryStatisticView = advertisementRepository.getNumberOfAdvertisementsPerSpecificCategoryForSeller(userId, categoryId);
    }
    getSellerStatisticForCategoryResponse.setId(categoryStatisticView.getId());
    getSellerStatisticForCategoryResponse.setPricePerCategory(categoryStatisticView.getPricePerCategory());
    getSellerStatisticForCategoryResponse.setNumberOfAdvertisementsPerCategory(categoryStatisticView.getNumberOfAdvertisementsPerCategory());
    getSellerStatisticForCategoryResponse.setCategoryName(categoryStatisticView.getCategoryName());
    return getSellerStatisticForCategoryResponse;
  }


  public CategoryStatisticsResponse categoryStatisticsForSeller(final Long userId) {
    CategoryStatisticsResponse categoryStatisticsResponse = new CategoryStatisticsResponse();
    List<GetSellerStatisticForCategoryResponse> getSellerStatisticForCategoryResponses = new ArrayList<>();
    List<CategoryStatisticView> categoryStatisticViews = new ArrayList<>();
    SumAndCountView sumAndCountView = null;
    if (userId == null) {
      categoryStatisticViews = advertisementRepository.getNumberOfAdvertisementsPerCategoryForAdmin();
      sumAndCountView = advertisementRepository.getPrizeAndNumberAdvertisementsForAdmin();
    } else {
      categoryStatisticViews = advertisementRepository.getNumberOfAdvertisementsPerCategoryForSeller(userId);
      sumAndCountView = advertisementRepository.getPrizeAndNumberAdvertisementsForSeller(userId);
    }

    for (int i = 0; i < categoryStatisticViews.size(); i++) {
      GetSellerStatisticForCategoryResponse getSellerStatisticForCategory = new GetSellerStatisticForCategoryResponse();
      getSellerStatisticForCategory.setCategoryName(categoryStatisticViews.get(i).getCategoryName());
      getSellerStatisticForCategory.setId(categoryStatisticViews.get(i).getId());
      getSellerStatisticForCategory.setNumberOfAdvertisementsPerCategory(categoryStatisticViews.get(i).getNumberOfAdvertisementsPerCategory());
      getSellerStatisticForCategory.setPricePerCategory(categoryStatisticViews.get(i).getPricePerCategory());
      getSellerStatisticForCategoryResponses.add(getSellerStatisticForCategory);
    }
    categoryStatisticsResponse.setGetSellerStatisticForCategories(getSellerStatisticForCategoryResponses);
    categoryStatisticsResponse.setTotalAdvertisement(sumAndCountView.getNumberOfAdvertisements());
    categoryStatisticsResponse.setTotalPrize(sumAndCountView.getPricePerCategory());

    return categoryStatisticsResponse;
  }


  public void deleteCategories() {
    categoryRepository.deleteAll();

  }

}
