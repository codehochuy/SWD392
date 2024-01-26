package com.example.swd392.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @GetMapping("/")
    @PreAuthorize("hasAuthority('admin:read')")
    public String get(){
        return "GET::Admin Controller";
    }
    @PostMapping("/")
    public String post(){
        return "POST::Admin Controller";
    }
    @DeleteMapping("/")
    public String delete(){
        return "DELETE::Admin Controller";
    }
    @PutMapping("/")
    public String put(){
        return "PUT::Admin Controller";
    }

}
