package com.revature.p0.services;

import com.revature.p0.daos.UserDao;
import com.revature.p0.exceptions.BadPasswordException;
import com.revature.p0.exceptions.NoSuchUserException;
import com.revature.p0.models.User;

import java.sql.SQLException;

public class UserService {
    UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User registerNewUser(User user) throws SQLException {
        return userDao.saveUser(user);
    }

    public User authenticateUser(String username, String password) throws NoSuchUserException, BadPasswordException {
        User result;
        try{
            result =  userDao.getUserByUsername(username);
        } catch (SQLException e) {
            throw new NoSuchUserException("User not found");
        }

        if(result.getPassword().equals(password)) {
            return result;
        } else {
            throw new BadPasswordException("Password mismatch!");
        }

    }

    public User getUserByUsername(String username) throws SQLException {
        return userDao.getUserByUsername(username);
    }
}
