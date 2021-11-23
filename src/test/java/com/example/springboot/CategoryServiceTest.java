package com.example.springboot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

import com.example.springboot.model.Advertisement;
import com.example.springboot.model.Category;
import com.example.springboot.model.Role;
import com.example.springboot.model.User;
import com.example.springboot.repository.AdvertisementRepository;
import com.example.springboot.repository.CategoryRepository;
import com.example.springboot.repository.RoleRepository;
import com.example.springboot.repository.UserRepository;
import com.example.springboot.response.CategoryStatisticsResponse;
import com.example.springboot.response.GetSellerStatisticForCategoryResponse;
import com.example.springboot.service.AdvertisementService;
import com.example.springboot.service.CategoryService;
import com.example.springboot.service.UserService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;


@TestInstance(PER_CLASS)
@SpringBootTest
@ActiveProfiles("test")
class CategoryServiceTest {

  private static final BigDecimal ADVERTISEMENT_PRIZE = BigDecimal.valueOf(100);
  private static final BigDecimal ADVERTISEMENT1_PRIZE = BigDecimal.valueOf(300);
  private static final BigDecimal ADVERTISEMENT2_PRIZE = BigDecimal.valueOf(50);
  private static final BigDecimal ADVERTISEMENT3_PRIZE = BigDecimal.valueOf(65000);
  private static final BigDecimal ADVERTISEMENT4_PRIZE = BigDecimal.valueOf(70000);

  private BigDecimal categoryPrizeForAdmin;
  private BigDecimal category1PrizeForAdmin;

  private BigDecimal categoryPrizeForSellerUser;
  private BigDecimal category1PrizeForSellerUser;
  private BigDecimal categoryPrizeForSellerUser2;
  private BigDecimal category1PrizeForSellerUser2;

  private BigDecimal prizeForSellerUserAdvertisements;
  private BigDecimal prizeForSellerUser2Advertisements;
  private BigDecimal prizeForAdminAdvertisements;

  private Long categoryVehicleId;
  private Long categoryHouseId;

  private Long userSellerId;
  private Long userSeller2Id;


  @Autowired
  private CategoryService categoryService;

  @Autowired
  private AdvertisementService advertisementService;

  @Autowired
  private UserService userService;

  @Autowired
  private RoleRepository roleRepository;


  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private AdvertisementRepository advertisementRepository;

  @Autowired
  private UserRepository userRepository;

  @BeforeAll
  void setUp() {
    Role admin = roleRepository.findById(1L).get();
    Role user = roleRepository.findById(2L).get();
    User sellerUser = new User("name", "surname", "0983934893", "mirza26@gmail.com", "mmujkanovi1", "password", user);
    User adminUser = new User("name3", "surname3", "0983934893", "mirza126@gmail.com", "mmujkanovi3", "password3", admin);
    User sellerUser2 = new User("name1", "surname1", "034023905049", "mirza266@gmail.com", "mmujkanovi2", "password2", user);
    userRepository.save(sellerUser);
    userRepository.save(sellerUser2);
    userRepository.save(adminUser);
    Category category = new Category("vehicle");
    Category category1 = new Category("house");
    categoryRepository.save(category).setUser(userRepository.findByUsername("mmujkanovi3"));
    categoryRepository.save(category1).setUser(userRepository.findByUsername("mmujkanovi3"));

    sellerUser = userRepository.findByUsername("mmujkanovi1");
    sellerUser2 = userRepository.findByUsername("mmujkanovi2");

    final Advertisement advertisement = new Advertisement("car", "car description", category, ADVERTISEMENT_PRIZE, sellerUser);
    final Advertisement advertisement1 = new Advertisement("truck", "truck description", category, ADVERTISEMENT1_PRIZE, sellerUser);
    final Advertisement advertisement2 = new Advertisement("engine", "engine description", category, ADVERTISEMENT2_PRIZE, sellerUser2);
    categoryPrizeForAdmin = ADVERTISEMENT_PRIZE.add(ADVERTISEMENT1_PRIZE).add(ADVERTISEMENT2_PRIZE);

    final Advertisement advertisement3 = new Advertisement("apartment", "apartment description", category1, ADVERTISEMENT3_PRIZE, sellerUser2);
    final Advertisement advertisement4 = new Advertisement("cottage", "cottage description", category1, ADVERTISEMENT4_PRIZE, sellerUser);
    category1PrizeForAdmin = ADVERTISEMENT3_PRIZE.add(ADVERTISEMENT4_PRIZE);

    categoryPrizeForSellerUser = ADVERTISEMENT1_PRIZE.add(ADVERTISEMENT_PRIZE);
    category1PrizeForSellerUser = ADVERTISEMENT4_PRIZE;
    categoryPrizeForSellerUser2 = ADVERTISEMENT2_PRIZE;
    category1PrizeForSellerUser2 = ADVERTISEMENT3_PRIZE;


    Category categoryVehicle = categoryRepository.findByCategoryName("vehicle");
    Category categoryHouse = categoryRepository.findByCategoryName("house");

    advertisementRepository.save(advertisement).setCategory(categoryVehicle);
    advertisementRepository.save(advertisement1).setCategory(categoryVehicle);
    advertisementRepository.save(advertisement2).setCategory(categoryVehicle);
    advertisementRepository.save(advertisement3).setCategory(categoryHouse);
    advertisementRepository.save(advertisement4).setCategory(categoryHouse);

    prizeForSellerUserAdvertisements = ADVERTISEMENT_PRIZE.add(ADVERTISEMENT1_PRIZE).add(ADVERTISEMENT4_PRIZE);
    prizeForSellerUser2Advertisements = ADVERTISEMENT2_PRIZE.add(ADVERTISEMENT3_PRIZE);

    prizeForAdminAdvertisements = ADVERTISEMENT_PRIZE.add(ADVERTISEMENT1_PRIZE).add(ADVERTISEMENT2_PRIZE)
            .add(ADVERTISEMENT3_PRIZE).add(ADVERTISEMENT4_PRIZE);

    userSellerId = sellerUser.getId();
    userSeller2Id = sellerUser2.getId();

    categoryVehicleId = categoryVehicle.getId();
    categoryHouseId = categoryHouse.getId();
  }

