package com.example.swd392.serviceimplement;

import com.example.swd392.model.User;
import com.example.swd392.repository.UserRepo;
import com.example.swd392.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserImplement implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Override
    public List<User> getAll() {
        return userRepo.findAll();
    }


}
