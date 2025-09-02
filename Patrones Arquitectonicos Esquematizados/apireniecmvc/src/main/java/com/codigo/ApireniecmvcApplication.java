package com.codigo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ApireniecmvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApireniecmvcApplication.class, args);
	}

}
