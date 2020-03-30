package com.example.lenovo.quransubjectsapp.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.quransubjectsapp.R;
import com.example.lenovo.quransubjectsapp.models.Subjects;

import java.util.ArrayList;

public class MainSubjectHistoryAdapter extends RecyclerView.Adapter<MainSubjectHistoryAdapter.MyViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(Subjects item, int clickedView);
        void onLongClick(Subjects item, int clickedView);
    }

    private Context context;
    private ArrayList<Subjects> subjects;
    private final OnItemClickListener listener;
    Boolean fromHistory;

    public void setSubjects(ArrayList<Subjects> subjects) {
        this.subjects = subjects;
    }

    public MainSubjectHistoryAdapter(Context context, ArrayList<Subjects> subjects, Boolean fromHistory, OnItemClickListener listener) {
        this.listener = listener;
        this.subjects = subjects;
        this.fromHistory = fromHistory;
        this.context = context;
    }

    @NonNull
    @Override
    public MainSubjectHistoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_row, parent, false);
        MyViewHolder vh = new MyViewHolder(item);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(subjects.get(position), listener);
        Subjects subject = subjects.get(position);
        String fontPath = "fonts/Al-QuranAlKareem.ttf";
        Typeface tf = Typeface.createFromAsset(context.getAssets(), fontPath);
        holder.subjectTitle.setText(subject.getSubject());
        holder.subjectTitle.setTypeface(tf);
        if(!fromHistory){
//            holder.history_image.setVisibility(View.GONE);
            holder.arrow_image.setVisibility(View.GONE);

            holder.subjectTitle.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.search_logo,0);
        }
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView subjectTitle;
        public ImageView  arrow_image;

        public MyViewHolder(final View itemView) {
            super(itemView);
            subjectTitle = itemView.findViewById(R.id.subject_title_main);
            arrow_image = itemView.findViewById(R.id.arrow_image);
//            history_image = itemView.findViewById(R.id.history_image);



        }

        public void bind(final Subjects item, final OnItemClickListener listener) {
            subjectTitle.setText(item.getSubject());
//            Picasso.with(itemView.getContext()).load(item.imageUrl).into(image);

            subjectTitle.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listener.onLongClick(item,R.id.subject_title_main);
                return true;
                }
            });
            subjectTitle.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {
                    listener.onItemClick(item, R.id.subject_title_main);
                }

            });
            if (fromHistory) {
                arrow_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onItemClick(item, R.id.arrow_image);
                    }
                });

            }

        }
    }

}

