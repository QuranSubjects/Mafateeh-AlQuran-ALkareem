package com.example.lenovo.quransubjectsapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class VersesOfSuras implements Parcelable {
    int suraId;
    String suraName;
    ArrayList<Verses> verses;

    protected VersesOfSuras(Parcel in) {
        suraId = in.readInt();
        suraName = in.readString();
        verses = in.createTypedArrayList(Verses.CREATOR);
    }

    public static final Creator<VersesOfSuras> CREATOR = new Creator<VersesOfSuras>() {
        @Override
        public VersesOfSuras createFromParcel(Parcel in) {
            return new VersesOfSuras(in);
        }

        @Override
        public VersesOfSuras[] newArray(int size) {
            return new VersesOfSuras[size];
        }
    };

    public int getSuraId() {
        return suraId;
    }

    public void setSuraId(int suraId) {
        this.suraId = suraId;
    }

    public String getSuraName() {
        return suraName;
    }

    public void setSuraName(String suraName) {
        this.suraName = suraName;
    }

    public ArrayList<Verses> getVerses() {
        return verses;
    }

    public void setVerses(ArrayList<Verses> verses) {
        this.verses = verses;
    }

    public VersesOfSuras(int suraId, String suraName, ArrayList<Verses> verses) {

        this.suraId = suraId;
        this.suraName = suraName;
        this.verses = verses;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(suraId);
        parcel.writeString(suraName);
        parcel.writeTypedList(verses);
    }
}
