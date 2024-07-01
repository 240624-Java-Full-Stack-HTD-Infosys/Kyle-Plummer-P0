package com.revature.p0.services;

import com.revature.p0.daos.TaskDao;

public class TaskService {
    TaskDao taskDao;

    public TaskService(TaskDao taskDao) {
        this.taskDao = taskDao;
    }
}
