package com.example.lenovo.quransubjectsapp.adapters;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.lenovo.quransubjectsapp.MainActivity;
import com.example.lenovo.quransubjectsapp.fragments.QuranFahrasFragment;
import com.example.lenovo.quransubjectsapp.fragments.QuranPageFragment;

import java.util.ArrayList;
import java.util.List;

public class QuranAdapter extends FragmentStatePagerAdapter {
    QuranPageFragment q;
    private final List<Fragment> mFragmentList = new ArrayList<>();

    public QuranAdapter(FragmentManager fm) {
        super(fm);
    }

//    @Override
//    public Fragment getItem(int position) {
//        if (position == 0)
//            return new QuranFahrasFragment();
//
//        Bundle b = new Bundle();
//        b.putString("text",MainActivity.quranPages[position-1]);
//        b.putString("page",position+"");
//
//        q=new QuranPageFragment();
//        q.setArguments(b);
////        b.clear();
//        return q;
//    }
//
//    @Override
//    public int getCount() {
//        return 605;
//    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return new QuranFahrasFragment();
        Bundle b = new Bundle();
        b.putString("text", MainActivity.quranPages[position - 1]);
        b.putString("page", position + "");
        mFragmentList.get(position).setArguments(b);
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment) {
        mFragmentList.add(fragment);
    }

    @Override
    public Parcelable saveState() {
        Bundle bundle = (Bundle) super.saveState();
        if (bundle != null) {
            // Never maintain any states from the base class, just null it out
            bundle.putParcelableArray("states", null);
        } else {
            // do nothing
        }
        return bundle;
    }
}