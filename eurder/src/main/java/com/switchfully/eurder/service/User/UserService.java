package com.switchfully.eurder.service.User;

import com.switchfully.eurder.domain.user.UserRepository;
import com.switchfully.eurder.domain.user.ressources.Customer;
import com.switchfully.eurder.service.dto.CreateCustomerDto;
import com.switchfully.eurder.service.dto.CustomerDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        userMapper = new UserMapper();
    }
    public CustomerDto getCustomerById(String userId, String customerId){
        userRepository.verifyAdmin(UUID.fromString(userId));
        return userMapper.toCustomerDto(userRepository.getCustomerById(UUID.fromString(customerId)));
    }
    public List<CustomerDto> getAllCustomers(String userId){
        userRepository.verifyAdmin(UUID.fromString(userId));
        return userMapper.toCustomerDtoList(userRepository.getAllCustomers());
    }
    public String registerCustomer(CreateCustomerDto createCustomerDto) {
        Customer customer = userMapper.fromCreateCustomerDto(createCustomerDto);
        userRepository.save(customer);
        return customer.getUserId().toString();
    }
}
