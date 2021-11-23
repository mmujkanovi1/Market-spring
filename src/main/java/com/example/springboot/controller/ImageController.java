package com.example.springboot.controller;

import com.example.springboot.model.Images;
import com.example.springboot.response.ImageReponse;
import com.example.springboot.service.AdvertisementService;
import com.example.springboot.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

@RestController
@Validated
@RequestMapping("/images")
@CrossOrigin
public class ImageController {

  private final ImageService imageService;

  @Autowired
  private AdvertisementService advertisementService;

  private List<Images> imagesFromDb;

  @Autowired
  public ImageController(ImageService imageService) {
    this.imageService = imageService;
    this.imagesFromDb = imageService.getAllFiles();
  }

  @PostMapping
  public ResponseEntity<String> upload(@RequestParam("file") List<MultipartFile> file, @RequestParam Long advertisementId) {
    try {


      imageService.saveImages(file, advertisementService.getAdvertisementById(advertisementId));

      return ResponseEntity.status(HttpStatus.OK)
              .body(String.format("File uploaded successfully: %s", file));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(String.format("Could not upload the file: %s!", file));
    }
  }
/*
  @GetMapping("{advertisementId}")
  public ResponseEntity<List<byte[]>> getImageForAdvertisement(@PathVariable Long advertisementId) {
    List<Image> images = advertisementService.getAdvertisementById(advertisementId).getImages();
    List<byte[]> imagesData = new ArrayList<>();
    for (int i = 0; i < images.size(); i++) {
      imagesData.add(images.get(i).getData());
    }

    return ResponseEntity.ok()
            .body(imagesData);
  }

 */


  @GetMapping("/advertisement/{advertisementId}")
  public List<ImageReponse> list(@PathVariable Long advertisementId) {
    System.out.println(imagesFromDb.size());
    List<Images> images = imageService.getAllFiles().stream().filter((image) -> {
      if (image.getAdvertisement() == null) {
        return false;
      }
      if (image.getAdvertisement().getId() == advertisementId) {
        return true;
      }
      return false;
    }).collect(Collectors.toList());


    return images
            .stream()
            .map(this::mapToFileResponse)
            .collect(Collectors.toList());
  }

  private ImageReponse mapToFileResponse(Images image) {
    System.out.println(image.getContentType());
    String downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/images/")
            .path(String.valueOf(image.getId()))
            .toUriString();
    ImageReponse imageReponse = new ImageReponse();
    imageReponse.setId(image.getId());
    imageReponse.setName(image.getName());
    imageReponse.setContentType(image.getContentType());
    imageReponse.setSize(image.getSize());
    imageReponse.setUrl(downloadURL);
    if (image.getAdvertisement() != null) {
      imageReponse.setAdvertisementId(image.getAdvertisement().getId());
    }
    return imageReponse;
  }

  @GetMapping("{id}")
  public ResponseEntity<byte[]> getFile(@PathVariable Long id) {
    Optional<Images> fileEntityOptional = imageService.getFile(id);

    if (!fileEntityOptional.isPresent()) {
      return ResponseEntity.notFound()
              .build();
    }


    Images image = fileEntityOptional.get();
    return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getName() + "\"")
            .contentType(MediaType.valueOf(image.getContentType()))
            .body(image.getData());
  }

  @GetMapping("")
  public List<byte[]> getAllImages() throws DataFormatException, IOException {
    List<byte[]> imagesByte = new ArrayList<>();
    List<Images> images = imageService.getAllFiles();
    for (int i = 0; i < images.size(); i++) {
      Inflater inflater = new Inflater();
      byte data[] = images.get(i).getData();
      inflater.setInput(data);
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
      byte[] buffer = new byte[1024];
      while (!inflater.finished()) {
        int count = inflater.inflate(buffer);
        outputStream.write(buffer, 0, count);
      }
      outputStream.close();
      imagesByte.add(outputStream.toByteArray());
    }
    return imagesByte;
  }


}



