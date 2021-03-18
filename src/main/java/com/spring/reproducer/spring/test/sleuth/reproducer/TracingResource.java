package com.spring.reproducer.spring.test.sleuth.reproducer;

import org.springframework.cloud.sleuth.CurrentTraceContext;
import org.springframework.cloud.sleuth.TraceContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.util.context.ContextView;


@RestController
@RequestMapping("/api/tracing")
public class TracingResource {

    private static final Class<TraceContext> KEY = TraceContext.class;
    private static final Class<CurrentTraceContext> CURRENT_KEY = CurrentTraceContext.class;

    @GetMapping("spanId")
    public Mono<String> spanId() {
        return Mono.deferContextual(view -> traceContext(view))
                .map( c -> c.spanId() );

    }

    @GetMapping("parentId")
    public Mono<String> parentId() {
        return Mono.deferContextual(view -> traceContext(view))
            .map(c -> c.parentId());
    }

    private Mono<TraceContext> traceContext(ContextView contextView){
        return Mono.justOrEmpty(contextView.get(KEY));
    }

    @GetMapping("currentSpanId")
    public Mono<String> spanIdForCurrentTraceContext() {
        return Mono.deferContextual(view -> currentTraceContext(view))
            .map(c -> c.context().spanId());

    }

    @GetMapping("currentParentId")
    public Mono<String> parentIdForCurrentTraceContext() {
        return Mono.deferContextual(view -> currentTraceContext(view))
            .map(c -> c.context().parentId());

    }

    private Mono<CurrentTraceContext> currentTraceContext(ContextView contextView){
        return Mono.justOrEmpty(contextView.get(CURRENT_KEY));
    }
}
