package org.example.academic.system.security;

import org.example.academic.system.exception.AuthenticationException;
import org.example.academic.system.model.Role;
import org.example.academic.system.model.User;

import java.util.ArrayList;
import java.util.List;

public class AuthenticationService {

    private List<User> users;

    public AuthenticationService() {
        users = new ArrayList<>();
        // Usuários padrão do sistema
        users.add(new User("admin", "admin123", Role.ADMIN));
        users.add(new User("professor", "prof123", Role.PROFESSOR));
    }

    public User authenticate(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username)
                    && user.getPassword().equals(password)) {
                Session.login(user);
                return user;
            }
        }
        throw new AuthenticationException("Invalid username or password.");
    }
}