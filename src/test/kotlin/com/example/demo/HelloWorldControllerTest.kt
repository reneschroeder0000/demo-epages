package com.example.demo

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestDocs
@AutoConfigureWebTestClient
class HelloWorldControllerTest(
    @Autowired private val webTestClient: WebTestClient
) {
    @Test
    fun `should return Hello World`() {
        // Given
        val response = webTestClient.get().uri("/hello").exchange()
        val defaultGreeting = Greeting()

        // When
        response.expectStatus().isOk
        response.expectBody<Greeting>()
            .isEqualTo(defaultGreeting)

        response.expectBody().consumeWith(
            document(
                "hello-world",
                responseFields(
                    fieldWithPath("greeting").description("The greeting"),
                    fieldWithPath("who").description("Who is greeted")
                )
            )
        )

        // Then
    }
}