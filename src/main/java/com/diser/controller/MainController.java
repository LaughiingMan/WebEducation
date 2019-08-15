package com.diser.controller;

import com.diser.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
public class MainController {

    private MessageRepository messageRepository;

    @Autowired
    public MainController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Value("${spring.profiles.active}")
    private String profile;

    @GetMapping
    public String main(Model model) {

        Map<Object, Object> data = new HashMap<>();

        data.put("messages", messageRepository.findAll());

        model.addAttribute("data", data);
        model.addAttribute("isDevMode", "dev".equals(profile));
        return "index";
    }
}
