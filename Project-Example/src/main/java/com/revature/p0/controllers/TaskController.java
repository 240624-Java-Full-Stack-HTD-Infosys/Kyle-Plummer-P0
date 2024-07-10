package com.revature.p0.controllers;

import com.revature.p0.exceptions.NoSuchUserException;
import com.revature.p0.exceptions.UnauthorizedAccessException;
import com.revature.p0.models.Task;
import com.revature.p0.models.User;
import com.revature.p0.services.TaskService;
import com.revature.p0.services.UserService;
import com.revature.p0.utils.CookieUtil;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;


public class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskService taskService;
    private final UserService userService;
    private final Javalin api;

    public TaskController(TaskService taskService, UserService userService, Javalin api) {
        this.taskService = taskService;
        this.userService = userService;
        this.api = api;

        api.post("/tasks", this::postNewTask);
        api.put("/tasks", this::updateTask);
        api.get("/tasks/{username}", this::getTasksForUser);


    }

    public void postNewTask(Context ctx) throws SQLException, UnauthorizedAccessException, NoSuchUserException {
        String username = ctx.cookie("Auth");
        if(!CookieUtil.validateAuthCookie(username)) {
            throw new UnauthorizedAccessException("user is not authorized.");
        }

        User user = userService.getUserByUsername(username);
        Task newTask = ctx.bodyAsClass(Task.class);
        newTask.setComplete(false);//all new tasks can be assumed to be incomplete
        newTask.setUser(user);
        taskService.saveTask(newTask);
        ctx.json(newTask);
        ctx.status(201);

    }

    public void updateTask(Context ctx) throws UnauthorizedAccessException, SQLException, NoSuchUserException {
        String username = ctx.cookie("Auth");
        if(!CookieUtil.validateAuthCookie(username)) {
            throw new UnauthorizedAccessException("user is not authorized");
        }
        User user = userService.getUserByUsername(username);
        Task task = ctx.bodyAsClass(Task.class);
        user.getTasks().add(task);
        task.setUser(user);
    }

    public void getTasksForUser(Context ctx) throws UnauthorizedAccessException, SQLException {
        String username = ctx.cookie("Auth");
        if(!CookieUtil.validateAuthCookie(username)) {
            throw new UnauthorizedAccessException("user is not authorized");
        }
        List<Task> taskList = this.taskService.getTasksForUser(ctx.bodyAsClass(User.class));
        ctx.json(taskList);
        ctx.status(200);
    }



}
