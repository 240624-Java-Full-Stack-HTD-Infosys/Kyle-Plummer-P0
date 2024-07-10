package com.revature.p0.utils;

import com.revature.p0.exceptions.NoSuchUserException;
import com.revature.p0.models.User;
import com.revature.p0.services.UserService;
import io.javalin.http.Cookie;

import java.sql.SQLException;

/*
Might be leaning on static/singletons a little too much in this project, mostly because the dependency injection is
getting out of hand. When we get to Spring Autowiring will take care of everything for us.
 */
public class CookieUtil {
    private static final UserService userService = (UserService)DependencyManager.getDependency("UserService");

    public static Cookie createAuthCookie(User user) {
        return new Cookie("Auth", user.getUsername());
    }

    public static boolean validateAuthCookie(String auth) {
        try {
            return userService.checkUserExists(auth);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
