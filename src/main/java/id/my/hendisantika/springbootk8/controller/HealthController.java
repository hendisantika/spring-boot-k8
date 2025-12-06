package id.my.hendisantika.springbootk8.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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
@RequestMapping("/api/health")
public class HealthController {

    @GetMapping("check")
    public ResponseEntity<Map<String, String>> check() {
        return new ResponseEntity<>(Map.of("status", "OK"), HttpStatus.OK);
    }
}
