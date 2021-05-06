package com.example.dailytaskmanager;

public class Task {

    public String TaskID;
    public String TaskDescription;
    public String TaskDate;

    public Task(String taskID, String taskDescription, String taskDate) {
        TaskID = taskID;
        TaskDescription = taskDescription;
        TaskDate = taskDate;
    }
}
