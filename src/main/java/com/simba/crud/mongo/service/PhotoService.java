package com.simba.crud.mongo.service;

import com.simba.crud.mongo.collection.Photo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PhotoService {
    String savePhoto(String fileName, MultipartFile image) throws IOException;

    Photo getPhotoById(String id);
}
