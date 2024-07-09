package com.revature.p0.controllers;

import com.revature.p0.models.Task;
import com.revature.p0.models.User;
import com.revature.p0.services.TaskService;
import com.revature.p0.services.UserService;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.sql.SQLException;


public class TaskController {
    TaskService taskService;
    UserService userService;
    Javalin api;

    public TaskController(TaskService taskService, UserService userService, Javalin api) {
        this.taskService = taskService;
        this.userService = userService;
        this.api = api;

        api.post("/tasks", this::createNewTaskForUser);

    }

/*
{
    "title": "value",
    "description": "value"
}
 */


    public void createNewTaskForUser(Context ctx) throws SQLException {
        String authUsername = ctx.cookie("Auth");//We will assume all cookies are untouched, and valid. We aren't very secure here, but this is okay for P0
        if(authUsername == null) {
            //this means cookie wasn't present, so requester is unauthenticated
            ctx.status(403);
            ctx.result("No Bearer token, unable to access resource as unauthenticated user");
        } else {
            User currentUser = userService.getUserByUsername(authUsername);
            Task newTask = ctx.bodyAsClass(Task.class);
            newTask.setComplete(false);//all new tasks can be assumed to be incomplete
            newTask.setUser(currentUser);
            taskService.saveTask(newTask);
            ctx.json(newTask);
            ctx.status(201);
        }


    }
}
