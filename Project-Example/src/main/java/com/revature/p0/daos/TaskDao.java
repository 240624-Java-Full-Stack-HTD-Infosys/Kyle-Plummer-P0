package com.revature.p0.daos;

import com.revature.p0.models.Task;
import com.revature.p0.models.User;
import com.revature.p0.utils.ConnectionUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskDao {
    Connection connection;

    public TaskDao(Connection connection) throws SQLException, IOException, ClassNotFoundException {
        this.connection = ConnectionUtil.getConnection();
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
}
