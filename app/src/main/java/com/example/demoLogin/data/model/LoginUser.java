package com.example.demoLogin.data.model;

public class LoginUser {
    // string variables for our name and job
    private String name;
    private String job;

    public LoginUser(String name, String job) {
        this.name = name;
        this.job = job;
    }

    public LoginUser() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
