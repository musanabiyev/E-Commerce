package com.company.service;

import com.company.dto.UserDTO;
import com.company.dto.UserDTOConverter;
import com.company.model.User;
import com.company.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import java.util.List;

import static com.company.service.TestSupport.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceTest extends TestSupport {

    private UserDTOConverter converter;
    private UserRepository userRepository;

    private UserService userService;

    @BeforeAll
    public  void setUp(){
        converter = mock(UserDTOConverter.class);
        userRepository = mock(UserRepository.class);

        userService = new UserService(userRepository,converter);
    }


    @Test
    public void  testGetAllUsers_itShouldReturnuserDtoList(){
        List<User> userList = generateUsers();
        List<UserDTO> userDtoList = generateUserDtoList(userList);
        when(userRepository.findAll()).thenReturn(userList);
        when(converter.convert(userList)).thenReturn(userDtoList);

        List<UserDTO> result = userService.getAllUsers();

        assertEquals(userDtoList,result);
        Mockito.verify(userRepository).findAll();
        verify(converter).convert(userList);
    }

}