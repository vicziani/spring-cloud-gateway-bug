package training.gatewaydemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class HelloController {

    private static final Logger log = LoggerFactory.getLogger(HelloController.class);

    @GetMapping("hosted/api/hello")
    public Mono<String> sayHello() {
        log.info("Call hello");
        return Mono.just("hello");
    }

    @GetMapping("hosted/api/hello/{name}")
    public Mono<String> sayHello(@PathVariable String name) {
        log.info("Call hello with name {}", name);
        return Mono.just("hello " + name);
    }
}
