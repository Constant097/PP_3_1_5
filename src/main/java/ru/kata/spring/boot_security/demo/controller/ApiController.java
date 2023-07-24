package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")

public class ApiController {
    private final UserService userService;
    private final RoleService roleService;
    @Autowired
    public ApiController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }
    @GetMapping()
    public ResponseEntity<User> getUserInfo(Principal principal) {
        return new ResponseEntity<>(userService.findByUsername(principal.getName()),HttpStatus.OK);
    }
    @GetMapping("/users")
    public ResponseEntity<List<User>> printUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }
    @GetMapping("users/{id}")
    public ResponseEntity<User> getUser (@PathVariable("id") long id) {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Void> create(@RequestBody User user) {
        userService.addUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<Void> pageEdit(@PathVariable("id") int id, @RequestBody User user) {
            userService.updateUser(user);
            return new ResponseEntity<>(HttpStatus.OK);

    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
