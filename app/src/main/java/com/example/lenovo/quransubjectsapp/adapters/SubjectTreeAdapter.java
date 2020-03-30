package com.example.lenovo.quransubjectsapp.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.quransubjectsapp.R;
import com.example.lenovo.quransubjectsapp.models.Subjects;

import java.util.List;

public class SubjectTreeAdapter extends RecyclerView.Adapter<SubjectTreeAdapter.MyViewHolder> {
    private List<Subjects> subjectsList;
    private final OnItemClickListener listener;
    Context context;
    public interface OnItemClickListener {
        void onItemClick(Subjects item, int clickedView);
    }
    public void setSubjectsList(List<Subjects> subjectsList) {
        this.subjectsList = subjectsList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView subjectTitle, versesNum;
        public CardView treeCard;

        public MyViewHolder(View itemView) {

            super(itemView);
            subjectTitle = itemView.findViewById(R.id.subject_title);
            versesNum = itemView.findViewById(R.id.verses_num);
            treeCard = itemView.findViewById(R.id.tree_card);

        }
        public void bind(final Subjects item, final OnItemClickListener listener) {
//        subjectTitle.setText(item.getSubject());
//            Picasso.with(itemView.getContext()).load(item.imageUrl).into(image);


            treeCard.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {
                    listener.onItemClick(item, R.id.tree_card);
                }

            });

        }
    }


    public SubjectTreeAdapter(List<Subjects> subjectsList, Context context, OnItemClickListener listener) {
        this.subjectsList = subjectsList;
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public SubjectTreeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_tree_row, parent, false);
       MyViewHolder vh = new MyViewHolder(item);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String fontPath = "fonts/Al-QuranAlKareem.ttf";
        Typeface tf = Typeface.createFromAsset(context.getAssets(), fontPath);
        Subjects subject = subjectsList.get(position);
        holder.subjectTitle.setText(subject.getSubject());
        holder.subjectTitle.setTypeface(tf);
        String s = "";
        int num = subject.getNoOfVerses();
        if(num==2){
            s="آيتين";
        }else if(num>=3 && num<=10){
            s=num +"آيات";
            }else if(num==1 || num>=11){
            s=num+"آية";
        }
        holder.versesNum.setText(s);
        holder.versesNum.setTypeface(tf);

        holder.bind(subjectsList.get(position),listener);

    }

    @Override
    public int getItemCount() {
        return subjectsList.size();
    }

}
