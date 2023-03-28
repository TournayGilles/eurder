package com.switchfully.eurder.service;

import com.switchfully.eurder.domain.user.UserRepository;
import com.switchfully.eurder.domain.user.ressources.Address;
import com.switchfully.eurder.domain.user.ressources.Admin;
import com.switchfully.eurder.domain.user.ressources.Customer;
import com.switchfully.eurder.domain.user.ressources.Name;
import com.switchfully.eurder.internals.exceptions.NoCustomerWithProvidedIdException;
import com.switchfully.eurder.internals.exceptions.NoRightException;
import com.switchfully.eurder.service.User.UserService;
import com.switchfully.eurder.service.dto.CreateCustomerDto;
import com.switchfully.eurder.service.dto.CustomerDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceTest {
    UserRepository userRepository = new UserRepository();
    UserService userService = new UserService(userRepository);
    Customer customer;
    String adminId;
    @BeforeEach
    void setup(){
        customer = new Customer("aa@aa.com",
                new Address("era street","26","1586","City","Country"),
                new Name("john", "doe"),
                "+0123456789");
        Admin admin = new Admin("aba@aa.com");
        adminId = admin.getUserId().toString();
        userRepository.getUserByUUIDRepository().put(admin.getUserId(),admin);
        userRepository.save(customer);
    }

    @Test
    void getCustomerByIdWithValidAdminIdReturnCustomerDtoOfCorrectCustomer(){
        CustomerDto actual = userService.getCustomerById(adminId, customer.getUserId().toString());

        Assertions.assertEquals(customer.getUserId(), actual.userId);
        Assertions.assertEquals(customer.getName().firstname(), actual.firstname);
        Assertions.assertEquals(customer.getName().lastname(), actual.lastname);
        Assertions.assertEquals(customer.getEmail(), actual.email);
    }
    @Test
    void getCustomerByIdWithInvalidAdminIdThrowsException(){
        Assertions.assertThrows(NoRightException.class, () -> userService.getCustomerById(UUID.randomUUID().toString(), UUID.randomUUID().toString()));
    }
    @Test
    void getCustomerByIdWithValidAdminIdThrowsExceptionWhenNonExistingId(){
        Assertions.assertThrows(NoCustomerWithProvidedIdException.class, ()-> userService.getCustomerById(adminId,UUID.randomUUID().toString()));
    }
    @Test
    void getAllCustomerWithValidAdminIDReturnsCorrectListOfCustomerDto(){
        Assertions.assertEquals(1, userService.getAllCustomers(adminId).size());
    }
    @Test
    void getAllCustomerWithInValidAdminIDThrowsException(){
        Assertions.assertThrows(NoRightException.class, () -> userService.getAllCustomers(UUID.randomUUID().toString()));
    }
    @Test
    void registerCustomerStoreCustomerIntoRepo(){
        CreateCustomerDto createCustomerDto = new CreateCustomerDto("aapead@fdf.com","a","b", customer.getAddress(),"+32478963251");
        Assertions.assertDoesNotThrow(()-> userService.registerCustomer(createCustomerDto));
        Assertions.assertEquals(2, userRepository.getAllCustomers().size());
    }

}
