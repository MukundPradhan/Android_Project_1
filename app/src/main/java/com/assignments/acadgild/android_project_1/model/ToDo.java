package com.assignments.acadgild.android_project_1.model;

/**
 * Created by Mukund on 20-03-16.
 */
public class ToDo {
    private int id;
    private String title;
    private String description;
    private String date;
    private int status;

    public ToDo(int id,  String title,String description ,String date,  int status) {
        this.id=id;
        this.date = date;
        this.description = description;
        this.status = status;
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
