package com.fvthree.micromcashgatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
@Import(WebConfig.class)
@PropertySource("classpath:application.yml")
public class MicroMcashGatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroMcashGatewayServiceApplication.class, args);
	}

}
