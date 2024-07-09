package com.revature.p0.controllers;

import com.revature.p0.dtos.AuthDto;
import com.revature.p0.exceptions.BadPasswordException;
import com.revature.p0.exceptions.NoSuchUserException;
import com.revature.p0.models.User;
import com.revature.p0.services.UserService;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.sql.SQLException;

public class UserController {
    UserService userService;
    Javalin api;

    public UserController(UserService userService, Javalin api) {
        this.api = api;
        this.userService = userService;

        //login test with auth DTO - now with method reference
        api.get("/login", this::login);

        //path param test - now with method references
        api.get("/user/{username}", this::getUserByUsername);

    }

    public User login(Context ctx) throws BadPasswordException, NoSuchUserException {
        ctx.status(200);
        AuthDto auth = ctx.bodyAsClass(AuthDto.class);
        User result = userService.authenticateUser(auth.getUsername(), auth.getPassword());
        ctx.json(result);
        ctx.header("Auth", result.getUsername());
        return result;
    }

    public User postNewUser(User user) throws SQLException {
        return userService.registerNewUser(user);
    }

    public User getUserByUsername(Context ctx) throws SQLException {
        return userService.getUserByUsername(ctx.pathParam("username"));
    }



}
