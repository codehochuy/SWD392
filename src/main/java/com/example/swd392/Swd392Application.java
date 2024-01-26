package com.example.swd392;

import com.example.swd392.auth.AuthenticationRequest;
import com.example.swd392.auth.AuthenticationService;
import com.example.swd392.auth.RegisterRequest;
import com.example.swd392.enums.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.example.swd392.enums.Role.ADMIN;
import static com.example.swd392.enums.Role.MANAGER;

@SpringBootApplication
@EnableWebSecurity
@EnableJpaRepositories
public class Swd392Application {

	public static void main(String[] args) {
		SpringApplication.run(Swd392Application.class, args);
	}

	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	public CommandLineRunner commandLineRunner(
			AuthenticationService service
	){
		return args -> {
			var admin= RegisterRequest.builder()
					.name("Admin")
					.email("Admin@gmail.com")
					.password("123")
					.role(ADMIN)
					.build();
			System.out.println("Admin token :"+ service.register(admin).getAccessToken());
			var manager = RegisterRequest.builder()
					.name("Manager")
					.email("Manager@gmail.com")
					.password("123")
					.role(MANAGER)
					.build();
			System.out.println("Manager token :"+ service.register(manager).getAccessToken());
		};


	}


}
