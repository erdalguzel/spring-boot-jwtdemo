package dev.spring.jwtdemo;

import dev.spring.jwtdemo.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class JwtdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtdemoApplication.class, args);
	}

}
