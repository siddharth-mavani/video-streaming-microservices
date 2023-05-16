package io.john.amiscaray;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class APIGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(APIGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("content-service", r -> r.path("/api/content/**")
                        .filters(f -> f.rewritePath("/api/content/(?<segment>.*)", "/${segment}"))
                                // .stripPrefix(1))
                        .uri("lb://content-service"))
                .route("subscription-service", r -> r.path("/api/subscription/**")
                        .filters(f -> f.rewritePath("/api/subscription/(?<segment>.*)", "/${segment}"))
                                // .stripPrefix(1))
                        .uri("lb://subscription-service"))
                .route("user-service", r -> r.path("/api/user/**")
                        .filters(f -> f.rewritePath("/api/user/(?<segment>.*)", "/${segment}"))
                                // .stripPrefix(1))
                        .uri("lb://user-service"))
                .build();
    }
}
