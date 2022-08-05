package com.example.database_test;

public class nianhui_info {
    private int id;
    private String name;
    private String size;

    public nianhui_info() {
    }

    public nianhui_info(int id, String name, String size) {
        this.name = name;
        this.size = size;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "nianhui_info{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", size='" + size + '\'' +
                '}';
    }
}
