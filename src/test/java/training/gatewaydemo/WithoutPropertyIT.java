package training.gatewaydemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
@TestPropertySource(properties = {
        "hosted.port=${wiremock.server.port}"})
class WithoutPropertyIT {

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
                .expectHeader().doesNotExist("Cache-Control");
    }
}
