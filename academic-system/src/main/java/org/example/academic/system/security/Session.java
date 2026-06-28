package org.example.academic.system.security;

import org.example.academic.system.model.Role;
import org.example.academic.system.model.User;

public class Session {

    private static User currentUser;

    public static void login(User user) {
        currentUser = user;
    }

    public static void logout() {
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
}