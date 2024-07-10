package com.revature.p0.utils;

import com.revature.p0.controllers.TaskController;
import com.revature.p0.controllers.UserController;
import com.revature.p0.daos.TaskDao;
import com.revature.p0.daos.UserDao;
import com.revature.p0.services.TaskService;
import com.revature.p0.services.UserService;
import io.javalin.Javalin;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ServerUtil {
    private static ServerUtil serverUtil;

    private ServerUtil() {
    }

    public static ServerUtil getServerUtil() {
        if(serverUtil == null) {
            serverUtil = new ServerUtil();
        }
        return serverUtil;
    }

    public Javalin initialize(int port) throws SQLException, IOException, ClassNotFoundException {
        Javalin api = Javalin.create().start(port);
        Connection conn = ConnectionUtil.getConnection();
        UserDao userDao = new UserDao(conn);
        TaskDao taskDao = new TaskDao(conn);
        TaskService taskService = new TaskService(taskDao);
        UserService userService = new UserService(userDao, taskService);
        UserController userController = new UserController(userService, api);
        TaskController taskController = new TaskController(taskService, userService, api);

        return api;
    }

    public static void executeSqlScript(String resourceName) throws IOException, SQLException, ClassNotFoundException {
        InputStream inputStream = ServerUtil.class.getClassLoader().getResourceAsStream(resourceName);
        InputStreamReader reader = new InputStreamReader(inputStream);
        BufferedReader buff = new BufferedReader(reader);
        StringBuilder sql = new StringBuilder();
        while(buff.ready()) {
            sql.append((char)buff.read());
        }

        System.out.println(sql);
        Connection conn = ConnectionUtil.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql.toString());
        pstmt.executeUpdate();



    }
//
//
//    public void truncateTable() throws SQLException {
//        String sql = "TRUNCATE TABLE users";
//        PreparedStatement pstmt = connection.prepareStatement(sql);
//        pstmt.execute();
//    }
//
//    public void dropTable() throws SQLException {
//        String sql = "DROP TABLE IF EXISTS users";
//        Statement stmt = connection.createStatement();
//        stmt.executeUpdate(sql);
//    }
//
//    public void createTable() throws SQLException {
//        String sql = "CREATE TABLE IF NOT EXISTS users (user_id SERIAL PRIMARY KEY, first_name VARCHAR(40), last_name CHAR(40), username VARCHAR(40) UNIQUE, password VARCHAR(40) NOT NULL);";
//        Statement stmt = connection.createStatement();
//        stmt.executeUpdate(sql);
//    }
//
//    public void populateTable() throws SQLException {
//        String sql = "INSERT INTO users (first_name, last_name, username, password) VALUES (?, ?, ?, ?)";
//        PreparedStatement pstmt = connection.prepareStatement(sql);
//
//        pstmt.setString(1, "Kyle");
//        pstmt.setString(2, "Plummer");
//        pstmt.setString(3, "kplummer");
//        pstmt.setString(4, "pass123");
//        pstmt.executeUpdate();
//    }

}
