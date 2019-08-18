package com.diser.controller;

import com.diser.entity.Views;
import com.diser.repository.MessageRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
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

    private final MessageRepository messageRepository;
    private final ObjectWriter writer;

    @Autowired
    public MainController(MessageRepository messageRepository, ObjectMapper mapper) {
        this.messageRepository = messageRepository;
        this.writer = mapper
                .setConfig(mapper.getSerializationConfig())
                .writerWithView(Views.FullMessage.class);
    }

    @Value("${spring.profiles.active}")
    private String profile;

    @GetMapping
    public String main(Model model) throws JsonProcessingException {
        Map<Object, Object> data = new HashMap<>();

        String messages = writer.writeValueAsString(messageRepository.findAll());
        model.addAttribute("messages", messages);

        model.addAttribute("data", data);
        model.addAttribute("isDevMode", "dev".equals(profile));
        return "index";
    }
}
