package com.switchfully.eurder.domain.user.ressources;

import com.switchfully.eurder.internals.Verification;
import com.switchfully.eurder.internals.exceptions.MissingFieldException;

public class Customer extends User {
    private final Address address;
    private final Name name;
    private final String phoneNumber;

    public Customer(String email, Address address, Name name, String phoneNumber) {
        super(email);
        if (address == null || name == null){
            throw new MissingFieldException();
        }
        this.address = address;
        this.name = name;
        this.phoneNumber = Verification.verifyPhoneNumber(phoneNumber);
    }

    public Address getAddress() {
        return address;
    }

    public Name getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

}
