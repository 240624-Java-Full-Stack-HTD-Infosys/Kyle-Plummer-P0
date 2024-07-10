package com.revature.p0.controllers;

import com.revature.p0.dtos.AuthDto;
import com.revature.p0.exceptions.BadPasswordException;
import com.revature.p0.exceptions.NoSuchUserException;
import com.revature.p0.exceptions.UnauthorizedAccessException;
import com.revature.p0.exceptions.UsernameTakenException;
import com.revature.p0.models.User;
import com.revature.p0.services.UserService;
import com.revature.p0.utils.CookieUtil;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final Javalin api;

    public UserController(UserService userService, Javalin api) {
        this.api = api;
        this.userService = userService;

        /*
        request handlers
         */
        api.post("/register", this::register);
        api.post("/login", this::login);
        api.get("/user/{username}", this::getUserByUsername);
        api.put("/user", this::updateUser);
        api.delete("user", this::deleteUser);
    }

    /**
     *
     * @param ctx
     * @throws SQLException
     * @throws UsernameTakenException
     */
    public void register(Context ctx) throws SQLException, UsernameTakenException {
        User newUser = ctx.bodyAsClass(User.class);
        ctx.status(201);
        ctx.json(userService.registerNewUser(newUser));
    }

    /**
     *
     * @param ctx
     * @throws BadPasswordException
     * @throws NoSuchUserException
     */
    public void login(Context ctx) throws BadPasswordException, NoSuchUserException, SQLException {
            AuthDto auth = ctx.bodyAsClass(AuthDto.class);
            User user = userService.authenticateUser(auth.getUsername(), auth.getPassword());
            ctx.cookie(CookieUtil.createAuthCookie(user));
            ctx.status(200);
    }

    /**
     *
     * @param ctx
     * @return
     * @throws SQLException
     * @throws NoSuchUserException
     */
    public User getUserByUsername(Context ctx) throws SQLException, NoSuchUserException, UnauthorizedAccessException {
        if(CookieUtil.validateAuthCookie(ctx.cookie("Auth"))) {
            return userService.getUserByUsername(ctx.pathParam("username"));
        } else {
            throw new UnauthorizedAccessException("User is not authorized.");
        }
    }

    /**
     *
     * @param ctx
     * @throws UnauthorizedAccessException
     * @throws SQLException
     */
    public void updateUser(Context ctx) throws UnauthorizedAccessException, SQLException {
        if(CookieUtil.validateAuthCookie(ctx.cookie("Auth"))) {
            userService.updateUser(ctx.bodyAsClass(User.class));
        } else {
            throw new UnauthorizedAccessException("User is not authorized.");
        }
    }

    public void deleteUser(Context ctx) throws UnauthorizedAccessException {
        //TODO: will need to delete all tasks before we can delete user
        if(CookieUtil.validateAuthCookie(ctx.cookie("Auth"))) {
            this.userService.deleteUser(ctx.bodyAsClass(User.class));
        } else {
            throw new UnauthorizedAccessException("User is not authorized.");
        }
    }




}
