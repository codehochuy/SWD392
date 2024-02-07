package com.example.swd392.controller;

import com.example.swd392.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/creator")
@PreAuthorize("hasRole('CREATOR')")
public class CreatorController {
    @Autowired
    private UserService iUserService;

}
