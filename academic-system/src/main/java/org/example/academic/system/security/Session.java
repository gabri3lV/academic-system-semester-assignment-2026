package org.example.academic.system.security;

import org.example.academic.system.model.Role;
import org.example.academic.system.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Session {

    private static final Logger logger =
            LoggerFactory.getLogger(Session.class);

    private static User currentUser;

    public static void login(User user) {
        currentUser = user;
    }

    public static void logout() {
        if (currentUser != null) {
            logger.info("Logout: username={}, role={}",
                    currentUser.getUsername(), currentUser.getRole());
        }
        currentUser = null;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static Role getCurrentRole() {
        return currentUser != null ? currentUser.getRole() : null;
    }

    public static boolean isLogged() {
        return currentUser != null;
    }

    public static boolean isAdmin() {
        return currentUser != null && currentUser.getRole() == Role.ADMIN;
    }

    // Compatibilidade com código JavaFX que usa Session.getInstance()
    public static Session getInstance() {
        return SessionHolder.INSTANCE;
    }

    private static class SessionHolder {
        private static final Session INSTANCE = new Session();
    }

    public String getCurrentUserRole() {
        return currentUser != null ? currentUser.getRole().name() : null;
    }

    public void clear() {
        logout();
    }
}