package org.example;

import datadog.trace.api.Trace;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@RestController
public class DemoController {

    @GetMapping("/test")
    public Mono<ResponseEntity<String>> getter() {
        return getJust();
    }

    @Trace(operationName = "get-test", resourceName = "DemoController")
    public Mono<ResponseEntity<String>> getJust() {
        return Mono.just(ResponseEntity.ok("Dummy response")).delayElement(Duration.of(1, ChronoUnit.SECONDS));
    }
}