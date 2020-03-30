package com.example.lenovo.quransubjectsapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.CharacterStyle;
import android.text.style.UnderlineSpan;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.lenovo.quransubjectsapp.R;
import com.example.lenovo.quransubjectsapp.WordMeaningDialog;
import com.example.lenovo.quransubjectsapp.fragments.QuranPageFragment;
import com.example.lenovo.quransubjectsapp.models.SubjectsSearch;
import com.example.lenovo.quransubjectsapp.models.Sura;
import com.example.lenovo.quransubjectsapp.models.Verses;
import com.example.lenovo.quransubjectsapp.volley.GsonRequest;
import com.example.lenovo.quransubjectsapp.volley.MySingleton;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VersesAdapter extends RecyclerView.Adapter<VersesAdapter.MyViewHolder> {
    private ArrayList<Verses> versesList;
    private final OnItemClickListener listener;
    Context context;
    TextView verseTextView;
    String wordMeaningUrl = "http://quransubjects.com/ng--QuranService/web/api/subjects/quran-word-meaning";
    Map<String, String> params = new HashMap<String, String>();
    String word="";
    public interface OnItemClickListener {
        void onItemClick(Verses item, int clickedView);
    }

    public void setVersesList(ArrayList<Verses> versesList) {
        this.versesList = versesList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView verseText, pageNum, partNum, suraName, verseNum,verseNumber;
        public ImageView copyImage;

        public MyViewHolder(View itemView) {

            super(itemView);
            verseText = itemView.findViewById(R.id.verse_text_view);
            pageNum = itemView.findViewById(R.id.page_num);
            partNum = itemView.findViewById(R.id.part_num);
            suraName = itemView.findViewById(R.id.sura_name);
            verseNum = itemView.findViewById(R.id.verse_num_in_list);
            verseNumber = itemView.findViewById(R.id.verse_num);
            copyImage = itemView.findViewById(R.id.copy_image);
        }

        public void bind(final Verses item, final OnItemClickListener listener) {
            verseText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item, R.id.verse_text_view);
                }
            });
            pageNum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item, R.id.page_num);
                }
            });
            suraName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item, R.id.sura_name);
                }
            });
            copyImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item, R.id.copy_image);
                }
            });

        }
    }


    public VersesAdapter(ArrayList<Verses> versesList, Context context, OnItemClickListener listener) {
        this.versesList = versesList;
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public VersesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.verse_row, parent, false);
        MyViewHolder vh = new MyViewHolder(item);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Verses verse = versesList.get(position);
        holder.verseText.setText(verse.getVerse());
        String fontPath = "fonts/Amiri-Regular.ttf";
        Typeface tf = Typeface.createFromAsset(context.getAssets(), fontPath);
        holder.verseText.setTypeface(tf);
            holder.verseText.setCustomSelectionActionModeCallback((ActionMode.Callback) new VersesAdapter.StyleCallback(holder.verseText, verse.getVerseID()+"",verse.getVersePage()+""));
            fontPath = "fonts/Kufi-LT-Regular.ttf";
        tf = Typeface.createFromAsset(context.getAssets(), fontPath);
        String pageNumText = "ص " + verse.getVersePage();
        holder.pageNum.setText(pageNumText);
        holder.pageNum.setTypeface(tf);

        holder.partNum.setText("جزء " +  Sura.getSection(verse.getVersePage()));
        holder.partNum.setTypeface(tf);

        String suraNameText = verse.getSura();
        holder.suraName.setText(suraNameText);
        holder.suraName.setTypeface(tf);
        int num = position + 1;
        holder.verseNum.setText("" + num);
        holder.verseNum.setTypeface(tf);
        holder.verseNumber.setText("آية " + verse.getVerseID());
        holder.verseNumber.setTypeface(tf);
        holder.bind(versesList.get(position), listener);

    }

    @Override
    public int getItemCount() {
        return versesList.size();
    }
    private class StyleCallback implements ActionMode.Callback {
        TextView t;
        String verseId,versePage;
        StyleCallback(TextView t,String verseId,String versePage){
            this.t=t;
            this.verseId=verseId;
            this.versePage=versePage;
        }
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            menu.clear();
            mode.getMenuInflater().inflate(R.menu.stylewithnotafseeraya, menu);
            return true;
        }

        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {

            menu.clear();
            mode.getMenuInflater().inflate(R.menu.stylewithnotafseeraya, menu);
            return true;
        }

        @Override


        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            int start = t.getSelectionStart();
            int end = t.getSelectionEnd();
            CharSequence selectedText = t.getText().subSequence(start, end);



            switch (item.getItemId()) {


                case R.id.word_tafseer:
                    CharSequence mainText = t.getText();
                    selectedText = selectedText.toString().trim();
                    if (!selectedText.toString().contains(" ")) {
                        for (int i = start; i > 0; i--) {
                            if (mainText.subSequence(i, end).toString().contains(" ") ||
                                    mainText.subSequence(i, end).toString().contains(")") ||
                                    mainText.subSequence(i, end).toString().contains("\n")) {
                                start = i + 1;
                                break;
                            }
                        }
                        for (int i = end; i < mainText.length(); i++) {
                            if (mainText.subSequence(start, i).toString().contains(" ") ||
                                    mainText.subSequence(start, i).toString().contains("(") ||
                                    mainText.subSequence(start, i).toString().contains("\n")) {
                                end = i - 1;
                                break;
                            }
                        }
                        selectedText = mainText.subSequence(start, end);
                        Intent intent = new Intent(context, WordMeaningDialog.class);
                        intent.putExtra("selectedtext", selectedText.toString());
                        intent.putExtra("verseId",  verseId);
                        intent.putExtra("versePage", versePage);
                        context.startActivity(intent);
                    }{
                    Toast.makeText(context, "الرجاء إختيار الكلمة بشكل دقيق. لا يمكن إظهار معنى لأكثر من كلمة", Toast.LENGTH_SHORT).show();
                }

            }
            return false;
        }

        public void onDestroyActionMode(ActionMode mode) {
        }

    }


}

