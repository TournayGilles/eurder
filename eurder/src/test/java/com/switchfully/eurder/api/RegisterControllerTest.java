package com.switchfully.eurder.api;

import com.switchfully.eurder.domain.user.UserRepository;
import com.switchfully.eurder.domain.user.ressources.Address;
import com.switchfully.eurder.service.User.UserService;
import com.switchfully.eurder.service.User.dto.CreateCustomerDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.apache.http.protocol.HTTP;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RegisterControllerTest {
    @LocalServerPort
    int port;
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @Test
    void Register_ReturnsUserId(){
        CreateCustomerDto createCustomerDto = new CreateCustomerDto("aa@aa.com", "gilles","tournay",new Address("a","a","a","a","a"), "+32456987123");
        String userId = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(createCustomerDto)
                .when()
                .port(port)
                .post("/register")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .extract().asString();
        Assertions.assertTrue(userRepository.getUserByUUIDRepository().containsKey(UUID.fromString(userId)));
    }
}
