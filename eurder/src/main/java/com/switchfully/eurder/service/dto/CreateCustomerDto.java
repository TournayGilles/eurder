package com.switchfully.eurder.service.dto;

import com.switchfully.eurder.domain.user.ressources.Address;

public class CreateCustomerDto {
    public final String email;
    public final String firstName;
    public final String lastName;
    public final Address address;
    public final String phoneNumber;

    public CreateCustomerDto(String email, String firstName, String lastName, Address address, String phoneNumber) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
}
