package org.example.academic.system.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserEqualityTest {

    @Test
    void twoUsersWithSameUsernameShouldBeEqual() {
        User u1 = new User("admin", "senha1", Role.ADMIN);
        User u2 = new User("admin", "senha2", Role.PROFESSOR);
        assertEquals(u1, u2);
    }

    @Test
    void twoUsersWithSameUsernameShouldHaveSameHashCode() {
        User u1 = new User("admin", "senha1", Role.ADMIN);
        User u2 = new User("admin", "senha2", Role.PROFESSOR);
        assertEquals(u1.hashCode(), u2.hashCode());
    }

    @Test
    void twoUsersWithDifferentUsernamesShouldNotBeEqual() {
        User u1 = new User("admin", "senha", Role.ADMIN);
        User u2 = new User("professor", "senha", Role.PROFESSOR);
        assertNotEquals(u1, u2);
    }
}