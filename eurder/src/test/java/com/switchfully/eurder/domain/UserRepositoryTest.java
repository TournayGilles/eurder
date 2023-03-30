package com.switchfully.eurder.domain;

import com.switchfully.eurder.domain.user.*;
import com.switchfully.eurder.domain.user.ressources.*;
import com.switchfully.eurder.internals.exceptions.MissingFieldException;
import com.switchfully.eurder.internals.exceptions.NoCustomerWithProvidedIdException;
import com.switchfully.eurder.internals.exceptions.NoRightException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserRepositoryTest {
    UserRepository userRepository = new UserRepository();
    Customer customer;
    @BeforeEach
    void setup(){
        customer = new Customer("aa@aa.com",
                new Address("era street","26","1586","Chalestown","Sealand"),
                new Name("john", "doe"),
                "+0123456789");
    }

    @Test
    void atLaunch_repositoryContainsOnlyAnAdmin(){
        Assertions.assertEquals(1, userRepository.getUserByUUIDRepository().size());
        Assertions.assertTrue(userRepository.getUserByUUIDRepository().values().stream().findFirst().get() instanceof Admin);
    }
    @Test
    void whenSaveCustomer_saveReturnsThatCustomerAfterAddingItToRepo(){
        Assertions.assertEquals(customer, userRepository.save(customer));
        Assertions.assertTrue(userRepository.getUserByUUIDRepository().containsValue(customer));
    }
    @Test
    void whenGetAllCustomers_ReturnsAListOfCustomersOnly(){
        userRepository.save(customer);
        Assertions.assertEquals(1, userRepository.getAllCustomers().size());
    }
    @Test
    void whenGetAllCustomers_WithNoCustomerInRepo_ReturnsAnEmptyList(){
        Assertions.assertTrue(userRepository.getAllCustomers().isEmpty());
    }
    @Test
    void getCustomerDetails_WhenGivingAdminId_throwsException(){
        User user = userRepository.getUserByUUIDRepository().values().stream().findFirst().get();
        Assertions.assertThrows(NoCustomerWithProvidedIdException.class, () -> userRepository.getCustomerById(user.getUserId()));
    }
    @Test
    void getCustomerDetails_WhenGivingUnknownId_throwsException(){
        userRepository.save(customer);
        Assertions.assertThrows(NoCustomerWithProvidedIdException.class, () -> userRepository.getCustomerById(UUID.randomUUID()));
    }
    @Test
    void getCustomerDetails_WhenGivingValidId_ReturnsAssociatedCustomer(){
        userRepository.save(new Customer("a-sd@asd.com",new Address("a","b","c","d","e"),new Name("a","b"),"+0000000000"));
        userRepository.save(customer);
        Assertions.assertEquals(customer,userRepository.getCustomerById(customer.getUserId()));
    }
    @Test
    void verifyAdmin_WhenGivenUnknownId_ThrowsException(){
        Assertions.assertThrows(NoRightException.class, ()->userRepository.verifyAdmin(UUID.randomUUID()));
    }
    @Test
    void verifyAdmin_WhenGivenCustomerId_ThrowsException(){
        userRepository.save(customer);
        Assertions.assertThrows(NoRightException.class, () -> userRepository.verifyAdmin(customer.getUserId()));
    }
    @Test
    void verifyAdmin_WhenGivenAdminId_DoesNotThrowException(){
        User user = userRepository.getUserByUUIDRepository().values().stream().findFirst().get();
        Assertions.assertDoesNotThrow(() -> userRepository.verifyAdmin(user.getUserId()));
    }
    @Test
    void CustomerConstructorThrowsExceptionWhenInvalidField(){
        Assertions.assertThrows(MissingFieldException.class, ()-> new Customer("aa@aa.com", null, null, "+478963214"));
    }
}
