package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepos;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class UserServiceImp implements UserService, UserDetailsService {
    private final UserRepos userRepos;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImp(UserRepos userRepos, PasswordEncoder passwordEncoder) {
        this.userRepos = userRepos;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepos.findAll();
    }
    @Override
    public User getUserById(long id) {
        Optional<User> userFromDb = userRepos.findById(id);
        return userFromDb.orElse(new User());
    }
    @Override
    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepos.save(user);
    }
    @Override
    public void deleteUser(long id) { userRepos.deleteById(id); }
    @Override
    public void updateUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepos.save(user);
    }

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
