package com.vitalgroundz.todolist.data;

/**
 * Created by jrperiod on 5/17/16.
 */
public class Task {

    public static final String TASKS_ROUTE = "tasks/";

    private String taskTitle;
    private String task;

    public Task() {}

    public Task(String task, String taskTitle) {
        this.task = task;
        this.taskTitle = taskTitle;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }



}


