package com.company.service;

import com.company.dto.*;
import com.company.exception.UserDetailsNotFoundException;
import com.company.model.User;
import com.company.model.UserDetails;
import com.company.repository.UserDetailsRepository;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService {

    private final UserDetailsRepository userDetailsRepository;
    private final UserService userService;
    private final UserDetailsDTOConverter converter;

    public UserDetailsService(UserDetailsRepository userDetailsRepository, UserService userService, UserDetailsDTOConverter converter) {
        this.userDetailsRepository = userDetailsRepository;
        this.userService = userService;
        this.converter = converter;
    }

    public UserDetailsDTO createUserDetails(final CreateUserDetailsRequest request){

        User user = userService.findUserById(request.getUserId());

        UserDetails userDetails = new UserDetails(request.getPhoneNumber(),
                request.getAddress(),
                request.getCity(),
                request.getCountry(),
                request.getPostCode(),
                user);
        return converter.convert(userDetailsRepository.save(userDetails));
    }

    public UserDetailsDTO updateUserDetails(final Long userDetailsId, final UpdateUserDetailsRequest request){

        UserDetails userDetails = findUserDetailsById(userDetailsId);

        UserDetails updateUserDetails = new UserDetails(
                userDetails.getId(),
                request.getPhoneNumber(),
                request.getAddress(),
                request.getCity(),
                request.getCountry(),
                request.getPostCode(),
                userDetails.getUser());
        return converter.convert(userDetailsRepository.save(updateUserDetails));
    }

    public void deleteusersDetails(final Long id){
        findUserDetailsById(id);

        userDetailsRepository.deleteById(id);
    }



    private UserDetails findUserDetailsById(final Long userDetailsId){
        return userDetailsRepository.findById(userDetailsId)
                .orElseThrow(() -> new UserDetailsNotFoundException("User details couldn't be found by following id" + userDetailsId));
    }

}
