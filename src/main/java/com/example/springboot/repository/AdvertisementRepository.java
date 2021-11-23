package com.example.springboot.repository;

import com.example.springboot.interfaces.CategoryStatisticView;
import com.example.springboot.interfaces.SumAndCountView;
import com.example.springboot.model.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {


  @Query("select c.id as id, c.categoryName as categoryName, sum(ad.pricelnEuro) as pricePerCategory,count(ad.id) as numberOfAdvertisementsPerCategory  from Category c,Advertisements ad where ad.category.id=c.id  group by(c.id)")
  List<CategoryStatisticView> getNumberOfAdvertisementsPerCategoryForAdmin();

  @Query("select c.id as id, c.categoryName as categoryName, sum(ad.pricelnEuro) as pricePerCategory,count(ad.id) as numberOfAdvertisementsPerCategory  from Category c,Advertisements ad where ad.category.id=c.id and ad.user.id=?1 group by(c.id)")
  List<CategoryStatisticView> getNumberOfAdvertisementsPerCategoryForSeller(Long userId);

  @Query("select sum(ad.pricelnEuro) as pricePerCategory, count(ad.id) as numberOfAdvertisements from Advertisements ad")
  SumAndCountView getPrizeAndNumberAdvertisementsForAdmin();

  @Query("select sum(ad.pricelnEuro) as pricePerCategory,count(ad.id) as numberOfAdvertisements from Advertisements ad where ad.user.id=?1")
  SumAndCountView getPrizeAndNumberAdvertisementsForSeller(Long userId);

  @Query("select c.id as id, c.categoryName as categoryName, sum(ad.pricelnEuro) as pricePerCategory,count(ad.id) as numberOfAdvertisementsPerCategory from Category c,Advertisements ad where ad.category.id=c.id and c.id=?1 group by(c.id)")
  CategoryStatisticView getNumberOfAdvertisementsPerSpecificCategoryForAdmin(Long categoryId);

  @Query("select c.id as id, c.categoryName as categoryName, sum(ad.pricelnEuro) as pricePerCategory,count(ad.id) as numberOfAdvertisementsPerCategory from Category c,Advertisements ad where c.id=?2 and ad.category.id=c.id and ad.user.id=?1 group by(c.id)")
  CategoryStatisticView getNumberOfAdvertisementsPerSpecificCategoryForSeller(Long userId, Long categoryId);

}
