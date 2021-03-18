package com.spring.reproducer.spring.test.sleuth.reproducer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.hamcrest.core.IsEqual.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class TracingResourceTest {

    @Autowired WebTestClient webTestClient;

    @Test
    public void shouldRetrieveSpanId() {
        String spanId = "a2fb4a1d1a96d312";
        webTestClient.get()
                .uri("/api/tracing/spanId")
                .header("X-B3-TraceId", "463ac35c9f6413ad48485a3953bb6124")
                .header("X-B3-SpanId", spanId)
                .header("X-B3-ParentSpanId", "0020000000000001")
                .header("X-B3-Flags", "1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(returnedSpanId -> returnedSpanId, equalTo(spanId));

    }

    @Test
    public void shouldRetrieveParentId() {
        String parentId = "0020000000000001";
        webTestClient.get()
                .uri("/api/tracing/parentId")
                .header("X-B3-TraceId", "463ac35c9f6413ad48485a3953bb6124")
                .header("X-B3-SpanId", "a2fb4a1d1a96d312")
                .header("X-B3-ParentSpanId", parentId)
                .header("X-B3-Flags", "1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(returnedParentId -> returnedParentId, equalTo(parentId));
    }

    @Test
    public void shouldRetrieveSpanIdWithCurrentTraceContext() {
        String spanId = "a2fb4a1d1a96d312";
        webTestClient.get()
                .uri("/api/tracing/currentSpanId")
                .header("X-B3-TraceId", "463ac35c9f6413ad48485a3953bb6124")
                .header("X-B3-SpanId", spanId)
                .header("X-B3-ParentSpanId", "0020000000000001")
                .header("X-B3-Flags", "1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(returnedSpanId -> returnedSpanId, equalTo(spanId));
    }

    @Test
    public void shouldRetrieveParentIdWithCurrentTraceContext() {
        String parentId = "0020000000000001";
        webTestClient.get()
                .uri("/api/tracing/currentParentId")
                .header("X-B3-TraceId", "463ac35c9f6413ad48485a3953bb6124")
                .header("X-B3-SpanId", "a2fb4a1d1a96d312")
                .header("X-B3-ParentSpanId", parentId)
                .header("X-B3-Flags", "1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(returnedParentId -> returnedParentId, equalTo(parentId));
    }
}
