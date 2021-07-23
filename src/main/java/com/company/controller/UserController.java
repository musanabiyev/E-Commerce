package com.company.controller;

import com.company.dto.CreateUserRequest;
import com.company.dto.UpdateUserRequest;
import com.company.dto.UserDTO;
import com.company.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{mail}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("mail") String mail) {
        return ResponseEntity.ok(userService.getUserByMail(mail));
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody CreateUserRequest userRequest) {
        return ResponseEntity.ok(userService.createUser(userRequest));
    }

    @PutMapping("/{mail}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable("mail") String mail,
            @RequestBody UpdateUserRequest updateUserRequest) {
        return ResponseEntity.ok(userService.updateUser(mail, updateUserRequest));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> deactiveUser(@PathVariable("id") Long id) {
        userService.deactivateUser(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/active")
    public ResponseEntity<Void> activeUser(@PathVariable("id") Long id) {
        userService.activeUser(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
