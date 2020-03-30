package com.example.lenovo.quransubjectsapp.models;

import com.example.lenovo.quransubjectsapp.Quran;

import java.util.ArrayList;

public class QuranPage {
    public QuranPage() {
        this.versesOfPage = "";
    }

    String versesOfPage="";

    public String getversesOfPage() {
        if(versesOfPage==null)
            versesOfPage = "";
        return versesOfPage;
    }

    public void setPage(String versesOfPage) {
        this.versesOfPage = versesOfPage;
    }
}
