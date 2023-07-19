package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;


public interface RoleService {
    void addRole(Role role);
    void deleteRole(long id);
    List<Role> getAllRoles();

    Role getRole(String role);
}
