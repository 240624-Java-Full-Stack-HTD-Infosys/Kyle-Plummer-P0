package com.revature.p0;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.p0.controllers.UserController;
import com.revature.p0.controllers.dtos.AuthDto;
import com.revature.p0.daos.UserDao;
import com.revature.p0.models.User;
import com.revature.p0.services.UserService;
import com.revature.p0.utils.ConnectionUtil;
import io.javalin.Javalin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;


public class Driver {
    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
        Javalin app = Javalin.create().start(7777);
        User user = new User("Kyle", "plummer","username","password");

        UserController userController = new UserController(new UserService(new UserDao(ConnectionUtil.getConnection())));

        app.get("/user",
            (ctx) -> {
                ctx.json(user);
                ctx.status(200);
                System.out.println("this is inside the function body");
            }
        );

        app.post("/user",
            (ctx) -> {
                ctx.status(201);
                ctx.json(userController.postNewUser(ctx.bodyAsClass(User.class)));
            }
        );

        app.get("/user/{username}",
            (ctx) -> {
                ctx.status(200);
                ctx.json(userController.getUserByUsername(ctx.pathParam("username")));
                System.out.println("this is inside the function body");
            }
        );

        app.get("/user/{username}/test2",
            (ctx) -> {
                ctx.status(200);
                User result = userController.getUserByUsername(ctx.pathParam("username"));
                ObjectMapper mapper = new ObjectMapper();
                String jsonString = mapper.writeValueAsString(result);
                System.out.println(jsonString);
                ctx.header("Content-Type", "application/json; UTF-8;");
                ctx.result(jsonString);
            }
        );

        app.get("/headers-test",
            (ctx)->{
                String contentType = ctx.header("Content-Type");
                Map<String, String> headers = ctx.headerMap();
                for(String key : headers.keySet()) {
                    System.out.println("[" + key + "]: " + headers.get(key));
                }
            }
        );

        app.get("/login",
            (ctx)->{
                ctx.status(200);
                ctx.json(userController.login(ctx.bodyAsClass(AuthDto.class)));
            }
        );




    }
}
