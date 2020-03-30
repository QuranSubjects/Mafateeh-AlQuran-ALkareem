package com.example.lenovo.quransubjectsapp.models;

import com.google.gson.JsonElement;

import java.util.ArrayList;

public class SubjectsSearch {
    String status,message;
    JsonElement data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JsonElement getData() {
        return data;
    }

    public void setData(JsonElement subjectsList) {
        this.data = subjectsList;
    }
}
