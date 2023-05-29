package com.simba.crud.mongo.collection;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "person")
@Builder
public class Person {
    private String personId;

    private String firstName;

    private String lastName;

    private Integer age;

    private List<String> hobbies;

    private List<Address> addresses;
}
