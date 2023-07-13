package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepos;

import java.util.List;


@Service
@Transactional
public class UserServiceImp implements UserService, UserDetailsService {
    private final UserRepos userRepos;

    @Autowired
    public UserServiceImp(UserRepos userRepos) {
        this.userRepos = userRepos;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepos.findAll();
    }
    @Override
    public Object getUserById(long id) {
        return userRepos.findById(id);
    }
    @Override
    public void addUser(User user) {
        /*User userFromDB = userRepos.findByUsername(user.getUsername());

        if (userFromDB != null) {
            user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        }*/


        userRepos.save(user);
    }
    @Override
    public void deleteUser(long id) { userRepos.deleteById(id); }
    @Override
    public void updateUser(User user) { userRepos.save(user); }

    @Override
    public User findByUsername(String username) {
        return userRepos.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepos.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }
}
