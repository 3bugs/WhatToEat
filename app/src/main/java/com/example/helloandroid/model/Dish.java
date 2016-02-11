package com.example.helloandroid.model;

/**
 * Created by COM_00 on 10/2/2559.
 */
public class Dish {

    public final String name;
    public final String fileName;

    // constructor
    public Dish(String name, String fileName) {
        this.name = name;
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return name;
    }
}
