package com.revature.p0.controllers;

import com.revature.p0.controllers.dtos.AuthDto;
import com.revature.p0.daos.UserDao;
import com.revature.p0.exceptions.BadPasswordException;
import com.revature.p0.exceptions.NoSuchUserException;
import com.revature.p0.models.User;
import com.revature.p0.services.UserService;

import java.sql.SQLException;

public class UserController {
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public User postNewUser(User user) throws SQLException {
        return userService.registerNewUser(user);
    }

    public User getUserByUsername(String username) throws SQLException {
        return userService.getUserByUsername(username);
    }

    public User login(AuthDto authDto) throws BadPasswordException, NoSuchUserException {
        return userService.authenticateUser(authDto.getUsername(), authDto.getPassword());
    }
}
