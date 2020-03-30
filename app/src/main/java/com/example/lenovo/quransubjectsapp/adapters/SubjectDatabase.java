package com.example.lenovo.quransubjectsapp.adapters;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.lenovo.quransubjectsapp.interfaces.SubjectDao;
import com.example.lenovo.quransubjectsapp.models.Subjects;

@Database(entities = {Subjects.class}, version = 1,exportSchema = false)
public abstract class SubjectDatabase extends RoomDatabase {

    public abstract SubjectDao subjectDao();

    private static volatile SubjectDatabase INSTANCE;

    public static SubjectDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (SubjectDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            SubjectDatabase.class, "word_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
