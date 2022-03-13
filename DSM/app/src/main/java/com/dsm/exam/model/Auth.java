package com.dsm.exam.model;

public class Auth {
    private int id;
    private String name;
    private String email;
    private String password;

    public Auth(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
