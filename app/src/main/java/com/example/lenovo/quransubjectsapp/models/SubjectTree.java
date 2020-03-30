package com.example.lenovo.quransubjectsapp.models;

import com.google.gson.JsonElement;

public class SubjectTree {
    JsonElement subject,titles,subtitles;

    public JsonElement getSubject() {
        return subject;
    }

    public void setSubject(JsonElement subject) {
        this.subject = subject;
    }

    public JsonElement getTitles() {
        return titles;
    }

    public void setTitles(JsonElement titles) {
        this.titles = titles;
    }

    public JsonElement getSubtitles() {
        return subtitles;
    }

    public void setSubtitles(JsonElement subtitles) {
        this.subtitles = subtitles;
    }
}
