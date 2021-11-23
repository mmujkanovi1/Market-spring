package com.example.springboot.repository;

import com.example.springboot.model.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ImageRepository extends JpaRepository<Images, Long> {

  @Query(value = "select * from images where advertisement_id=?1",nativeQuery = true)
  List<Images> getImagesForAdvertisement(Long advertisementId);


}
