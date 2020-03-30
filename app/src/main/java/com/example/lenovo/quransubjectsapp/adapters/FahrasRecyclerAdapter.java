package com.example.lenovo.quransubjectsapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.quransubjectsapp.QuranActivity;
import com.example.lenovo.quransubjectsapp.R;
import com.example.lenovo.quransubjectsapp.models.Sura;
import com.example.lenovo.quransubjectsapp.models.Verses;

import java.util.ArrayList;

public class FahrasRecyclerAdapter extends RecyclerView.Adapter<FahrasRecyclerAdapter.MyViewHolder> {
//    private final FahrasRecyclerAdapter.OnItemClickListener listener;
    Context context;

    public interface OnItemClickListener {
        void onItemClick(int item);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView suraText, pageNum;
        RelativeLayout fahrasRow;

        public MyViewHolder(View itemView) {

            super(itemView);
            pageNum = itemView.findViewById(R.id.page_num);
            suraText = itemView.findViewById(R.id.sura_text);
            fahrasRow = itemView.findViewById(R.id.fahras_row);
        }

        public void bind(int item, final FahrasRecyclerAdapter.OnItemClickListener listener) {
            fahrasRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, item+"", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    public FahrasRecyclerAdapter(Context context) {

        this.context = context;
    }

    @NonNull
    @Override
    public FahrasRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.fahras_row, parent, false);
        FahrasRecyclerAdapter.MyViewHolder vh = new FahrasRecyclerAdapter.MyViewHolder(item);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull FahrasRecyclerAdapter.MyViewHolder holder, int position) {

        holder.suraText.setText((position+1)+" - سورة " + Sura.SURANAMES[position+1]);
        String fontPath = "fonts/Kufi-LT-Regular.ttf";
        Typeface tf = Typeface.createFromAsset(context.getAssets(), fontPath);
        holder.suraText.setTypeface(tf);
        holder.pageNum.setTypeface(tf);
        holder.pageNum.setText(Sura.SURAPAGES[position+1]+"");
        holder.fahrasRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,QuranActivity.class);
                intent.putExtra("currentPage", Sura.SURAPAGES[position+1]);
                context.startActivity(intent);
            }
        });
//        fontPath = "fonts/Kufi-LT-Regular.ttf";
//        tf = Typeface.createFromAsset(context.getAssets(), fontPath);
//        String pageNumText = "ص " + verse.getVersePage();
//        holder.pageNum.setText(pageNumText);
//        holder.pageNum.setTypeface(tf);

//        String suraNameText = verse.getSura();
//        holder.suraName.setText(suraNameText);
//        holder.suraName.setTypeface(tf);
//        int num = position + 1;
//        holder.verseNum.setText("" + num);
//        holder.verseNum.setTypeface(tf);
//        holder.bind(versesList.get(position), listener);

    }

    @Override
    public int getItemCount() {
        return Sura.SURANAMES.length-1;
    }

}

