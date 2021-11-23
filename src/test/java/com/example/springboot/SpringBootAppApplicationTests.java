package com.example.springboot;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasKey;

import com.example.springboot.clients.CurrencyClient;
import com.example.springboot.controller.StatisticsController;
import com.example.springboot.model.User;
import com.example.springboot.repository.CurrencyRepository;
import com.example.springboot.request.CreateAdvertisementRequest;
import com.example.springboot.request.CreateCategoryRequest;
import com.example.springboot.request.CreateUserRequest;
import com.example.springboot.request.LoginRequest;
import com.example.springboot.request.UpdateAdvertisementRequest;
import com.example.springboot.request.UpdateAppConfigRequest;
import com.example.springboot.request.UpdateCategoryRequest;
import com.example.springboot.request.UpdateUserRequest;
import com.example.springboot.service.CategoryService;
import com.example.springboot.service.UserService;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;


@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringBootAppApplicationTests {


  @Autowired
  private MockMvc mockMvc;

  @LocalServerPort
  private Integer port;

  private String host = "http://localhost:";

  private String pathForUser = "/users";

  private String pathForUserWithId = "/users/{id}";

  private String pathForAdvertisement = "/advertisements";

  private String pathForAdvertisementWithId = "/advertisements/{id}";

  private String pathForCategory = "/categories";

  private String pathForCategoryWithId = "/categories/{id}";

  private String idString = "id";

  private String pathForLoginUser = "/login";

  private String pathForAppConfig = "/config";

  private String pathForAdvertisementStatistic = "/statistics/advertisements";

  private String pathForCategoryStatistic = "/statistics/categories";

  private String pathForSpecificCategoryStatistic = "/statistics/categories/{id}";

  private String prizeUsd = "prizelnUsd";

  private static final BigDecimal ADVERTISEMENT_PRIZE = BigDecimal.valueOf(200);

  private static final BigDecimal ADVERTISEMENT_PRIZE_UPDATE = BigDecimal.valueOf(250);

  private static final Integer ADVERTISEMENT_PAGINATION = 20;

  private static final Integer PATH_FOR_BAD_REQUEST = 100;

  private static final float ADVERTISEMENT_PRIZE_USD = 289.52502F;

  private static final Integer PORT_FOR_WIRE_MOCK = 7070;

  private static final double USD = 6.5434;

  private static final double EUR = 6.1244;

  @Autowired
  private CategoryService categoryService;


  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  private UserService userService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private StatisticsController statisticsController;

  private static WireMockServer wireMockServer;

  @Autowired
  private CurrencyRepository currencyRepository;

  @Autowired
  private CurrencyClient currencyClient;


  @Value("${value}")
  private String url;

  @BeforeAll
  void init() {
    wireMockServer = new WireMockServer(PORT_FOR_WIRE_MOCK);
    wireMockServer.start();
    WireMock.configureFor("localhost", PORT_FOR_WIRE_MOCK);
    stubFor(WireMock.get(url)
            .willReturn(aResponse()
                    .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                    .withBody("[\n"
                            + "  {\n"
                            + "    \"Kupovni za devize\": \"6.5434\"\n"
                            + "  },\n"
                            + "  {\n"
                            + "    \"Kupovni za devize\": \"6.1244\"\n"
                            + "  }\n"
                            + "]")
                    .withStatus(HttpStatus.OK.value())));
  }

  @Test
  void contextLoads() {

  }

  private Float advertisementPrize() {
    double usdEur = EUR / USD;
    return ADVERTISEMENT_PRIZE_UPDATE.multiply(BigDecimal.valueOf(usdEur)).floatValue();
  }

  private CreateUserRequest createAdminRequestBody() {
    CreateUserRequest createUserRequest = new CreateUserRequest();
    createUserRequest.setDisplayName("Mirza");
    createUserRequest.setDisplaySurname("Mujkanovic");
    createUserRequest.setPhoneNumber("0623999541");
    createUserRequest.setEmail("mmujkano@gmail.com");
    createUserRequest.setPassword("password");
    createUserRequest.setRoleId((long) 1);
    createUserRequest.setUsername("maxi");
    return createUserRequest;
  }

  private CreateUserRequest createUserRequestBody() {
    CreateUserRequest createUserRequest = new CreateUserRequest();
    createUserRequest.setDisplayName("Mirza");
    createUserRequest.setDisplaySurname("Mujkanovic");
    createUserRequest.setPhoneNumber("0623999541");
    createUserRequest.setEmail("mmujkano@gmail.com");
    createUserRequest.setPassword("password");
    createUserRequest.setRoleId((long) 2);
    createUserRequest.setUsername("maximm");
    return createUserRequest;
  }


  private CreateUserRequest createUserRequestBodyforStatus400() {
    CreateUserRequest createUserRequest = new CreateUserRequest();
    createUserRequest.setDisplayName("Mirza");
    createUserRequest.setDisplaySurname("Mujkanovic");
    createUserRequest.setPhoneNumber("string");
    createUserRequest.setEmail("mmujkano");
    createUserRequest.setPassword("password");
    createUserRequest.setRoleId((long) 2);
    createUserRequest.setUsername("max");
    return createUserRequest;
  }


  private CreateAdvertisementRequest createAdvertisementRequestBody(final Long userId, final Long categoryId) {
    CreateAdvertisementRequest createAdvertisementRequest = new CreateAdvertisementRequest();
    createAdvertisementRequest.setTitle("title");
    createAdvertisementRequest.setDescription("description for advertisement");
    createAdvertisementRequest.setCategoryId(categoryId);
    createAdvertisementRequest.setPricelnEuro(ADVERTISEMENT_PRIZE);
    return createAdvertisementRequest;
  }

  private CreateCategoryRequest createCategoryRequestBody(final Long userId) {
    CreateCategoryRequest createCategoryRequest = new CreateCategoryRequest();
    createCategoryRequest.setCategoryName("category");
    return createCategoryRequest;
  }


  private UpdateUserRequest updateUserRequestBody() {
    UpdateUserRequest updateUserRequest = new UpdateUserRequest();
    updateUserRequest.setDisplayName("updateName");
    updateUserRequest.setDisplaySurname("updateSurname");
    updateUserRequest.setEmail("updateEmail@gmail.com");
    updateUserRequest.setPhoneNumber("4325431234");
    updateUserRequest.setRoleId((long) 1);
    updateUserRequest.setPassword("password");
    updateUserRequest.setUsername("maxi");
    return updateUserRequest;
  }

  private UpdateAdvertisementRequest updateAdvertisementRequestBody(final Long userId, final Long categoryId) {
    UpdateAdvertisementRequest updateAdvertisementRequest = new UpdateAdvertisementRequest();
    updateAdvertisementRequest.setDescription("updateDescription for advertisement");
    updateAdvertisementRequest.setTitle("updateTitle");
    updateAdvertisementRequest.setCategoryId(categoryId);
    updateAdvertisementRequest.setPricelnEuro(ADVERTISEMENT_PRIZE_UPDATE);
    return updateAdvertisementRequest;
  }

  private UpdateCategoryRequest updateCategoryRequestBody(final Long userId) {
    UpdateCategoryRequest updateCategoryRequest = new UpdateCategoryRequest();
    updateCategoryRequest.setCategoryName("updateCategory");
    return updateCategoryRequest;
  }


  private UpdateAppConfigRequest updateAppConfigRequestBody() {
    UpdateAppConfigRequest updateAppConfigRequest = new UpdateAppConfigRequest();
    updateAppConfigRequest.setAdvertisementPageDefaultSortField("id");
    updateAppConfigRequest.setAdvertisementPageItemsNo(ADVERTISEMENT_PAGINATION);
    updateAppConfigRequest.setUserPageDefaultSortField("id");
    updateAppConfigRequest.setUserPageItemsNo(ADVERTISEMENT_PAGINATION);
    return updateAppConfigRequest;
  }


  private Integer createAndReturnAdminId() {

    return RestAssured
            .given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(createAdminRequestBody())
            .when()
            .post(host + port + pathForUser)
            .then()
            .extract()
            .path(idString);

  }

  private Integer createAndReturnUserId() {

    return RestAssured
            .given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(createUserRequestBody())
            .when()
            .post(host + port + pathForUser)
            .then()
            .extract()
            .path(idString);

  }

  private Integer createAndReturnAdvertisementId(final Long userId, final Long categoryId, final String jwt) {


    return RestAssured
            .given()
            .header("Authorization", "Bearer " + jwt)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(createAdvertisementRequestBody(userId, categoryId))
            .when()
            .post(host + port + pathForAdvertisement)
            .then()
            .extract()
            .path(idString);
  }

  private Integer createAndReturnCategoryId(final Long userId, final String jwt) {
    return RestAssured
            .given()
            .header("Authorization", "Bearer " + jwt)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(createCategoryRequestBody(userId))
            .when()
            .post(host + port + pathForCategory)
            .then()
            .extract()
            .path(idString);
  }

  private String loginAndReturnJwt(final User user) {
    LoginRequest loginRequest = new LoginRequest();
    loginRequest.setPassword("password");
    loginRequest.setUsername(user.getUsername());
    String token = RestAssured
            .given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(loginRequest)
            .when()
            .post(host + port + pathForLoginUser)
            .jsonPath()
            .get("token");
    return token;
  }

  @Test
  void shouldhaveStatus200() throws Exception {
    Integer id = createAndReturnAdminId();
    String jwt = loginAndReturnJwt(userService.getUserById(Long.valueOf(id)));
    RestAssured
            .given()
            .header("Authorization", "Bearer " + jwt)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .pathParam(idString, id)
            .body(updateUserRequestBody())
            .when()
            .put(host + port + pathForUserWithId)
            .then()
            .statusCode(HttpStatus.OK.value());
    RestAssured
            .given()
            .header("Authorization", "Bearer " + jwt)
            .get(host + port + pathForUser)
            .then()
            .statusCode(HttpStatus.OK.value());
    RestAssured
            .given()
            .header("Authorization", "Bearer " + jwt)
            .pathParam(idString, Long.valueOf(id))
            .get(host + port + pathForUserWithId)
            .then()
            .statusCode(HttpStatus.OK.value())
            .body(idString, equalTo(id));
    Integer userId = createAndReturnUserId();
    Integer categoryId = createAndReturnCategoryId(Long.valueOf(id), jwt);
    String jwtUser = loginAndReturnJwt(userService.getUserById(Long.valueOf(userId)));
    Integer advertisementId = createAndReturnAdvertisementId(Long.valueOf(userId), Long.valueOf(categoryId), jwtUser);
    RestAssured
            .given()
            .header("Authorization", "Bearer " + jwt)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .pathParam(idString, Long.valueOf(advertisementId))
            .body(updateAdvertisementRequestBody(Long.valueOf(id), Long.valueOf(categoryId)))
            .when()
            .put(host + port + pathForAdvertisementWithId)
            .then()
            .statusCode(HttpStatus.OK.value());
    RestAssured
            .given()
            .header("Authorization", "Bearer " + jwt)
            .pathParam(idString, Long.valueOf(advertisementId))
            .get(host + port + pathForAdvertisementWithId)
            .then()
            .body("$", hasKey(prizeUsd))
            .body(prizeUsd, equalTo(advertisementPrize()))
            .statusCode(HttpStatus.OK.value());
    RestAssured
            .given()
            .header("Authorization", "Bearer " + jwt)
            .pathParam(idString, advertisementId)
            .delete(host + port + pathForAdvertisementWithId)
            .then()
            .statusCode(HttpStatus.OK.value())
            .body(idString, equalTo(advertisementId));
    RestAssured
            .given()
            .header("Authorization", "Bearer " + jwt)
            .pathParam(idString, categoryId)
            .delete(host + port + pathForCategoryWithId)
            .then()
            .statusCode(HttpStatus.OK.value())
            .body(idString, equalTo(categoryId));
    Integer categoryId2 = createAndReturnCategoryId(Long.valueOf(id), jwt);
    RestAssured
            .given()
            .header("Authorization", "Bearer " + jwt)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .pathParam(idString, Long.valueOf(categoryId2))
            .body(updateCategoryRequestBody(Long.valueOf(id)))
            .when()
            .put(host + port + pathForCategoryWithId)
            .then()
            .statusCode(HttpStatus.OK.value());
    RestAssured
            .given()
            .header("Authorization", "Bearer " + jwt)
            .pathParam(idString, Long.valueOf(categoryId2))
            .get(host + port + pathForCategoryWithId)
            .then()
            .statusCode(HttpStatus.OK.value())
            .body(idString, equalTo(categoryId2));
    RestAssured
            .given()
            .header("Authorization", "Bearer " + jwt)
            .pathParam(idString, Long.valueOf(PATH_FOR_BAD_REQUEST))
            .get(host + port + pathForCategoryWithId)
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    RestAssured
            .given()
            .header("Authorization", "Bearer " + jwt)
            .get(host + port + pathForCategory)
            .then()
            .statusCode(HttpStatus.OK.value())
            .body(idString, hasItems(categoryId2));
    RestAssured
            .given()
            .header("Authorization", "Bearer " + jwt)
            .pathParam(idString, categoryId2)
            .delete(host + port + pathForCategoryWithId)
            .then()
            .statusCode(HttpStatus.OK.value())
            .body(idString, equalTo(categoryId2));
    RestAssured
            .given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(createUserRequestBodyforStatus400())
            .when()
            .post(host + port + pathForUser)
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    RestAssured
            .given()
            .header("Authorization", "Bearer " + jwtUser)
            .pathParam(idString, userId)
            .delete(host + port + pathForUserWithId)
            .then()
            .statusCode(HttpStatus.OK.value())
            .body(idString, equalTo(userId));
    RestAssured
            .given()
            .header("Authorization", "Bearer " + jwt)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(updateAppConfigRequestBody())
            .when()
            .put(host + port + pathForAppConfig)
            .then()
            .statusCode(HttpStatus.OK.value());
    RestAssured
            .given()
            .header("Authorization", "Bearer " + jwt)
            .get(host + port + pathForAppConfig)
            .then()
            .statusCode(HttpStatus.OK.value());
    RestAssured
            .given()
            .header("Authorization", "Bearer " + jwt)
            .pathParam(idString, id)
            .delete(host + port + pathForUserWithId)
            .then()
            .statusCode(HttpStatus.OK.value())
            .body(idString, equalTo(id))
            .body("displayName", equalTo("updateName"));
  }

  private CreateUserRequest createAdminRequestBodyForTest2() {
    CreateUserRequest createUserRequest = new CreateUserRequest();
    createUserRequest.setDisplayName("kmvdfk");
    createUserRequest.setDisplaySurname("Mujkanobfgvic");
    createUserRequest.setPhoneNumber("0623999541");
    createUserRequest.setEmail("mmujka@gmail.com");
    createUserRequest.setPassword("password");
    createUserRequest.setRoleId((long) 1);
    createUserRequest.setUsername("mmiki");
    return createUserRequest;
  }

  private CreateUserRequest createUserRequestBodyForTest2() {
    CreateUserRequest createUserRequest = new CreateUserRequest();
    createUserRequest.setDisplayName("Mirvdfza");
    createUserRequest.setDisplaySurname("Mujkafvnovic");
    createUserRequest.setPhoneNumber("0623999541");
    createUserRequest.setEmail("mmujko@gmail.com");
    createUserRequest.setPassword("password");
    createUserRequest.setRoleId((long) 2);
    createUserRequest.setUsername("mmikim");
    return createUserRequest;
  }

  private Integer createAndReturnAdminForTest2Id() {

    return RestAssured
            .given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(createAdminRequestBodyForTest2())
            .when()
            .post(host + port + pathForUser)
            .then()
            .extract()
            .path(idString);
  }

  private Integer createAndReturnUserIdForTest2() {

    return RestAssured
            .given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(createUserRequestBodyForTest2())
            .when()
            .post(host + port + pathForUser)
            .then()
            .extract()
            .path(idString);

  }


  @Test
  void shouldHaveStatus200StatsCategory() {
    Integer id = createAndReturnAdminForTest2Id();
    String jwt = loginAndReturnJwt(userService.getUserById(Long.valueOf(id)));
    Integer userId = createAndReturnUserIdForTest2();
    Integer categoryId = createAndReturnCategoryId(Long.valueOf(id), jwt);
    String jwtUser = loginAndReturnJwt(userService.getUserById(Long.valueOf(userId)));
    Integer advertisementId = createAndReturnAdvertisementId(Long.valueOf(userId), Long.valueOf(categoryId), jwtUser);

    RestAssured
            .given()
            .header("Authorization", "Bearer " + jwt)
            .pathParam(idString, Long.valueOf(categoryId))
            .get(host + port + pathForSpecificCategoryStatistic)
            .then()
            .statusCode(HttpStatus.OK.value());
    RestAssured
            .given()
            .header("Authorization", "Bearer " + jwt)
            .get(host + port + pathForCategoryStatistic)
            .then()
            .statusCode(HttpStatus.OK.value());
    RestAssured
            .given()
            .header("Authorization", "Bearer " + jwt)
            .get(host + port + pathForAdvertisementStatistic)
            .then()
            .statusCode(HttpStatus.OK.value());

    RestAssured
            .given()
            .header("Authorization", "Bearer " + jwtUser)
            .pathParam(idString, Long.valueOf(categoryId))
            .get(host + port + pathForSpecificCategoryStatistic)
            .then()
            .statusCode(HttpStatus.OK.value());
    RestAssured
            .given()
            .header("Authorization", "Bearer " + jwtUser)
            .get(host + port + pathForCategoryStatistic)
            .then()
            .statusCode(HttpStatus.OK.value());
    RestAssured
            .given()
            .header("Authorization", "Bearer " + jwtUser)
            .get(host + port + pathForAdvertisementStatistic)
            .then()
            .statusCode(HttpStatus.OK.value());
    RestAssured
            .given()
            .header("Authorization", "Bearer " + jwt)
            .get(host + port + pathForAdvertisement)
            .then()
            .statusCode(HttpStatus.OK.value());
    RestAssured
            .given()
            .param("page", 1)
            .param("sort", "id,asc")
            .header("Authorization", "Bearer " + jwt)
            .get(host + port + pathForUser)
            .then()
            .statusCode(HttpStatus.OK.value());

  }


  @Test
  void shouldHaveStatus401WhenThereIsNoJwtInHeader() {
    RestAssured
            .given()
            .get(host + port + pathForUser)
            .then()
            .statusCode(HttpStatus.UNAUTHORIZED.value());
  }
}
