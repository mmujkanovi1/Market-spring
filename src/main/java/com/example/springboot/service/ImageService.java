package com.example.springboot.service;

import com.example.springboot.model.Advertisement;
import com.example.springboot.model.Images;
import com.example.springboot.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ImageService {

  @Autowired
  private ImageRepository imageRepository;

  @Autowired
  private AdvertisementService advertisementService;
/*
  @Autowired
  public ImageService(ImageRepository imageRepository) {
    this.imageRepository = imageRepository;
  }

 */

  public void save(MultipartFile file) throws IOException {
    Images image = new Images();
    image.setName(StringUtils.cleanPath(file.getOriginalFilename()));
    image.setContentType(file.getContentType());
    image.setData(file.getBytes());
    image.setSize(file.getSize());

    imageRepository.save(image);
  }

  public void saveImages(List<MultipartFile> file, Advertisement advertisement) throws IOException {
    for (int i = 0; i < file.size(); i++) {
      Images image = new Images();
      image.setName(StringUtils.cleanPath(file.get(i).getOriginalFilename()));
      image.setContentType(file.get(i).getContentType());
      image.setData(file.get(i).getBytes());
      image.setSize(file.get(i).getSize());
      image.setAdvertisement(advertisement);
      imageRepository.save(image);
    }

  }

  public Optional<Images> getFile(Long id) {
    return imageRepository.findById(id);
  }

  public List<Images> getAllFiles() {
  // return advertisementService.getAdvertisementById(advertisementId).getImages();
   return imageRepository.findAll();
  }


}
