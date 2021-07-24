package com.company.service;


import com.company.dto.CreateUserRequest;
import com.company.dto.UpdateUserRequest;
import com.company.dto.UserDTO;
import com.company.dto.UserDTOConverter;
import com.company.exception.UserIsNotActiveException;
import com.company.exception.UserNotFoundException;
import com.company.model.User;
import com.company.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final UserDTOConverter userDTOConverter;

    public UserService(UserRepository userRepository, UserDTOConverter userDTOConverter) {
        this.userRepository = userRepository;
        this.userDTOConverter = userDTOConverter;
    }

    public List<UserDTO> getAllUsers() {
        return userDTOConverter.convert(userRepository.findAll());
    }

    public UserDTO getUserByMail(String mail) {
        User user = findUserByMail(mail);
        return userDTOConverter.convert(user);
    }

    public UserDTO createUser(final CreateUserRequest userRequest) {
        User user = new User(
                userRequest.getMail(),
                userRequest.getFirstName(),
                userRequest.getLastName(),
                userRequest.getMiddleName(),
                true);


        return userDTOConverter.convert(userRepository.save(user));
    }

    public UserDTO updateUser(final String mail, final UpdateUserRequest updateUserRequest) {

        User user = findUserByMail(mail);
        if (!user.isActive()) {
            logger.warn(String.format("The User wanted update is not active!, user mail: %s", mail));
            throw new UserIsNotActiveException();
        }
        User updateUser = new User(
                user.getId(),
                user.getMail(),
                user.getFirstName(),
                user.getLastName(),
                user.getMiddleName());

        return userDTOConverter.convert(userRepository.save(updateUser));
    }


    public void deactivateUser(final Long id) {
        changeActivateUser(id, false);
    }

    public void activeUser(final Long id) {
        changeActivateUser(id, true);
    }

    public void deleteUser(final Long id) {
        if (doesUserExist(id)) {
            userRepository.existsById(id);
        } else {
            throw new UserNotFoundException("User couldn't be found by following id: " + id);
        }

    }

    private boolean doesUserExist(final Long id) {
        return userRepository.existsById(id);
    }

    private void changeActivateUser(final Long id, Boolean isActive) {
        User user = findUserById(id);

        User u = new User(user.getId(),
                user.getMail(),
                user.getFirstName(),
                user.getLastName(),
                user.getMiddleName(),
                false);
        userRepository.save(u);
    }

    private User findUserByMail(String mail) {
        return userRepository.findByMail(mail)
                .orElseThrow(() -> new UserNotFoundException("User couldn't be found by following mail: " + mail));
    }

    protected User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User couldn't be found by following id: " + id));
    }
}
