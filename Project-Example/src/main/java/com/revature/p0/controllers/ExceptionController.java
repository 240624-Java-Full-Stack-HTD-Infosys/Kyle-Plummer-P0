package com.revature.p0.controllers;

import com.revature.p0.exceptions.BadPasswordException;
import com.revature.p0.exceptions.NoSuchUserException;
import com.revature.p0.exceptions.UnauthorizedAccessException;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.sql.SQLException;

public class ExceptionController {

    public ExceptionController(Javalin api) {
        /*
        Exception handlers
         */
        api.exception(RuntimeException.class, this::runtimeExceptionHandler);
        api.exception(SQLException.class, this::sqlExceptionHandler);
        api.exception(UnauthorizedAccessException.class, this::unauthorizedAccessExceptionHandler);
        api.exception(BadPasswordException.class, this::badPasswordExceptionHandler);
        api.exception(NoSuchUserException.class, this::noSuchUserExceptionHandler);
    }


    /**
     *
     * @param e
     * @param ctx
     */
    public void runtimeExceptionHandler(RuntimeException e, Context ctx) {
        ctx.status(500);
    }

    /**
     *
     * @param e
     * @param ctx
     */
    public void sqlExceptionHandler(SQLException e, Context ctx) {
        ctx.status(500);
    }

    /**
     *
     * @param e
     * @param ctx
     */
    public void noSuchUserExceptionHandler(NoSuchUserException e, Context ctx) {
        ctx.status(400);
        ctx.result("That user does not exist.");
    }

    /**
     *
     * @param e
     * @param ctx
     */
    public void badPasswordExceptionHandler(BadPasswordException e, Context ctx) {
        ctx.status(401);
        ctx.result("Incorrect password.");
    }

    /**
     *
     * @param e
     * @param ctx
     */
    public void unauthorizedAccessExceptionHandler(UnauthorizedAccessException e, Context ctx) {
        ctx.status(401);
        ctx.result(e.getMessage());
    }
}
