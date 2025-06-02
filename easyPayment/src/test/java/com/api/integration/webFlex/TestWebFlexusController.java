package com.api.integration.webFlex;

import com.api.auth.domain.entity.Client;
import com.api.integration.factory.FactoryData;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestReporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class TestWebFlexusController {

    @Autowired
    private WebTestClient webTestClient;


    @Test
    public void calculator() throws Exception {

        // Arrange
        String num1 = "20";
        String num2 = "10";

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/calculadora/operaciones")
                        .queryParam("a", num1)
                        .queryParam("b", num2)
                        .build())
                
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.data").isEqualTo("30.0, 10.0");

    }
}
