package com.jorge.jwt_user_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {
				"com.jorge.jwt_user_service",
				"com.jorge.common.security"
})
public class JwtUserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtUserServiceApplication.class, args);
	}

}
