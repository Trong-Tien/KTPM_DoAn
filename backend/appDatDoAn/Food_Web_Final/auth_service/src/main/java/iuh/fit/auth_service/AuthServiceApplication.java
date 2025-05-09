package iuh.fit.auth_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(scanBasePackages = "iuh.fit.auth_service")
@EnableFeignClients(basePackages = "iuh.fit.auth_service.client")
public class AuthServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}

