package com.spring.reproducer.spring.test.sleuth.reproducer

import brave.Span
import brave.propagation.TraceContext
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import reactor.util.context.Context

@RestController
@RequestMapping("/api/tracing")
class TracingResource {

    @GetMapping
    fun spanId(): Mono<String> = Mono.subscriberContext().traceContext().map { it.spanIdString() }

    private fun Mono<Context>.traceContext(): Mono<TraceContext> {
        return filter { it.hasKey(Span::class.java) }
                .map { it[Span::class.java].context() }
    }
}