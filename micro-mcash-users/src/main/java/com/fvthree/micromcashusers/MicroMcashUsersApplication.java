package com.fvthree.micromcashusers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MicroMcashUsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroMcashUsersApplication.class, args);
	}

}
