package com.dsm.exam.model;

public class MenuOption {
    private final int id;
    private final String title;
    private final String name;

    public MenuOption(int id, String title, String name) {
        this.id = id;
        this.title = title;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getName() {
        return name;
    }
}
