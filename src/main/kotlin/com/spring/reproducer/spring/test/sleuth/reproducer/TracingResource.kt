package com.spring.reproducer.spring.test.sleuth.reproducer

import org.springframework.cloud.sleuth.CurrentTraceContext
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
        val CURRENT_KEY = CurrentTraceContext::class.java
    }

    @GetMapping("spanId")
    fun spanId(): Mono<String> {
        return Mono.deferContextual { view -> view.traceContext() }
                .map { it.spanId() }

    }

    @GetMapping("parentId")
    fun parentId(): Mono<String> {
        return Mono.deferContextual { view -> view.traceContext() }
            .map { it.parentId() }

    }

    private fun ContextView.traceContext() = get(KEY).toMono()

    @GetMapping("currentSpanId")
    fun spanIdForCurrentTraceContext(): Mono<String> {
        return Mono.deferContextual { view -> view.currentTraceContext() }
            .map { it.context()?.spanId() }

    }

    @GetMapping("currentParentId")
    fun parentIdForCurrentTraceContext(): Mono<String> {
        return Mono.deferContextual { view -> view.currentTraceContext() }
            .map { it.context()?.parentId() }

    }

    private fun ContextView.currentTraceContext() = get(CURRENT_KEY).toMono()
}