  @Test
  void specificCategoryStatisticForSeller() {
    GetSellerStatisticForCategoryResponse adminStatsForSpecificCategory = new GetSellerStatisticForCategoryResponse();
    adminStatsForSpecificCategory = categoryService.specificCategoryStatisticForSeller(categoryVehicleId, null);
    assertTrue(categoryPrizeForAdmin.compareTo(adminStatsForSpecificCategory.getPricePerCategory()) == 0);

    adminStatsForSpecificCategory = categoryService.specificCategoryStatisticForSeller(categoryHouseId, null);
    assertTrue(category1PrizeForAdmin.compareTo(adminStatsForSpecificCategory.getPricePerCategory()) == 0);


    GetSellerStatisticForCategoryResponse userStatsForSpecificCategory = new GetSellerStatisticForCategoryResponse();
    userStatsForSpecificCategory = categoryService.specificCategoryStatisticForSeller(categoryVehicleId, userSellerId);
    assertTrue(categoryPrizeForSellerUser.compareTo(userStatsForSpecificCategory.getPricePerCategory()) == 0);

    userStatsForSpecificCategory = categoryService.specificCategoryStatisticForSeller(categoryVehicleId, userSeller2Id);
    assertTrue(categoryPrizeForSellerUser2.compareTo(userStatsForSpecificCategory.getPricePerCategory()) == 0);

    userStatsForSpecificCategory = categoryService.specificCategoryStatisticForSeller(categoryHouseId, userSellerId);
    assertTrue(category1PrizeForSellerUser.compareTo(userStatsForSpecificCategory.getPricePerCategory()) == 0);

    userStatsForSpecificCategory = categoryService.specificCategoryStatisticForSeller(categoryHouseId, userSeller2Id);
    assertTrue(category1PrizeForSellerUser2.compareTo(userStatsForSpecificCategory.getPricePerCategory()) == 0);

  }

  @Test
  void categoryStatisticsForSeller() {
    CategoryStatisticsResponse categoryStatisticsResponse = new CategoryStatisticsResponse();
    categoryStatisticsResponse = categoryService.categoryStatisticsForSeller(userSellerId);
    assertEquals(prizeForSellerUserAdvertisements.setScale(2), categoryStatisticsResponse.getTotalPrize().setScale(2));

    categoryStatisticsResponse = categoryService.categoryStatisticsForSeller(userSeller2Id);
    assertEquals(prizeForSellerUser2Advertisements.setScale(2), categoryStatisticsResponse.getTotalPrize().setScale(2));

  }


  @AfterAll
  void tearDown() {
    advertisementService.deleteAdvertisements();
    categoryService.deleteCategories();
    userService.deleteAllUsers();
  }
}