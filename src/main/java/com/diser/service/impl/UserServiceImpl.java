package com.diser.service.impl;

import com.diser.entity.Roles;
import com.diser.entity.User;
import com.diser.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailSenderImpl mailSender;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, MailSenderImpl mailSender) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Данный пользователь не зарегистриован");
        }

        return user;
    }

    public User addUser(User user) {
        User findUser = userRepository.findByUsername(user.getUsername());

        if(findUser != null) {
            return null;
        }

        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActivationcode(UUID.randomUUID().toString());

        if(!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Здравствуйте, %s! \n" +
                            "Добро пожаловать! Для активации учетной записи перейдите по ссылке: http://localhost:8080/activate/%s",
                    user.getFirstname() + " " + user.getLastname(),
                    user.getActivationcode()
            );

            mailSender.send(user.getEmail(), "Код активации", message);
        }

        return userRepository.save(user);
    }

    public User updateUser(User settings) {
        Optional<User> currentUser = userRepository.findById(settings.getId());
        currentUser.ifPresent(item -> {
            String password = settings.getPassword();
            if (password != null && !password.isEmpty()) item.setPassword(passwordEncoder.encode(password));
            item.setFirstname(settings.getFirstname());
            item.setLastname(settings.getLastname());
            item.setMiddlename(settings.getMiddlename());
            item.setAge(settings.getAge());
            userRepository.save(item);
        });
        return currentUser.orElse(null);
    }

    public boolean activateUser(String code) {
        code = code.replaceAll("=","");
        User user = userRepository.findByActivationcode(code);

        if (user == null) {
            return false;
        }

        user.setActivationcode(null);
        userRepository.save(user);

        return true;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> getAll() {
        return userRepository.findAll().stream()
                .filter(item -> !item.getRoles().contains(Roles.ADMIN))
                .collect(Collectors.toList());
    }
}
