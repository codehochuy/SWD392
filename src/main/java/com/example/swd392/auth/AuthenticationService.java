package com.example.swd392.auth;

import com.example.swd392.config.JwtService;
import com.example.swd392.enums.Role;
import com.example.swd392.enums.TokenType;
import com.example.swd392.model.Token;
import com.example.swd392.model.User;
import com.example.swd392.repository.TokeRepo;
import com.example.swd392.repository.UserRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepo userRepo;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final TokeRepo tokeRepo;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .email(request.getEmail())
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
//                .address(request.getAddress())
//                .phone(request.getPhone())
                .role(request.getRole())
                .build();
        var save = userRepo.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(save, jwtToken);
        return AuthenticationResponse.builder()
                .status("Hello")
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void revokeAllUserTokens(User user) {
        var validToken = tokeRepo.findAllValidTokensByUser(user.getUsersID());
        if (validToken.isEmpty())
            return;
        validToken.forEach(t -> {
            t.setRevoked(true);
            t.setExpired(true);
        });
        tokeRepo.saveAll(validToken);
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();
        tokeRepo.save(token);
    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepo.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .status("Login successfully")
//                .userInfo(userRepo.findUserByEmail(request.getEmail()).orElseThrow())
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String jwt;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            return;
        }
        refreshToken =authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);// todo extract the userEmail from JWT Token
        if(userEmail!= null ){
            var user = this.userRepo.findUserByEmail(userEmail).orElseThrow();
            if(jwtService.isTokenValid(refreshToken,user)){
               var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
               var authResponse = AuthenticationResponse.builder()
                       .accessToken(accessToken)
                       .refreshToken(refreshToken)
                       .build();
               new ObjectMapper().writeValue(response.getOutputStream(),authResponse);
            }
        }
    }
}
