package com.dsm.exam.model;

public class Employee {
    private final int id;
    private final String name;
    private final String role;
    private final int hours;

    public Employee(int id, String name, String role, int hours) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.hours = hours;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getHours() {
        return hours;
    }

    public String getRole() {
        return role;
    }
}
