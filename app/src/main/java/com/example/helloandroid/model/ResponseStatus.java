package com.example.helloandroid.model;

/**
 * Created by Promlert on 2/11/2016.
 */
public class ResponseStatus {

    public final boolean success;
    public final String message;

    public ResponseStatus(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}