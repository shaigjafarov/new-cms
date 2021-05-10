package com.guavapay.cms.gatewayservice;

import com.guavapay.cms.gatewayservice.dao.entity.UserEntity;
import com.guavapay.cms.gatewayservice.dao.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableDiscoveryClient
@SpringBootApplication
public class GatewayServiceApplication implements CommandLineRunner {
@Autowired
	PasswordEncoder encoder;

@Autowired
	UserRepository userRepository;
	public static void main(String[] args) {
		SpringApplication.run(GatewayServiceApplication.class, args);
	}

	@Bean
	public ServerCodecConfigurer serverCodecConfigurer() {
		return ServerCodecConfigurer.create();
	}

	@Override
	public void run(String... args) throws Exception {
		UserEntity userEntity = new UserEntity();
		userEntity.setId(1l);
		userEntity.setEmail("iku@gmail.com");
		userEntity.setPassword(encoder.encode("password"));
		userEntity.setVerified(true);
		userRepository.save(userEntity);

	}
}
