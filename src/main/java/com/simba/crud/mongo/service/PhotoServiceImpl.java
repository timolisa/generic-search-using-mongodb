package com.simba.crud.mongo.service;

import com.simba.crud.mongo.collection.Photo;
import com.simba.crud.mongo.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class PhotoServiceImpl implements PhotoService {
    private final PhotoRepository photoRepository;

    @Override
    public String savePhoto(String originalFilename, MultipartFile image) throws IOException {
        Photo photo = Photo.builder()
                .title(originalFilename)
                .photo(new Binary(BsonBinarySubType.BINARY, image.getBytes()))
                .build();

        return photoRepository.save(photo).getPhotoId();
    }

    @Override
    public Photo getPhotoById(String id) {
        return photoRepository.findById(id).get();
    }
}
