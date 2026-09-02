package com.bank.ai.gateway;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Mono;

@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		properties = {
				"eureka.client.enabled=false",
				"spring.cloud.discovery.enabled=false",
				"spring.cloud.service-registry.auto-registration.enabled=false",
				"spring.cloud.gateway.server.webflux.discovery.locator.enabled=false",
				"spring.cloud.gateway.server.webflux.routes[0].id=account-service",
				"spring.cloud.gateway.server.webflux.routes[0].uri=http://127.0.0.1:1",
				"spring.cloud.gateway.server.webflux.routes[0].predicates[0]=Path=/api/accounts/**",
				"spring.cloud.gateway.server.webflux.routes[0].filters[0].name=RequestRateLimiter",
				"spring.cloud.gateway.server.webflux.routes[0].filters[0].args.key-resolver=#{@testKeyResolver}",
				"spring.cloud.gateway.server.webflux.routes[0].filters[0].args.rate-limiter=#{@denyAllRateLimiter}",
				"spring.cloud.gateway.server.webflux.routes[0].filters[0].args.status-code=TOO_MANY_REQUESTS"
		})
class GatewayApplicationTests {

	@LocalServerPort
	private int port;

	@Test
	void contextLoads() {
	}

	@Test
	void accountRouteReturns429TooManyRequestsWhenRateLimitIsExceeded() {
		WebTestClient.bindToServer()
				.baseUrl("http://localhost:" + port)
				.build()
				.get()
				.uri("/api/accounts/123")
				.exchange()
				.expectStatus().isEqualTo(HttpStatus.TOO_MANY_REQUESTS)
				.expectHeader().valueEquals(HttpHeaders.RETRY_AFTER, "60")
				.expectHeader().valueEquals("X-RateLimit-Remaining", "0");
	}

	@TestConfiguration
	static class RateLimitTestConfiguration {

		@Bean
		@Primary
		KeyResolver testKeyResolver() {
			return exchange -> Mono.just("test-client");
		}

		@Bean
		@Primary
		RateLimiter<Object> denyAllRateLimiter() {
			return new RateLimiter<>() {

				@Override
				public Mono<Response> isAllowed(String routeId, String id) {
					return Mono.just(new Response(false, Map.of(
							HttpHeaders.RETRY_AFTER, "60",
							"X-RateLimit-Remaining", "0"
					)));
				}

				@Override
				public Map<String, Object> getConfig() {
					return Map.of();
				}

				@Override
				public Class<Object> getConfigClass() {
					return Object.class;
				}

				@Override
				public Object newConfig() {
					return new Object();
				}
			};
		}
	}

}
