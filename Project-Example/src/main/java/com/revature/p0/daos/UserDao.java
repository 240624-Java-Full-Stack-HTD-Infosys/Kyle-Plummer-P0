package com.revature.p0.daos;

import com.revature.p0.models.User;
import com.revature.p0.utils.ConnectionUtil;

import java.io.IOException;
import java.sql.*;

public class UserDao {
    Connection connection;

    public UserDao(Connection connection) throws SQLException, IOException {
        this.connection = ConnectionUtil.getConnection();
    }

    public void truncateTable() throws SQLException {
        String sql = "TRUNCATE TABLE users";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.execute();
    }

    public void dropTable() throws SQLException {
        String sql = "DROP TABLE IF EXISTS users";
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(sql);
    }

    public void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS users (user_id SERIAL PRIMARY KEY, first_name VARCHAR(40), last_name CHAR(40), username VARCHAR(40) UNIQUE, password VARCHAR(40) NOT NULL);";
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(sql);
    }

    public void populateTable() throws SQLException {
        String sql = "INSERT INTO users (first_name, last_name, username, password) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, "Kyle");
        pstmt.setString(2, "Plummer");
        pstmt.setString(3, "kplummer");
        pstmt.setString(4, "pass123");
        pstmt.executeUpdate();
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

    public User getUser(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, username);
        ResultSet results = pstmt.executeQuery();

        User user = new User();
        if(results.next()) {
            user.setFirstName(results.getString("first_name"));
            user.setLastName(results.getString("last_name"));
            user.setUsername(results.getString("username"));
        }

        return user;
    }

    /**
     * This method will save a User object, or if the object has a valid ID will update the existing row
     * @param user
     */
    public void saveUser(User user) {
        if(user.get)
    }
}
