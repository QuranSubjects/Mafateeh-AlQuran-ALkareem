package com.example.lenovo.quransubjectsapp.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.quransubjectsapp.R;
import com.example.lenovo.quransubjectsapp.VersesActivity;
import com.example.lenovo.quransubjectsapp.adapters.SubjectTreeAdapter;
import com.example.lenovo.quransubjectsapp.models.SubjectTree;
import com.example.lenovo.quransubjectsapp.models.Subjects;

import java.util.ArrayList;

public class DrawerTreeFragment extends Fragment {
    RecyclerView fragmentRecyclerView;
    ArrayList<Subjects> subjects;
    SubjectTreeAdapter treeAdapter;
    TextView noDataTreeText;

    public DrawerTreeFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.drawer_fragment, container, false);
        fragmentRecyclerView = rootView.findViewById(R.id.tree_recycler_view);
        noDataTreeText = rootView.findViewById(R.id.noDataTreeText);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        fragmentRecyclerView.setLayoutManager(layoutManager);
        fragmentRecyclerView.setItemAnimator(new DefaultItemAnimator());


        Bundle b = getArguments();
        if (b != null) {
            this.subjects = b.getParcelableArrayList("list");
            if (subjects.isEmpty()) {
                fragmentRecyclerView.setVisibility(View.GONE);
                noDataTreeText.setText(getResources().getString(R.string.no_subtitles_found));
                noDataTreeText.setVisibility(View.VISIBLE);

            } else {
                noDataTreeText.setVisibility(View.GONE);
                treeAdapter = new SubjectTreeAdapter(subjects,getActivity() ,new SubjectTreeAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Subjects item, int clickedView) {
                        Intent intent = new Intent(getActivity(),VersesActivity.class);
                        intent.putExtra("subject_id", item.getID());
                        intent.putExtra("subject_name", item.getSubject());
                        startActivity(intent);
                        getActivity().finish();
                    }

                });
                fragmentRecyclerView.setAdapter(treeAdapter);
//                treeAdapter.setSubjectsList(this.subjects);
//                treeAdapter.notifyDataSetChanged();
                fragmentRecyclerView.setVisibility(View.VISIBLE);
            }
        }
        return rootView;
    }

}
