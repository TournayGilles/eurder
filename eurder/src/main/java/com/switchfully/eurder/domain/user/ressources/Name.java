package com.switchfully.eurder.domain.user.ressources;

import static com.switchfully.eurder.internals.Verification.*;

public record Name(String firstname, String lastname) {
    public Name(String firstname, String lastname) {
        this.firstname = verifyField(firstname);
        this.lastname = verifyField(lastname);
    }
}
