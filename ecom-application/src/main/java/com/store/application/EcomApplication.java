package com.store.application;

import com.store.application.model.UserLogin;
import com.store.application.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
public class EcomApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcomApplication.class, args);

	}

	@Bean
	CommandLineRunner init(UserRepository repo, PasswordEncoder encoder) {
		return args -> {
			if (repo.findByUsername("vishal").isEmpty()) {
				UserLogin admin = new UserLogin();
				admin.setUsername("vishal");
				admin.setPassword(encoder.encode("vishal123"));
				admin.setRole("ADMIN");
				admin.setEmail("vickychowdary11@gmail.com");
				repo.save(admin);
			}
		};
	}

}
