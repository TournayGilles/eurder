package com.switchfully.eurder.domain.user.ressources;

import static com.switchfully.eurder.internals.Verification.*;


public record Address(String streetName, String streetNumber, String postalCode, String city, String country) {
    public Address(String streetName, String streetNumber, String postalCode, String city, String country) {
        this.streetName = verifyField(streetName);
        this.streetNumber = verifyField(streetNumber);
        this.postalCode = verifyField(postalCode);
        this.city = verifyField(city);
        this.country = verifyField(country);
    }
}
