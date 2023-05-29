package com.simba.crud.mongo.controller;

import com.simba.crud.mongo.collection.Photo;
import com.simba.crud.mongo.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/photo")
public class PhotoController {
    private final PhotoService photoService;

    @PostMapping
    public String savePhoto(@RequestParam("image") MultipartFile image) throws IOException {
        return photoService.savePhoto(image.getOriginalFilename(), image);
    }

    @GetMapping
    public ResponseEntity<Resource> downloadPhoto(String id) {
        Photo photo = photoService.getPhotoById(id);
        Resource resource = new ByteArrayResource(photo.getPhoto().getData());
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment: filename=\"" + photo.getTitle() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);

    }
}
