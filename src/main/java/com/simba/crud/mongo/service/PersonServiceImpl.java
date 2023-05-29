package com.simba.crud.mongo.service;

import com.simba.crud.mongo.collection.Person;
import com.simba.crud.mongo.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;
    private final MongoTemplate mongoTemplate;

    @Override
    public String save(Person person) {
        return personRepository.save(person).getPersonId();
    }

    @Override
    public List<Person> getPersonByAgeBetween(Integer minAge, Integer maxAge) {
        return personRepository.getPersonByAgeBetween(minAge, maxAge);
    }

    @Override
    public Page<Person> search(String name, Integer minAge, Integer maxAge, String city, Pageable pageable) {
        Query query = new Query().with(pageable);
        List<Criteria> criteriaList = new ArrayList<>();

        if (name != null) {
            criteriaList.add(Criteria.where("firstName").regex(name, "i"));
        }

        if (minAge != null && maxAge != null) {
            criteriaList.add(Criteria.where("age").gte(minAge).lte(maxAge));
        }

        if (city != null && !city.isEmpty()) {
            criteriaList.add(Criteria.where("addresses.city").is(city));
        }

        if (!criteriaList.isEmpty()) {
            query.addCriteria(new Criteria()
                    .andOperator(criteriaList.toArray(new Criteria[0])));
        }

        return PageableExecutionUtils.getPage(
                mongoTemplate.find(query, Person.class), pageable, () -> mongoTemplate.count(query.skip(0).limit(0), Person.class)

        );
    }

    @Override
    public List<Document> getOldestPersonByCity() {
        // Aggregation is the process of performing data processing operations on multiple documents within a collection.
        // It allows you to perform advanced data analysis and transformation operations, such as grouping, filtering, sorting,
        // and calculating aggregate values.
        UnwindOperation unwindOperation = Aggregation.unwind("addresses");

        SortOperation sortOperation = Aggregation.sort(Sort.Direction.DESC, "age");

        GroupOperation groupOperation = Aggregation.group("addresses.city").first(Aggregation.ROOT).as("oldestPerson");

        Aggregation aggregation = Aggregation.newAggregation(unwindOperation, sortOperation, groupOperation);

        return mongoTemplate.aggregate(aggregation, Person.class, Document.class).getMappedResults();
    }

    @Override
    public List<Document> getPopulationByCity() {
        UnwindOperation unwindOperation = Aggregation.unwind("addresses");

        GroupOperation groupOperation = Aggregation
                .group("addresses.city").count().as("popCount");

        SortOperation sortOperation = Aggregation.sort(Sort.Direction.DESC, "popCount");

        // helps us define which field we need in the result rather than return the object as a whole.
        ProjectionOperation projectionOperation = Aggregation.project()
                .andExpression("_id").as("city")
                .andExpression("popCount").as("count")
                .andExclude("_id");

        Aggregation aggregation = Aggregation.newAggregation(unwindOperation, groupOperation, sortOperation, projectionOperation);
        return mongoTemplate.aggregate(aggregation, Person.class, Document.class).getMappedResults();
    }
}
