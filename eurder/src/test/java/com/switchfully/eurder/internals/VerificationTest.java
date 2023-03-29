package com.switchfully.eurder.internals;

import com.switchfully.eurder.internals.exceptions.InvalidFormatException;
import com.switchfully.eurder.internals.exceptions.MissingFieldException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;

import static com.switchfully.eurder.internals.Verification.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VerificationTest {

    @Test
    void givenFilledField_VerifyFieldReturnField(){
        String field = "hello";
        Assertions.assertEquals(field, verifyField(field));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ","\n","\r","\t","\n\r\t"})
    void verifyFieldGivenEmptyOrBlankStringThrowsException(String field){
        Assertions.assertThrows(MissingFieldException.class,()->verifyField(field));
    }
    @Test
    void verifyFieldGivenNullThrowsException(){
        Assertions.assertThrows(MissingFieldException.class,()->verifyField(null));
    }
    @ParameterizedTest
    @ValueSource(strings = {"gilles.tournay@gmail.com",
            "gilles19@hotmail.com",
            "gilles_starl@gmail.com",
            "gilles-sa@yahoo.fr"})
    void verifyEmail_ReturnsEmailWhenValid(String email){
        Assertions.assertEquals(email, verifyEmailFormat(email));
    }

    @ParameterizedTest
    @ValueSource(strings = {"g..illes@gmail.com", "gillesgmail.com", "d_-a@hotmail.com", "gilles@hotmailcom"})
    void verifyEmail_throwsExceptionWhenInvalid(String email) {
        Assertions.assertThrows(InvalidFormatException.class, () -> verifyEmailFormat(email));
    }
}