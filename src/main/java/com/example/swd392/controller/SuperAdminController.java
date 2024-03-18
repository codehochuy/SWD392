package com.example.swd392.controller;

import com.example.swd392.Response.UserResponse.BalanceRequest;
import com.example.swd392.Response.UserResponse.UpdateUserResponse;
import com.example.swd392.repository.UserRepo;
import com.example.swd392.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/super_admin")
public class SuperAdminController {
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    private UserService iUserService;


    public SuperAdminController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping("/send-notification")
    public void sendNotification(@RequestBody String message) {
        messagingTemplate.convertAndSend("/topic/notification", message);
    }

}
