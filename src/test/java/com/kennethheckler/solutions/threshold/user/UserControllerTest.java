package com.kennethheckler.solutions.threshold.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.ArrayList;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    @Test
    void getSuccessful(@Autowired WebTestClient webClient) {
        ThresholdResponse response = webClient.get().uri("/successful_user?thresholdperc=30")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ThresholdResponse.class).isEqualTo(new ThresholdResponse(30, new ArrayList<>()))
                .returnResult().getResponseBody();

        System.out.println(response);
    }
}