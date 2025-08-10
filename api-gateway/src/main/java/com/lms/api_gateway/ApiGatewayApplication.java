package com.lms.api_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(
		exclude = {
				ReactiveSecurityAutoConfiguration.class, // Disables all core security setup
				ReactiveUserDetailsServiceAutoConfiguration.class // Disables the generated password
		}
)
@ComponentScan(basePackages = {"com.lms.api_gateway"})
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

}
