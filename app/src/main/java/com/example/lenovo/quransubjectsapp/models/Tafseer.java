package com.example.lenovo.quransubjectsapp.models;

public class Tafseer {
    int VerseID;

    public int getVerseID() {
        return VerseID;
    }

    public void setVerseID(int verseID) {
        VerseID = verseID;
    }

    public int getSuraID() {
        return SuraID;
    }

    public void setSuraID(int suraID) {
        SuraID = suraID;
    }

    public String getTafseerText() {
        return TafseerText;
    }

    public void setTafseerText(String tafseerText) {
        TafseerText = tafseerText;
    }

    int SuraID;
    String TafseerText;

}
