package com.simba.crud.mongo.controller;

import com.simba.crud.mongo.collection.Person;
import com.simba.crud.mongo.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/persons")
@RequiredArgsConstructor
public class PersonController {
    private final PersonService personService;

    @PostMapping
    public String savePerson(@RequestBody Person person) {
        return personService.save(person);
    }

    @GetMapping("/age")
    public List<Person> getPersonByAgeBetween(@RequestParam Integer minAge,
                                              @RequestParam Integer maxAge) {
        return personService.getPersonByAgeBetween(minAge, maxAge);
    }

    @GetMapping("/search")
    public Page<Person> searchPerson(@RequestParam(required = false) String name,
                                     @RequestParam(required = false) Integer minAge,
                                     @RequestParam(required = false) Integer maxAge,
                                     @RequestParam(required = false) String city,
                                     @RequestParam(required = false, defaultValue = "0") Integer page,
                                     @RequestParam(required = false, defaultValue = "5") Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return personService.search(name, minAge, maxAge, city, pageable);
    }

    @GetMapping("/oldest-person")
    public List<Document> getOldestPerson() {
        return personService.getOldestPersonByCity();
    }

    @GetMapping("/population-by-city")
    public List<Document> getPopulationByCity() {
        return personService.getPopulationByCity();
    }
}
