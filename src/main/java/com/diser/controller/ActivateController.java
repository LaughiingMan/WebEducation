package com.diser.controller;

import com.diser.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("activated")
public class ActivateController {

    private final UserServiceImpl userService;

    @Autowired
    public ActivateController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping
    public boolean activate(@RequestBody String code) {
        return userService.activateUser(code);
    }
}
