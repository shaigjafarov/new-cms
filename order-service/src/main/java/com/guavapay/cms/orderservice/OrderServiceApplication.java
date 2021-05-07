package com.guavapay.cms.orderservice;

import com.guavapay.cms.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class OrderServiceApplication implements CommandLineRunner {
@Autowired
OrderService orderService;
	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		orderService.findCardDTOById()
	}
}
