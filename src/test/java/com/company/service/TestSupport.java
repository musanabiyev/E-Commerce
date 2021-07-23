package com.company.service;

import com.company.dto.UserDTO;
import com.company.model.User;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestSupport {

    public static List<User> generateUsers(){
        return IntStream.range(0,5).mapToObj(i ->
                new User((long) i,
                    i + "@folk.net",
                    i + "firstname",
                    i + "lastname",
                    i + "",
                    new Random(2).nextBoolean())
            ).collect(Collectors.toList());
    }

    public static List<UserDTO> generateUserDtoList(List<User> users){
     return users.stream().map(from -> new UserDTO(from.getMail(), from.getFirstName(), from.getLastName(), from.getMiddleName()))
             .collect(Collectors.toList());
    }
}
