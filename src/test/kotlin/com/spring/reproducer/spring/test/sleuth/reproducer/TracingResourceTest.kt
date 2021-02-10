package com.spring.reproducer.spring.test.sleuth.reproducer

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody

@SpringBootTest
@AutoConfigureWebTestClient
internal class TracingResourceTest(@Autowired private val webTestClient: WebTestClient) {

    @Test
    fun `should retrieve span id`() {
        val spanId = "a2fb4a1d1a96d312"
        webTestClient.get()
                .uri("/api/tracing/spanId")
                .header("X-B3-TraceId", "463ac35c9f6413ad48485a3953bb6124")
                .header("X-B3-SpanId", spanId)
                .header("X-B3-ParentSpanId", "0020000000000001")
                .header("X-B3-Flags", "1")
                .exchange()
                .expectStatus().isOk
                .expectBody<String>()
                .isEqualTo(spanId)
    }

    @Test
    fun `should retrieve parent id`() {
        val parentId = "0020000000000001"
        webTestClient.get()
            .uri("/api/tracing/parentId")
            .header("X-B3-TraceId", "463ac35c9f6413ad48485a3953bb6124")
            .header("X-B3-SpanId", "a2fb4a1d1a96d312")
            .header("X-B3-ParentSpanId", parentId)
            .header("X-B3-Flags", "1")
            .exchange()
            .expectStatus().isOk
            .expectBody<String>()
            .isEqualTo(parentId)
    }

    @Test
    fun `should retrieve span id with CurrentTraceContext`() {
        val spanId = "a2fb4a1d1a96d312"
        webTestClient.get()
            .uri("/api/tracing/currentSpanId")
            .header("X-B3-TraceId", "463ac35c9f6413ad48485a3953bb6124")
            .header("X-B3-SpanId", spanId)
            .header("X-B3-ParentSpanId", "0020000000000001")
            .header("X-B3-Flags", "1")
            .exchange()
            .expectStatus().isOk
            .expectBody<String>()
            .isEqualTo(spanId)
    }

    @Test
    fun `should retrieve parent id with CurrentTraceContext`() {
        val parentId = "0020000000000001"
        webTestClient.get()
            .uri("/api/tracing/currentParentId")
            .header("X-B3-TraceId", "463ac35c9f6413ad48485a3953bb6124")
            .header("X-B3-SpanId", "a2fb4a1d1a96d312")
            .header("X-B3-ParentSpanId", parentId)
            .header("X-B3-Flags", "1")
            .exchange()
            .expectStatus().isOk
            .expectBody<String>()
            .isEqualTo(parentId)
    }
}
