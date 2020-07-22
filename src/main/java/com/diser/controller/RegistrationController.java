package com.diser.controller;

import com.diser.entity.User;
import com.diser.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

    private final UserServiceImpl userService;

    @Autowired
    public RegistrationController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping
    public User registration(@RequestBody User user) {
        return userService.addUser(user);
    }
}
