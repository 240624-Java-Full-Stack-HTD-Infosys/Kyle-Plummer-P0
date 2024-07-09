package com.revature.p0.services;

import com.revature.p0.daos.UserDao;
import com.revature.p0.exceptions.BadPasswordException;
import com.revature.p0.exceptions.NoSuchUserException;
import com.revature.p0.models.User;

import java.sql.SQLException;

public class UserService {
    UserDao userDao;
    TaskService taskService;

    public UserService(UserDao userDao, TaskService taskService) {
        this.userDao = userDao;
        this.taskService = taskService;
    }

    public User registerNewUser(User user) throws SQLException {
        return userDao.saveUser(user);
    }

    public User authenticateUser(String username, String password) throws NoSuchUserException, BadPasswordException, SQLException {
        User result = userDao.getUserByUsername(username);

        if(result.getUserId() == null) {
            throw new NoSuchUserException("User not found");
        } else if(result.getPassword().equals(password)) {
            return result;
        } else {
            throw new BadPasswordException("Password mismatch!");
        }

    }

    public User getUserByUsername(String username) throws SQLException {
        User user = userDao.getUserByUsername(username);
        taskService.getTasksForUser(user);
        return user;
    }
}
