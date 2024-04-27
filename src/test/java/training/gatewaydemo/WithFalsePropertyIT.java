package training.gatewaydemo;

import org.apache.groovy.util.Maps;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.CacheControl;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class WithFalsePropertyIT {

    @Test
    void testWithFalseProperty() {
        var exception = assertThrows(IllegalArgumentException.class, () -> {
            var application = new SpringApplication(GatewayDemoApplication.class);
            application.setDefaultProperties(Maps.of("spring.cloud.gateway.filter.local-response-cache.enabled", "false"));

        // spring.cloud.gateway.filter.local-response-cache.enabled=false
            application.run();
        });
        assertEquals("Unable to find GatewayFilterFactory with name LocalResponseCache", exception.getMessage());
    }

}
