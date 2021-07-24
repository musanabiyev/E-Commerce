package com.company.dto;

import com.company.model.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDetailsDTOConverter {

    public UserDetailsDTO convert(UserDetails from){
        return new UserDetailsDTO(from.getPhoneNumber(), from.getAddress(), from.getCity(), from.getCountry(), from.getPostCode());
    }

    public List<UserDetailsDTO> convert(List<UserDetails> from){
        return from.stream().map(this::convert).collect(Collectors.toList());
    }
}
