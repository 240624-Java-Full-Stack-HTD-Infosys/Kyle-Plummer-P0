package com.revature.p0.utils;

import com.revature.p0.controllers.ErrorController;
import com.revature.p0.controllers.ExceptionController;
import com.revature.p0.controllers.TaskController;
import com.revature.p0.controllers.UserController;
import com.revature.p0.daos.TaskDao;
import com.revature.p0.daos.UserDao;
import com.revature.p0.services.TaskService;
import com.revature.p0.services.UserService;
import io.javalin.Javalin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;


public class ServerUtil {

    public static Javalin initialize(int port) throws ClassNotFoundException, SQLException, IOException {
        Javalin api = Javalin.create().start(port);
        Connection conn = DatabaseUtil.getConnection();

        UserDao userDao = new UserDao(conn);
        DependencyManager.addDependency("UserDao", userDao);

        TaskDao taskDao = new TaskDao(conn);
        DependencyManager.addDependency("TaskDao", taskDao);

        TaskService taskService = new TaskService(taskDao);
        DependencyManager.addDependency("TaskService", taskService);

        UserService userService = new UserService(userDao, taskService);
        DependencyManager.addDependency("UserService", userService);

        UserController userController = new UserController(userService, api);
        DependencyManager.addDependency("UserController", userController);

        TaskController taskController = new TaskController(taskService, userService, api);
        DependencyManager.addDependency("TaskController", taskController);

        ExceptionController exceptionController = new ExceptionController(api);
        DependencyManager.addDependency("ExceptionController", exceptionController);

        ErrorController errorController = new ErrorController(api);
        DependencyManager.addDependency("ErrorController", errorController);

        return api;
    }


}
