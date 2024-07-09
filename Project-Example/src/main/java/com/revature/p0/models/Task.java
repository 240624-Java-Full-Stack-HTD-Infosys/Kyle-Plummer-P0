package com.revature.p0.models;


import com.fasterxml.jackson.annotation.JsonBackReference;

public class Task {
    private Integer taskId;
    private String title;
    private String description;
    private boolean complete;

    @JsonBackReference
    private User user;

    public Task(Integer taskId, String title, String description, boolean complete, User user) {
        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.complete = complete;
        this.user = user;
    }

    public Task(String title, String description, boolean complete, User user) {
        this.title = title;
        this.description = description;
        this.complete = complete;
        this.user = user;
    }

    public Task(String title, String description, boolean complete) {
        this.title = title;
        this.description = description;
        this.complete = complete;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Task() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
