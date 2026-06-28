package org.example.academic.system.controller;

import org.example.academic.system.exception.AuthenticationException;
import org.example.academic.system.model.User;
import org.example.academic.system.security.AuthenticationService;

public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController() {
        this.authenticationService = new AuthenticationService();
    }

    public boolean authenticate(String username, String password) {
        try {
            authenticationService.authenticate(username, password);
            return true;
        } catch (AuthenticationException e) {
            return false;
        }
    }

    public User authenticateAndReturn(String username, String password) {
        return authenticationService.authenticate(username, password);
    }
}