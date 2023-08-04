package com.example.demo.controllers;

import com.example.demo.dtos.RegisterDto;
import com.example.demo.entities.User;
import com.example.demo.services.AuthenticationService;
import com.example.demo.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping(value = "/view")
public class ThymeleafController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("registerDto", new RegisterDto());
        return "registration-form";
    }

    @PostMapping
    public ResponseEntity<User> register(@Valid @ModelAttribute RegisterDto registerDto) {
        User user = authenticationService.register(registerDto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(uri).body(user);
    }

    @GetMapping("/users")
    public String findAll(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "users";
    }

}

