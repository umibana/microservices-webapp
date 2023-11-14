package com.example.installmentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class InstallmentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InstallmentServiceApplication.class, args);
	}

}
