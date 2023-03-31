package com.switchfully.eurder.service.User;

import com.switchfully.eurder.domain.user.ressources.Customer;
import com.switchfully.eurder.domain.user.ressources.Name;
import com.switchfully.eurder.service.User.dto.CreateCustomerDto;
import com.switchfully.eurder.service.User.dto.CustomerDto;

import java.util.List;

public class UserMapper {
    public CustomerDto toCustomerDto(Customer customer){
        return new CustomerDto(customer.getUserId(),customer.getEmail(),customer.getName().firstname(),customer.getName().lastname());
    }
    public Customer fromCreateCustomerDto(CreateCustomerDto createCustomerDto){
        return new Customer(createCustomerDto.email,createCustomerDto.address,new Name(createCustomerDto.firstName, createCustomerDto.lastName), createCustomerDto.phoneNumber);
    }
    public List<CustomerDto> toCustomerDtoList(List<Customer> customerList){
        return customerList.stream().map(this::toCustomerDto).toList();
    }
}
