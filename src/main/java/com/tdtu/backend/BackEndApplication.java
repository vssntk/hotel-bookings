package com.tdtu.backend;

import com.tdtu.backend.model.Role;
import com.tdtu.backend.model.User;
import com.tdtu.backend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@SpringBootApplication
public class BackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackEndApplication.class, args);
	}
	@Bean
	CommandLineRunner createDefaultAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			String adminUsername = "admin";
			String adminEmail = "admin@gmail.com";
			String adminPassword = passwordEncoder.encode("admin");
			if (userRepository.findByUsername(adminUsername).isEmpty()) {
				User adminUser = new User();
				adminUser.setUsername(adminUsername);
				adminUser.setPassword(adminPassword);
				adminUser.setEmail(adminEmail);
				adminUser.setActive(true);
				adminUser.setRoles(Collections.singleton(Role.ADMIN));
				userRepository.save(adminUser);
			}
		};
	}
}
