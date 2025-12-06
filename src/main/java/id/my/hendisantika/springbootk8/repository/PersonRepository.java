package id.my.hendisantika.springbootk8.repository;

import id.my.hendisantika.springbootk8.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-k8
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 06/12/25
 * Time: 10.01
 * To change this template use File | Settings | File Templates.
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
}