package com.example.springboot.mappers;

import com.example.springboot.model.Advertisement;
import com.example.springboot.model.AppConfig;
import com.example.springboot.model.Category;
import com.example.springboot.model.Role;
import com.example.springboot.model.User;
import com.example.springboot.request.CreateAdvertisementRequest;
import com.example.springboot.request.CreateCategoryRequest;
import com.example.springboot.request.CreateUserRequest;
import com.example.springboot.request.UpdateAdvertisementRequest;
import com.example.springboot.request.UpdateAppConfigRequest;
import com.example.springboot.request.UpdateCategoryRequest;
import com.example.springboot.request.UpdateUserRequest;
import com.example.springboot.response.CreateAdvertisementResponse;
import com.example.springboot.response.CreateCategoryResponse;
import com.example.springboot.response.CreateUserResponse;
import com.example.springboot.response.DeleteAdvertisementResponse;
import com.example.springboot.response.DeleteCategoryResponse;
import com.example.springboot.response.DeleteUserResponse;
import com.example.springboot.response.GetAdvertisementResponse;
import com.example.springboot.response.GetAdvertisementWithUsdResponse;
import com.example.springboot.response.GetAppConfigResponse;
import com.example.springboot.response.GetCategoryResponse;
import com.example.springboot.response.GetUserResponse;
import com.example.springboot.response.UpdateAdvertisementResponse;
import com.example.springboot.response.UpdateAppConfigResponse;
import com.example.springboot.response.UpdateCategoryResponse;
import com.example.springboot.response.UpdateUserResponse;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;
import java.util.List;


@Mapper(
        componentModel = "spring"
)
public interface MapStructMapper {

  @Mapping(source = "oldUser.username", target = "username")
  @Mapping(source = "oldUser.password", target = "password")
  @Mapping(source = "role", target = "userRole")
  @Mapping(target = "id", ignore = true)
  User createUserRequest(CreateUserRequest oldUser, Role role);

  @Mapping(source = "oldUser.username", target = "username")
  @Mapping(source = "oldUser.password", target = "password")
  @Mapping(source = "role", target = "userRole")
  @Mapping(source = "oldUser.id", target = "id")
  User updateUserRequest(UpdateUserRequest oldUser, Role role);

  CreateUserResponse createUserResponse(User user);

  DeleteUserResponse deleteUserResponse(User user);

  @Named(value = "useMe")
  @Mapping(source = "user.userRole.id", target = "roleId")
  GetUserResponse getUserResponse(User user);

  UpdateUserResponse updateUserResponse(User user);

  @Mapping(source = "user", target = "user")
  @Mapping(source = "category", target = "category")
  @Mapping(target = "id", ignore = true)
  @Mapping(source = "prize", target = "pricelnEuro")
  Advertisement createAdvertisementRequest(CreateAdvertisementRequest createAdvertisementRequest, BigDecimal prize, User user, Category category);

  @Mapping(source = "user", target = "user")
  @Mapping(source = "category", target = "category")
  @Mapping(source = "oldAdvertisement.id", target = "id")
  Advertisement updateAdvertisementRequest(UpdateAdvertisementRequest oldAdvertisement, User user, Category category);

  CreateAdvertisementResponse createAdvertisementResponse(Advertisement advertisement);

  DeleteAdvertisementResponse deleteAdvertisementResponse(Advertisement advertisement);

  @Named(value = "useGetAdvertisementResponse")
  GetAdvertisementResponse getAdvertisementResponse(Advertisement advertisement);

  @Mapping(source = "prizelnUsd", target = "prizelnUsd")
  @Mapping(source = "categoryId", target = "categoryId")
  GetAdvertisementWithUsdResponse getAdvertisementWithUsdResponse(Advertisement advertisement, BigDecimal prizelnUsd, Long categoryId);

  @IterableMapping(qualifiedByName = "useGetAdvertisementResponse")
  List<GetAdvertisementResponse> getListOfAdvertisemens(List<Advertisement> advertisements);

  UpdateAdvertisementResponse updateAdvertisementResponse(Advertisement advertisement);

  @Mapping(source = "user", target = "user")
  @Mapping(target = "id", ignore = true)
  Category createCategoryRequest(CreateCategoryRequest category, User user);

  @Mapping(source = "user", target = "user")
  @Mapping(source = "category.id", target = "id")
  Category updateCategoryRequest(UpdateCategoryRequest category, User user);

  CreateCategoryResponse createCategoryResponse(Category category);

  UpdateCategoryResponse updateCategoryResponse(Category category);

  DeleteCategoryResponse deleteCategoryResponse(Category category);

  @Named(value = "useGetCategoryResponse")
  GetCategoryResponse getCategoryResponse(Category category);

  @IterableMapping(qualifiedByName = "useGetCategoryResponse")
  List<GetCategoryResponse> getListOfCategories(List<Category> category);

  GetAppConfigResponse getAppConfigResponse(AppConfig appConfig);

  AppConfig updateAppConfigRequest(UpdateAppConfigRequest updateAppConfigRequest);

  UpdateAppConfigResponse updateAppConfigResponse(AppConfig appConfig);

}

