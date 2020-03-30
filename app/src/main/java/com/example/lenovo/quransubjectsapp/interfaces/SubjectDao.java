package com.example.lenovo.quransubjectsapp.interfaces;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.lenovo.quransubjectsapp.models.Subjects;

import java.util.List;

@Dao
public interface SubjectDao  {
    @Insert
    void insert(Subjects subject);

    @Query("DELETE FROM subjects_table")
    void deleteAll();


    @Query("SELECT * from subjects_table ORDER BY serverId Desc")
    List<Subjects> getAllSubjects();

    @Query("SELECT COUNT(*) FROM subjects_table WHERE ID =:subjectId")
    int getNumberOfExistance(String subjectId);

    @Query("SELECT * FROM subjects_table WHERE ID =:subjectId")
    Subjects get(String subjectId);

    @Delete
    void delete(Subjects s);
}
