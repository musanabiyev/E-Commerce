package com.company.controller;

import com.company.dto.CreateUserDetailsRequest;
import com.company.dto.UpdateUserDetailsRequest;
import com.company.dto.UpdateUserRequest;
import com.company.dto.UserDetailsDTO;
import com.company.service.UserDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/userdetails")
public class UserDetailsController {

    private final UserDetailsService userDetailsService;


    public UserDetailsController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostMapping
    public ResponseEntity<UserDetailsDTO> createUserDetails(@RequestBody CreateUserDetailsRequest request){
        return ResponseEntity.ok(userDetailsService.createUserDetails(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDetailsDTO> updateUserDetails(
            @PathVariable Long id,
            @RequestBody UpdateUserDetailsRequest request){
        return ResponseEntity.ok(userDetailsService.updateUserDetails(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserDetails(@PathVariable Long id){
        userDetailsService.deleteusersDetails(id);
        return ResponseEntity.ok().build();
    }
}
