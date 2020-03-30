package com.example.lenovo.quransubjectsapp.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

@Entity(tableName = "subjects_table")
public class Subjects implements Parcelable {


    @PrimaryKey(autoGenerate = true)
    @NonNull
    int serverId;
    String ID;
    String verseId;
    String suraId;
    int NoOfVerses;

    public int getNoOfVerses() {
        return NoOfVerses;
    }

    public void setNoOfVerses(int noOfVerses) {
        NoOfVerses = noOfVerses;
    }

    String Subject;
    String subject_type;
    String Master;


    protected Subjects(Parcel in) {
        serverId = in.readInt();
        ID = in.readString();
        verseId = in.readString();
        suraId = in.readString();
        Subject = in.readString();
        subject_type = in.readString();
        Master = in.readString();
    }

    public static final Creator<Subjects> CREATOR = new Creator<Subjects>() {
        @Override
        public Subjects createFromParcel(Parcel in) {
            return new Subjects(in);
        }

        @Override
        public Subjects[] newArray(int size) {
            return new Subjects[size];
        }
    };

    public String getMaster() {
        return Master;
    }

    public void setMaster(String master) {
        Master = master;
    }

    public String getSubject_type() {
        return subject_type;
    }

    public void setSubject_type(String subject_type) {
        this.subject_type = subject_type;
    }

    public Subjects(String name) {
        this.Subject = name;
    }

    public Subjects(@NonNull int serverId, String ID, String verseId, String suraId, String subject, String subject_type, String master) {
        this.serverId = serverId;
        this.ID = ID;
        this.verseId = verseId;
        this.suraId = suraId;
        Subject = subject;
        this.subject_type = subject_type;
        Master = master;
    }

    @NonNull
    public int getServerId() {
        return serverId;

    }

    public void setServerId(@NonNull int serverId) {
        this.serverId = serverId;
    }

    public Subjects(String ID, String subject) {
        this.ID = ID;
        Subject = subject;
    }

    public Subjects() {
    }

    ;

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String name) {
        this.Subject = name;
    }


    public String getID() {
        return ID;
    }

    public void setID(String id) {
        this.ID = id;
    }

    public String getVerseId() {
        return verseId;
    }

    public void setVerseId(String verseId) {
        this.verseId = verseId;
    }

    public String getSuraId() {
        return suraId;
    }

    public void setSuraId(String suraId) {
        this.suraId = suraId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this.getID() == ((Subjects) obj).getID())
            return true;
        return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(serverId);
        parcel.writeString(ID);
        parcel.writeString(verseId);
        parcel.writeString(suraId);
        parcel.writeString(Subject);
        parcel.writeString(subject_type);
        parcel.writeString(Master);
    }
}
