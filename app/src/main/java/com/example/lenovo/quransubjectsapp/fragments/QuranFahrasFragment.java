package com.example.lenovo.quransubjectsapp.fragments;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenovo.quransubjectsapp.R;
import com.example.lenovo.quransubjectsapp.adapters.FahrasRecyclerAdapter;

public class QuranFahrasFragment extends Fragment {
   RecyclerView fahrasRecyclerView;
    public QuranFahrasFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.quran_first_page_fragment, container, false);
        fahrasRecyclerView = rootView.findViewById(R.id.fahras_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        fahrasRecyclerView.setLayoutManager(layoutManager);
        fahrasRecyclerView.setItemAnimator(new DefaultItemAnimator());
        fahrasRecyclerView.setAdapter(new FahrasRecyclerAdapter(getContext()));
        return rootView;
    }

}