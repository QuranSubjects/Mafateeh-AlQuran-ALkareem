package com.example.lenovo.quransubjectsapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.quransubjectsapp.R;
import com.example.lenovo.quransubjectsapp.adapters.ExpandableRecyclerAdapter;
import com.example.lenovo.quransubjectsapp.models.VersesOfSuras;
import com.h6ah4i.android.widget.advrecyclerview.animator.GeneralItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.animator.RefactoredDefaultItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.decoration.SimpleListDividerDecorator;
import com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager;

import java.util.ArrayList;

public class ExpandedVersesFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener, RecyclerViewExpandableItemManager.OnGroupExpandListener{
    Context context;
    RecyclerView expandedRecyclerView;
    RecyclerViewExpandableItemManager recyclerViewExpandableItemManager;
    public ExpandableRecyclerAdapter expandableRecyclerAdapter;
    RecyclerView.Adapter recyclerViewAdapterWrapper;
    public ExpandedVersesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.expanded_verses_fragment, container, false);
        expandedRecyclerView = rootView.findViewById(R.id.expanded_verses_recycler_view);
        GeneralItemAnimator generalItemAnimator = new RefactoredDefaultItemAnimator();
        generalItemAnimator.setSupportsChangeAnimations(false);
        expandedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        expandedRecyclerView.addItemDecoration(new SimpleListDividerDecorator(ContextCompat.getDrawable(getContext(),R.drawable.list_divider_h),true));
        expandedRecyclerView.setItemAnimator(generalItemAnimator);
        recyclerViewExpandableItemManager = new RecyclerViewExpandableItemManager(null);
        recyclerViewExpandableItemManager.setOnGroupExpandListener(this);
        expandableRecyclerAdapter = new ExpandableRecyclerAdapter(recyclerViewExpandableItemManager);
        recyclerViewAdapterWrapper = recyclerViewExpandableItemManager.createWrappedAdapter(expandableRecyclerAdapter);
        expandedRecyclerView.setAdapter(recyclerViewAdapterWrapper);
        expandableRecyclerAdapter.setData((ArrayList<VersesOfSuras>) getArguments().get("vos"));

//      getArguments().getString("tafseer");
        return rootView;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onGroupExpand(int groupPosition, boolean fromUser, Object payload) {
        expandedRecyclerView.getLayoutManager().scrollToPosition(groupPosition);

    }
    public void setVOS(ArrayList<VersesOfSuras> vos){
        GeneralItemAnimator generalItemAnimator = new RefactoredDefaultItemAnimator();
        generalItemAnimator.setSupportsChangeAnimations(false);
        expandedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        expandedRecyclerView.addItemDecoration(new SimpleListDividerDecorator(ContextCompat.getDrawable(getContext(),R.drawable.list_divider_h),true));
        expandedRecyclerView.setItemAnimator(generalItemAnimator);
        recyclerViewExpandableItemManager = new RecyclerViewExpandableItemManager(null);
        recyclerViewExpandableItemManager.setOnGroupExpandListener(this);
        expandableRecyclerAdapter = new ExpandableRecyclerAdapter(recyclerViewExpandableItemManager);
        recyclerViewAdapterWrapper = recyclerViewExpandableItemManager.createWrappedAdapter(expandableRecyclerAdapter);
        expandedRecyclerView.setAdapter(recyclerViewAdapterWrapper);
        expandableRecyclerAdapter.setData(vos);
    }
}
