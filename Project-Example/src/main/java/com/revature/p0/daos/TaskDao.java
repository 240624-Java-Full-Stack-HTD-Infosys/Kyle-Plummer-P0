package com.revature.p0.daos;

import com.revature.p0.models.Task;
import com.revature.p0.models.User;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDao {
    Connection connection;

    public TaskDao(Connection connection) throws SQLException, IOException, ClassNotFoundException {
        this.connection = connection;
    }

    public List<Task> getTasksForUser(User user) throws SQLException {
        String sql = "SELECT * FROM tasks WHERE user_id = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, user.getUserId());
        ResultSet rs = pstmt.executeQuery();

        List<Task> taskList = new ArrayList<>();
        while(rs.next()) {
            Task task = new Task(rs.getInt("task_id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getBoolean("completed"),
                    user
                    );
            taskList.add(task);
        }
        user.setTasks(taskList);
        return taskList;
    }

    public Task persistNewTask(Task task) throws SQLException {
        String sql = "INSERT INTO tasks (title, description, completed, user_id) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, task.getTitle());
        pstmt.setString(2, task.getDescription());
        pstmt.setBoolean(3, task.isComplete());
        pstmt.setInt(4, task.getUser().getUserId());
        pstmt.executeUpdate();

        ResultSet rs = pstmt.getGeneratedKeys();
        if(rs.next()) {
            task.setTaskId(rs.getInt(1));
        }

        return task;
    }

    public void updateTask(Task task) throws SQLException {
        String sql = "UPDATE tasks SET title = ?, description = ?, complete = ? WHERE task_id = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, task.getTitle());
        pstmt.setString(2, task.getDescription());
        pstmt.setBoolean(3, task.isComplete());
        pstmt.setInt(4, task.getTaskId());

        pstmt.executeUpdate();
    }


}
