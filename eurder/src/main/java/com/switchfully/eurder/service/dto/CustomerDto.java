package com.switchfully.eurder.service.dto;

import java.util.UUID;

public class CustomerDto {
    public final UUID userId;
    public final String email;
    public final String firstname;
    public final String lastname;

    public CustomerDto(UUID userId, String email, String firstname, String lastname) {
        this.userId = userId;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
    }
}
