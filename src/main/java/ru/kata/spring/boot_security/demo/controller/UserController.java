package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.service.UserService;
import java.security.Principal;

@Controller
@RequestMapping("/")
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping(value = "login")
    public String loginPage() {
        return "bootstrap/login";
    }
    @GetMapping("/user")
    public String user(Principal principal, ModelMap model) {
        model.addAttribute("user", userService.findByUsername(principal.getName()));
        return "bootstrap/user";
    }



}
