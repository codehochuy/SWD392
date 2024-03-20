package com.example.swd392;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.swd392.auth.AuthenticationRequest;
import com.example.swd392.auth.AuthenticationService;
import com.example.swd392.auth.RegisterRequest;
import com.example.swd392.enums.Role;
import com.example.swd392.model.Artwork;
import com.example.swd392.model.User;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.aspectj.apache.bcel.util.ClassPath;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.time.LocalDateTime;

import static com.example.swd392.enums.Role.*;


@SpringBootApplication
@EnableWebSecurity
@EnableJpaRepositories
public class Swd392Application {

    public static void main(String[] args) {
        SpringApplication.run(Swd392Application.class, args);
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Cloudinary cloudinary() {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dpxs39hkb",
                "api_key", "679575712278322",
                "api_secret", "KJfkzpiXRnmkPCeRwH6TUAmFGks"));
        return cloudinary;
    }

    @Bean
    public CommandLineRunner commandLineRunner(
            AuthenticationService service
    ) {
        return args -> {
            var super_admin = RegisterRequest.builder()
                    .name("Super Admin")
                    .email("Superadmin@gmail.com")
//					.avatar("admin.jpg")
                    .password("123")
                    .phone("0326514968")
                    .status(true)
                    .account_balance(0.0)
                    .role(SUPER_ADMIN)
                    .build();
            System.out.println("Super_Admin token :" + service.register(super_admin).getAccessToken());

            var admin = RegisterRequest.builder()
                    .name("Admin")
                    .email("Admin@gmail.com")
//					.avatar("admin.jpg")
                    .password("123")
                    .phone("0392272536")
                    .status(true)
                    .account_balance(0.0)
                    .role(ADMIN)
                    .build();
            System.out.println("Admin token :" + service.register(admin).getAccessToken());

            var audience = RegisterRequest.builder()
                    .name("David12")
                    .email("david@gmail.com")
//					.avatar("huy.jpg")
                    .status(true)
                    .password("123")
                    .phone("0854512367")
                    .role(AUDIENCE)
                    .build();
            audience.setAccount_balance(1000000.0);
            System.out.println("Audience token :" + service.register(audience).getAccessToken());
            var creator = RegisterRequest.builder()
                    .name("Huy")
                    .email("huypt160548@fpt.edu.vn")
//    .avatar("huy.jpg")
                    .status(true)
                    .password("123")
                    .phone("0326514875")
                    .role(CREATOR)
                    .build();
            creator.setAccount_balance(1000000.0);
            System.out.println("Creator token :" + service.register(creator).getAccessToken());

        };
    }

}
