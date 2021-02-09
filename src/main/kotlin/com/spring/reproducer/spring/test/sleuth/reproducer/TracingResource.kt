package com.spring.reproducer.spring.test.sleuth.reproducer

import org.springframework.cloud.sleuth.TraceContext
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import reactor.util.context.ContextView

@RestController
@RequestMapping("/api/tracing")
class TracingResource {

    companion object {
        val KEY = TraceContext::class.java
    }

    @GetMapping
    fun spanId(): Mono<String> {
        return Mono.deferContextual { view -> view.traceContext() }
                .map { it.spanId() }

    }

    private fun ContextView.traceContext() = get(KEY).toMono()
}