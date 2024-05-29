package com.bazar.configsv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class ConfigSvApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigSvApplication.class, args);
	}

}
