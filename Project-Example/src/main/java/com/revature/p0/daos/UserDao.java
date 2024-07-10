package com.revature.p0.daos;

import com.revature.p0.models.User;
import com.revature.p0.utils.ConnectionUtil;

import java.io.IOException;
import java.sql.*;

public class UserDao {
    Connection connection;

    public UserDao(Connection connection) throws SQLException, IOException, ClassNotFoundException {
        this.connection = connection;
    }

    public boolean checkUserExists(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, username);
        ResultSet results = pstmt.executeQuery();

        return results.next();
    }


    public User getUser(int id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, id);
        ResultSet results = pstmt.executeQuery();

        User user = new User();
        if(results.next()) {
            user.setFirstName(results.getString("first_name"));
            user.setLastName(results.getString("last_name"));
            user.setUsername(results.getString("username"));
        }

        return user;
    }

    public User getUserByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, username);
        ResultSet results = pstmt.executeQuery();

        User user = new User();
        if(results.next()) {
            user.setUserId(results.getInt("user_id"));
            user.setFirstName(results.getString("first_name"));
            user.setLastName(results.getString("last_name"));
            user.setUsername(results.getString("username"));
            user.setPassword(results.getString("password"));
        }

        return user;
    }

    /**
     * This method will save a User object, or if the object has a valid ID will update the existing row
     * @param user
     */
    public User saveUser(User user) throws SQLException {//create AND update
        if(user.getUserId() == null) {
            //save the new user
            String sql = "INSERT INTO users (first_name, last_name, username, password) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, user.getFirstName());
            pstmt.setString(2, user.getLastName());
            pstmt.setString(3, user.getUsername());
            pstmt.setString(4, user.getPassword());

            pstmt.executeUpdate();

            ResultSet keys = pstmt.getGeneratedKeys();

            if(keys.next()) {
                user.setUserId(keys.getInt(1));
            }

        } else {
            //update the existing user row
            String sql = "UPDATE users SET first_name = ?, last_name = ?, username = ?, password = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, user.getFirstName());
            pstmt.setString(2, user.getLastName());
            pstmt.setString(3, user.getUsername());
            pstmt.setString(4, user.getPassword());

            pstmt.executeUpdate();
        }

        return user;
    }

    public void deleteUser(User user) {
        //TODO: Implement delete, clear tasks for user as well
    }



}
