package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepos;

import java.util.List;
@Service
@Transactional
public class RoleServiceImp implements RoleService{
    RoleRepos roleRepos;
    @Autowired
    public RoleServiceImp(RoleRepos roleRepos) {
        this.roleRepos = roleRepos;
    }

    @Override
    public void addRole(Role role) {
        roleRepos.save(role);
    }

    @Override
    public void deleteRole(long id) {
        roleRepos.deleteById(id);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepos.findAll();
    }
}
