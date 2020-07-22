package com.diser.controller;

import com.diser.entity.User;
import com.diser.entity.Views;
import com.diser.security.jwt.JwtTokenProvider;
import com.diser.service.impl.UserServiceImpl;
import com.fasterxml.jackson.annotation.JsonView;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Optional;

@RestController
@RequestMapping("menu")
public class MenuController {

    private final AuthenticationManager authenticationManager;
    private final UserServiceImpl userService;
    private final UserDetailsService userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public MenuController(AuthenticationManager authenticationManager, UserServiceImpl userService,
                          UserDetailsService userDetailsService, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping("/manual")
    public void downloadManual(HttpServletResponse response) {
        try {
            File file = new File(getClass().getClassLoader().getResource("files/Manual.docx").getFile());
            InputStream in = file.toURI().toURL().openStream();
            response.setContentType("application/docx");
            response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
            IOUtils.copy(in, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/save_settings")
    public User saveSettings(@RequestBody User settings) {
        return userService.updateUser(settings);
    }

    @GetMapping("/find_user")
    @JsonView(Views.UserInfo.class)
    public User findAuthUser(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            Optional<Cookie> cookieOptional = Arrays.stream(cookies)
                    .filter(item -> item.getName().equalsIgnoreCase("jwt.token"))
                    .findFirst();
            String bearerToken = cookieOptional.map(Cookie::getValue).orElse(null);
            if (bearerToken != null && bearerToken.startsWith("Bearer_")) {
                String token = bearerToken.substring(7, bearerToken.length());
                return (User) this.userDetailsService.loadUserByUsername(jwtTokenProvider.getUsername(token));
            }
        }
        return null;
    }
}
