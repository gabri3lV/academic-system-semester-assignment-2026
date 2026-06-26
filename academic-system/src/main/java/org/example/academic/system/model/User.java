package org.example.academic.system.model;

import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString
public class User {

    private final String username;

    public User(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof User that)) {
            return false;
        }
        return Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
