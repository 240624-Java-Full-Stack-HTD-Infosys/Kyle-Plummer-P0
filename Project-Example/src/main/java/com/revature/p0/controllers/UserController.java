package com.revature.p0.controllers;

import com.revature.p0.dtos.AuthDto;
import com.revature.p0.exceptions.BadPasswordException;
import com.revature.p0.exceptions.NoSuchUserException;
import com.revature.p0.models.User;
import com.revature.p0.services.UserService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.Cookie;

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

    public void login(Context ctx) throws BadPasswordException, NoSuchUserException {
        AuthDto auth = ctx.bodyAsClass(AuthDto.class);
        User result = null;
        try {
            result = userService.authenticateUser(auth.getUsername(), auth.getPassword());
            ctx.json(result);
            Cookie cookie = new Cookie("Auth", result.getUsername());
            ctx.cookie(cookie);
            ctx.status(200);
        } catch (SQLException e) {
            e.printStackTrace();//not really a good idea in prod, we should log this instead
            //TODO: implement logging
            throw new RuntimeException(e);
        } catch (NoSuchUserException e) {
            ctx.status(418);//we are making both the server and client, so we can use any status codes we want
            ctx.result("No such user");
        } catch (BadPasswordException e) {
            ctx.status(401);
            ctx.result("Bad password");
        }
    }

    public User postNewUser(User user) throws SQLException {
        return userService.registerNewUser(user);
    }

    public User getUserByUsername(Context ctx) throws SQLException {
        return userService.getUserByUsername(ctx.pathParam("username"));
    }



}
