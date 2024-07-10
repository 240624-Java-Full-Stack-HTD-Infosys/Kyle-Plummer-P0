package com.revature.p0.controllers;

import io.javalin.Javalin;
import io.javalin.http.Context;

public class ErrorController {

    public ErrorController(Javalin api) {
        /*
        Error handlers
         */
        api.error(500, this::genericServerErrorHandler);
    }



    /**
     *
     * @param ctx
     */
    public void genericServerErrorHandler(Context ctx) {
        ctx.result("An internal server error has occurred.");
    }
}
