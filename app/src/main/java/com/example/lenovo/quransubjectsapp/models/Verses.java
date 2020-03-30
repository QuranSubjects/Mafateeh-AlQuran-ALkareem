package com.example.lenovo.quransubjectsapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Verses implements Parcelable {
    int VerseID;
    String Verse;
    int VersePage;
    int SuraID;
    String Sura;
    int Page;

    protected Verses(Parcel in) {
        VerseID = in.readInt();
        Verse = in.readString();
        VersePage = in.readInt();
        SuraID = in.readInt();
        Sura = in.readString();
        Page = in.readInt();
    }

    public static final Creator<Verses> CREATOR = new Creator<Verses>() {
        @Override
        public Verses createFromParcel(Parcel in) {
            return new Verses(in);
        }

        @Override
        public Verses[] newArray(int size) {
            return new Verses[size];
        }
    };

    public int getPage() {
        return Page;
    }

    public void setPage(int page) {
        Page = page;
    }

    public Verses(int verseId, String verse, int versePage, int suraId, String sura) {
        VerseID = verseId;
        Verse = verse;
        VersePage = versePage;
        SuraID = suraId;
        Sura = sura;
    }

    public int getSuraID() {

        return SuraID;
    }

    public void setSuraID(int suraID) {
        SuraID = suraID;
    }

    public String getSura() {
        return Sura;
    }

    public void setSura(String sura) {
        Sura = sura;
    }

    public Verses(int verseId, String verse, int versePage) {
        VerseID = verseId;
        Verse = verse;
        VersePage = versePage;
    }

    public int getVerseID() {

        return VerseID;
    }

    public void setVerseID(int verseID) {
        VerseID = verseID;
    }

    public String getVerse() {
        return Verse;
    }

    public void setVerse(String verse) {
        Verse = verse;
    }

    public int getVersePage() {
        return VersePage;
    }

    public void setVersePage(int versePage) {
        VersePage = versePage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(VerseID);
        parcel.writeString(Verse);
        parcel.writeInt(VersePage);
        parcel.writeInt(SuraID);
        parcel.writeString(Sura);
        parcel.writeInt(Page);
    }
}
