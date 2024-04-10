package com.bazar.detallesventasservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DetallesVentasServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DetallesVentasServiceApplication.class, args);
	}

}
