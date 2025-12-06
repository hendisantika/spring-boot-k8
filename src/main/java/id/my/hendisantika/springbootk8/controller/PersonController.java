package id.my.hendisantika.springbootk8.controller;

import id.my.hendisantika.springbootk8.entity.Person;
import id.my.hendisantika.springbootk8.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-k8
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 06/12/25
 * Time: 10.03
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/persons")
public class PersonController {

    private final PersonService personService;

    @GetMapping
    public ResponseEntity<List<Person>> all() {
        List<Person> people = personService.findAll();
        return new ResponseEntity<>(people, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Person> byId(@PathVariable Integer id) {
        Optional<Person> optionalPerson = personService.findById(id);
        return optionalPerson
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Person> create(@RequestBody Person person) {
        if (person.getId() != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Person createdPerson = personService.save(person);
        return new ResponseEntity<>(createdPerson, HttpStatus.CREATED);
    }
}
