package com.revature.p0.controllers;

import com.revature.p0.models.Task;
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


    public Task createNewTaskForUser(Context ctx) throws SQLException {
        String authUsername = ctx.header("Auth");
        Task newTask = ctx.bodyAsClass(Task.class);
        newTask.setUser(userService.getUserByUsername(authUsername));
        taskService.saveTask(newTask);
        ctx.json(newTask);
        ctx.status(201);
        return newTask;//totally unnecessary
    }
}
