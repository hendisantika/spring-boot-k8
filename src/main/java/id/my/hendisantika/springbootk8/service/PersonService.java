package id.my.hendisantika.springbootk8.service;

import id.my.hendisantika.springbootk8.entity.Person;
import id.my.hendisantika.springbootk8.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
 * Time: 10.02
 * To change this template use File | Settings | File Templates.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PersonService {

    private final PersonRepository personRepository;

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Optional<Person> findById(Integer id) {
        return personRepository.findById(id);
    }

    @Transactional
    public Person save(Person person) {
        return personRepository.save(person);
    }
}
