package com.switchfully.eurder.domain.user.ressources;

import com.switchfully.eurder.internals.Verification;

import java.util.Objects;
import java.util.UUID;

public abstract class User {
    private final UUID userId;
    private final String email;

    public User(String email) {
        this.userId = UUID.randomUUID();
        this.email = Verification.verifyEmailFormat(email);
    }

    public UUID getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId.equals(user.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
