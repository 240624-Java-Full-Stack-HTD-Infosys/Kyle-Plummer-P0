package com.revature.p0.controllers;

import com.revature.p0.services.TaskService;

public class TaskController {
    TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }
}
