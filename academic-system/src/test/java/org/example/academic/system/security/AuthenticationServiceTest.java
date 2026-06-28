package org.example.academic.system.security;

import org.example.academic.system.exception.AuthenticationException;
import org.example.academic.system.model.Role;
import org.example.academic.system.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AuthenticationServiceTest {

    private AuthenticationService authService;

    @BeforeEach
    void setUp() {
        Session.logout(); // garante sessão limpa antes de cada teste
        authService = new AuthenticationService();
    }

    @Test
    void validAdminCredentialsShouldAuthenticateSuccessfully() {
        User user = authService.authenticate("admin", "admin123");
        assertNotNull(user);
        assertEquals("admin", user.getUsername());
        assertEquals(Role.ADMIN, user.getRole());
    }

    @Test
    void validProfessorCredentialsShouldAuthenticateSuccessfully() {
        User user = authService.authenticate("professor", "prof123");
        assertNotNull(user);
        assertEquals(Role.PROFESSOR, user.getRole());
    }

    @Test
    void invalidUsernameShouldThrowAuthenticationException() {
        assertThrows(AuthenticationException.class, () ->
                authService.authenticate("naoexiste", "admin123")
        );
    }

    @Test
    void invalidPasswordShouldThrowAuthenticationException() {
        assertThrows(AuthenticationException.class, () ->
                authService.authenticate("admin", "senhaerrada")
        );
    }

    @Test
    void successfulAuthenticationShouldCreateSession() {
        authService.authenticate("admin", "admin123");
        assertTrue(Session.isLogged());
        assertEquals("admin", Session.getCurrentUser().getUsername());
    }
}