package com.revature.p0.services;

import com.revature.p0.daos.TaskDao;
import com.revature.p0.models.Task;
import com.revature.p0.models.User;

import java.sql.SQLException;
import java.util.List;

public class TaskService {
    TaskDao taskDao;

    public TaskService(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public List<Task> getTasksForUser(User user) throws SQLException {
        return taskDao.getTasksForUser(user);
    }

    public Task saveTask(Task task) throws SQLException {
        return taskDao.persistNewTask(task);
    }
}
