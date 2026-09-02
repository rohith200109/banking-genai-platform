package com.bank.ai.gateway.filter;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class CorrelationIdGlobalFilter implements GlobalFilter, Ordered {

    private static final Logger log =
            LoggerFactory.getLogger(CorrelationIdGlobalFilter.class);

    private static final String CORRELATION_ID = "X-Correlation-ID";

    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange,
            org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {

        long startTime = System.currentTimeMillis();

        String correlationId =
                exchange.getRequest()
                        .getHeaders()
                        .getFirst(CORRELATION_ID);

        if (correlationId == null || correlationId.isBlank()) {
            correlationId = UUID.randomUUID().toString();
        }

        final String finalCorrelationId = correlationId;

        ServerHttpRequest request =
                exchange.getRequest()
                        .mutate()
                        .header(CORRELATION_ID, finalCorrelationId)
                        .build();

        ServerWebExchange mutatedExchange =
                exchange.mutate()
                        .request(request)
                        .build();

        mutatedExchange.getResponse()
        .getHeaders()
        .add(CORRELATION_ID, finalCorrelationId);

        log.info(
                "[{}] Incoming Request: {} {}",
                finalCorrelationId,
                request.getMethod(),
                request.getURI().getPath()
        );

        return chain.filter(mutatedExchange)
                .doFinally(signal -> {

                    long duration =
                            System.currentTimeMillis() - startTime;

                    int statusCode =
                            mutatedExchange.getResponse()
                                    .getStatusCode() != null
                                    ? mutatedExchange.getResponse()
                                        .getStatusCode()
                                        .value()
                                    : 0;

                    log.info(
                            "[{}] Response: {} | Status: {} | Duration: {} ms",
                            finalCorrelationId,
                            request.getURI().getPath(),
                            statusCode,
                            duration
                    );
                });
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}