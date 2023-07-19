package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import java.security.Principal;



@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;
    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }
    @GetMapping()
    public String printUsers(Principal principal, ModelMap model) {
        model.addAttribute("user", userService.findByUsername(principal.getName()));
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("roles", roleService.getAllRoles());
        return "bootstrap/users";
    }

    @GetMapping("/new")
    public String addUser(Model model,Principal principal) {
        User user = new User();
        model.addAttribute("admin",userService.findByUsername(principal.getName()));
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.getAllRoles());
        return "bootstrap/add";
    }

    @PostMapping("/add")
    public String create(@ModelAttribute("user") User user,
                         @RequestParam(value = "selectoradd") String[] changeRole) {
        for (String s : changeRole) {
            user.addRole(roleService.getRole(s));
        }
        userService.addUser(user);
        return "redirect:/admin";
    }

    @PatchMapping("/{id}/edit")
    public String update(@ModelAttribute("user") User user, Model model,
                         @RequestParam(value = "selector") String[] changeRole) {
        model.addAttribute("roles", roleService.getAllRoles());
        for (String s : changeRole) {
            user.addRole(roleService.getRole(s));
        }
        userService.updateUser(user);
        return "redirect:/admin";
    }
    @DeleteMapping(value = "/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
