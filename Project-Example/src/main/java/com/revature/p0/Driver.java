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
import java.util.List;
import java.util.Map;


public class Driver {
    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
        Javalin app = Javalin.create().start(7777);
        User user = new User("Kyle", "plummer","username","password");

        //UserController userController = new UserController(new UserService(new UserDao(ConnectionUtil.getConnection())));



        //post new user test
//        app.post("/user",
//            (ctx) -> {
//                ctx.status(201);
//                ctx.json(userController.postNewUser(ctx.bodyAsClass(User.class)));
//            }
//        );

        //path param test
//        app.get("/user/{username}",
//            (ctx) -> {
//                ctx.status(200);
//                ctx.json(userController.getUserByUsername(ctx.pathParam("username")));
//                System.out.println("this is inside the function body");
//            }
//        );

        //path param test 2
//        app.get("/user/{username}/test2",
//            (ctx) -> {
//                ctx.status(200);
//                User result = userController.getUserByUsername(ctx.pathParam("username"));
//                ObjectMapper mapper = new ObjectMapper();
//                String jsonString = mapper.writeValueAsString(result);
//                System.out.println(jsonString);
//                ctx.header("Content-Type", "application/json; UTF-8;");
//                ctx.result(jsonString);
//            }
//        );

        //headers test
        app.get("/headers-test",
            (ctx)->{
                String contentType = ctx.header("Content-Type");
                Map<String, String> headers = ctx.headerMap();
                for(String key : headers.keySet()) {
                    System.out.println("[" + key + "]: " + headers.get(key));
                }
            }
        );


        //query params test
//        app.get("/query-params",
//            (ctx)->{
//                ctx.status(200);
//                String username = ctx.queryParam("username");
//                ctx.json(userController.getUserByUsername(username));
//            }
//        );

        app.get("/query-params-print",
                (ctx)->{
                    ctx.status(200);
                    Map<String, List<String>> queryParams = ctx.queryParamMap();
                    for(String key : queryParams.keySet()) {
                        System.out.println("[" + key + "]: " + queryParams.get(key));
                    }
                }
        );




    }
}
