package training.gatewaydemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.CacheControl;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
@TestPropertySource(properties = {"spring.cloud.gateway.filter.local-response-cache.enabled=true",
        "hosted.port=${wiremock.server.port}"})
class WithTruePropertyIT {

    @Autowired
    WebTestClient webClient;

    @Test
    void testWithoutProperty() {
        stubFor(get(urlPathEqualTo("/hosted/api/hello"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("hello")
                ));

        webClient.get().uri("/api/hello")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().exists("Cache-Control");

        stubFor(get(urlPathEqualTo("/hosted/api/hello/john"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("hello john")
                ));

        webClient.get().uri("/api/hello/john")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().exists("Cache-Control");
    }
}
