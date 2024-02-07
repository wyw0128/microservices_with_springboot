package com.in28minutes.microservices.apigateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {
    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
        return builder.routes()
                // creating a simple route function, if a request comes to /get, then we will redirect to this uri:
                // http://httpbin.org:80
                .route(p -> p.path("/get")
                        .filters(f -> f
                                .addRequestHeader("MyHeader", "MyURI")
                                .addRequestParameter("Param", "MyValue"))
                        .uri("http://httpbin.org:80"))
                // creating a customed routes for /currency-exchange
                // lb://currency-exchange will allow load balance
                .route(p -> p.path("/currency-exchange/**")
                        // This specifies the target URI (Uniform Resource Identifier) where the rewritten request should be forwarded.
                        // In this case, it uses the LoadBalancer (lb://) to route the request to the "currency-conversion" microservice.
                        // The actual microservice instance is determined by the load balancer.
                        .uri("lb://currency-exchange"))
                .route(p -> p.path("/currency-conversion/**")
                        .uri("lb://currency-conversion"))
                .route(p -> p.path("/currency-conversion-feign/**")
                        .uri("lb://currency-conversion"))
                // This defines a route for requests matching the specified path pattern ("/currency-conversion-new/**").
                // This means that any request with a path starting with "/currency-conversion-new/" will be handled by this route.
                .route(p -> p.path("/currency-conversion-new/**")
                        .filters(f -> f.rewritePath(
                                // rewritePath has two arguments: first one is what is the string to be replaced, and what should it be replaced with
                                // The (?<segment>.*) part captures any characters after "/currency-conversion-new/" and assigns them to a variable named "segment".
                                "/currency-conversion-new/(?<segment>.*)",
                                // This is the replacement pattern. It rewrites the path to "/currency-conversion-feign/" followed by the captured segment.
                                // The ${segment} is a placeholder referring to the value captured by the named group "segment" in the previous pattern.
                                "/currency-conversion-feign/${segment}"))
                        .uri("lb://currency-conversion"))
                .build();
    }
}
