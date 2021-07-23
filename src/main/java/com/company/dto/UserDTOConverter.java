package com.company.dto;

import com.company.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDTOConverter {

    public UserDTO convert(User from){
        return new UserDTO(from.getMail(), from.getFirstName(),from.getLastName(),from.getMiddleName());
    }

    public List<UserDTO> convert(List<User> fromList){
        return fromList.stream()
                .map(from -> new UserDTO(from.getMail(), from.getFirstName(),from.getLastName(),from.getMiddleName()))
                .collect(Collectors.toList());
    }
}
