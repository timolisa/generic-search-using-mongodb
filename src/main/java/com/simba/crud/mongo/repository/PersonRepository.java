package com.simba.crud.mongo.repository;

import com.simba.crud.mongo.collection.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PersonRepository extends MongoRepository<Person, String> {
    List<Person> findByAgeBetween(Integer min, Integer max);

    @Query(value = "{ 'age' : { $gt : ?0, $lt : ?1 } }")
    List<Person> getPersonByAgeBetween(Integer minAge, Integer maxAge);
}
