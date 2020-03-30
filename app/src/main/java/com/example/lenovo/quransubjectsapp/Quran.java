package com.example.lenovo.quransubjectsapp;

import com.orm.SugarRecord;

public class Quran extends SugarRecord {
  int VerseID,Page,NoOfSubjects;
    String Sura,SuraID,Verse,PureVerse,Subjects,Ayah_Number_Symbole;

    public String getAyah_Number_Symbole() {
        return Ayah_Number_Symbole;
    }

    public void setAyah_Number_Symbole(String ayah_Number_Symbole) {
        Ayah_Number_Symbole = ayah_Number_Symbole;
    }

    public Quran() {
    }



    public int getVerseID() {
        return VerseID;
    }

    public void setVerseID(int verseID) {
        VerseID = verseID;
    }

    public int getPage() {
        return Page;
    }

    public void setPage(int page) {
        Page = page;
    }

    public int getNoOfSubjects() {
        return NoOfSubjects;
    }

    public void setNoOfSubjects(int noOfSubjects) {
        NoOfSubjects = noOfSubjects;
    }

    public String getSura() {
        return Sura;
    }

    public void setSura(String sura) {
        Sura = sura;
    }

    public String getSuraID() {
        return SuraID;
    }

    public void setSuraID(String suraID) {
        SuraID = suraID;
    }

    public String getVerse() {
        return Verse;
    }

    public void setVerse(String verse) {
        Verse = verse;
    }

    public String getPureVerse() {
        return PureVerse;
    }

    public void setPureVerse(String pureVerse) {
        PureVerse = pureVerse;
    }

    public String getSubjects() {
        return Subjects;
    }

    public void setSubjects(String subjects) {
        Subjects = subjects;
    }

    public Quran( int noOfSubjects, String suraID, String pureVerse, int page, int verseID, String verse,String sura,  String subjects) {

        VerseID = verseID;
        Page = page;
        NoOfSubjects = noOfSubjects;
        Sura = sura;

        SuraID = suraID;
        Verse = verse;
        PureVerse = pureVerse;
        Subjects = subjects;
    }


}
