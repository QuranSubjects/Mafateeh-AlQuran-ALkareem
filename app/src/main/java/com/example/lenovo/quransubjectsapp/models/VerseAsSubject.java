package com.example.lenovo.quransubjectsapp.models;

public class VerseAsSubject {
    int ID,Master;
    String subject_type,Synonym,Subject;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getMaster() {
        return Master;
    }

    public void setMaster(int master) {
        Master = master;
    }

    public String getSubject_type() {
        return subject_type;
    }

    public void setSubject_type(String subject_type) {
        this.subject_type = subject_type;
    }

    public String getSynonym() {
        return Synonym;
    }

    public void setSynonym(String synonym) {
        Synonym = synonym;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }
}
