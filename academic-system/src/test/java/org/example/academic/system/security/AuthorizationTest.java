package org.example.academic.system.security;

import org.example.academic.system.model.Role;
import org.example.academic.system.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AuthorizationTest {

    @AfterEach
    void tearDown() {
        Session.logout();
    }

    @Test
    void adminShouldBeAuthorizedForAdminOperations() {
        Session.login(new User("admin", "admin123", Role.ADMIN));
        assertTrue(Session.isAdmin());
    }

    @Test
    void professorShouldNotBeAuthorizedForAdminOperations() {
        Session.login(new User("professor", "prof123", Role.PROFESSOR));
        assertFalse(Session.isAdmin());
    }

    @Test
    void loggedOutUserShouldNotBeAuthorized() {
        Session.logout();
        assertFalse(Session.isAdmin());
        assertFalse(Session.isLogged());
    }

    @Test
    void logoutShouldTerminateSession() {
        Session.login(new User("admin", "admin123", Role.ADMIN));
        assertTrue(Session.isLogged());
        Session.logout();
        assertFalse(Session.isLogged());
        assertNull(Session.getCurrentUser());
    }
}