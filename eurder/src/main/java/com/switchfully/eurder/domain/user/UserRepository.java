package com.switchfully.eurder.domain.user;

import com.switchfully.eurder.domain.user.ressources.Admin;
import com.switchfully.eurder.domain.user.ressources.Customer;
import com.switchfully.eurder.domain.user.ressources.User;
import com.switchfully.eurder.internals.exceptions.NoCustomerWithProvidedIdException;
import com.switchfully.eurder.internals.exceptions.NoRightException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserRepository {
    private final Map<UUID, User> userByUUIDRepository;

    public UserRepository() {
        userByUUIDRepository = new ConcurrentHashMap<>();
        Admin admin = new Admin("admin@admin.com");
        userByUUIDRepository.put(admin.getUserId(),admin);
    }
    public User save(Customer customer){
        userByUUIDRepository.put(customer.getUserId(),customer);
        return customer;
    }
    public List<Customer> getAllCustomers(){
        return userByUUIDRepository.values().stream()
                .filter(user -> user instanceof Customer)
                .map(user->(Customer)user).toList();
    }
    public Customer getCustomerById(UUID userId){
        User user = userByUUIDRepository.get(userId);
        if ( user == null || user instanceof Admin){
            throw new NoCustomerWithProvidedIdException("No Customer found with id: " + userId.toString());
        }
        return (Customer) user;
    }
    public void verifyAdmin(UUID userId){
        User user = userByUUIDRepository.get(userId);
        if (user == null || !(user instanceof Admin)){
            throw new NoRightException("No Right to request for id: " + userId.toString());
        }
    }

    public Map<UUID, User> getUserByUUIDRepository() {
        return userByUUIDRepository;
    }
}
