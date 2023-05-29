package com.simba.crud.mongo.collection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Builder
@AllArgsConstructor
public class Photo {
    @Id
    private String photoId;

    private String title;

    private Binary photo;
}
