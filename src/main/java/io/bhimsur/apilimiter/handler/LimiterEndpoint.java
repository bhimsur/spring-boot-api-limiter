package io.bhimsur.apilimiter.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hello")
public class LimiterEndpoint {
    @GetMapping(path = "/{id}")
    public ResponseEntity<String> hello(@PathVariable("id") int id) {
        return ResponseEntity.ok("Hello " + id);
    }
}
