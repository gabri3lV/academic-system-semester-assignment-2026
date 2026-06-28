package org.example.academic.system.controller;

import org.example.academic.system.exception.AuthenticationException;
import org.example.academic.system.model.User;
import org.example.academic.system.security.AuthenticationService;

public class AuthenticationController {

    private final AuthenticationService authService;

    public AuthenticationController() {
        this.authService = new AuthenticationService();
    }

    public boolean authenticate(String username, String password) {
        try {
            User user = authService.authenticate(username, password);
            return user != null;
        } catch (AuthenticationException e) {
            return false;
        }
    }
}