package com.example.lenovo.quransubjectsapp.models;

public class SearchedSubject {
    String searched_subject_name,searched_subject_id,searched_Type;

    public SearchedSubject( String searched_subject_id,String searched_subject_name,String searched_Type) {
        this.searched_subject_name = searched_subject_name;
        this.searched_subject_id = searched_subject_id;
        this.searched_Type = searched_Type;
    }

    public String getSearched_subject_name() {
        return searched_subject_name;
    }

    public void setSearched_subject_name(String searched_subject_name) {
        this.searched_subject_name = searched_subject_name;
    }

    public String getSearched_subject_id() {
        return searched_subject_id;
    }

    public void setSearched_subject_id(String searched_subject_id) {
        this.searched_subject_id = searched_subject_id;
    }

    public String getSearched_Type() {
        return searched_Type;
    }

    public void setSearched_Type(String searched_Type) {
        this.searched_Type = searched_Type;
    }
}
