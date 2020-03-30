package com.example.lenovo.quransubjectsapp.fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Toast;

import com.example.lenovo.quransubjectsapp.MainActivity;
import com.example.lenovo.quransubjectsapp.QuranActivity;
import com.example.lenovo.quransubjectsapp.R;
import com.example.lenovo.quransubjectsapp.TafaseerActivity;
import com.example.lenovo.quransubjectsapp.adapters.VersesAdapter;
import com.example.lenovo.quransubjectsapp.models.Sura;
import com.example.lenovo.quransubjectsapp.models.Verses;
import com.example.lenovo.quransubjectsapp.utils.MyRecyclerScroll;

import java.util.ArrayList;

import static android.content.Context.CLIPBOARD_SERVICE;

public class NormalVersesFragment extends Fragment  {
    Context context;
    RecyclerView normalRecyclerView;
    public VersesAdapter normalversesAdapter;

    public NormalVersesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.normal_verses_fragment, container, false);
        normalRecyclerView=rootView.findViewById(R.id.normal_verses_recycler_view);
        this.context=getContext();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        normalRecyclerView.setLayoutManager(layoutManager);
        normalRecyclerView.setItemAnimator(new DefaultItemAnimator());

        normalversesAdapter = new VersesAdapter((ArrayList<Verses>) getArguments().get("verses"),getActivity() ,new VersesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Verses item, int id) {
                switch (id) {

                    case R.id.verse_text_view:
                        Intent intent = new Intent(context,TafaseerActivity.class);
                        intent.putExtra("book_id",0);
                        intent.putExtra("sura_id",item.getSuraID());
                        intent.putExtra("verse_id",item.getVerseID());
                        context.startActivity(intent);
                        break;
                    case R.id.page_num:
                        Intent intent1 = new Intent(context,QuranActivity.class);
                        intent1.putExtra("currentPage",item.getVersePage());
                        context.startActivity(intent1);

                        break;
                    case R.id.sura_name:

                        Intent intent2 = new Intent(context,QuranActivity.class);
                        intent2.putExtra("currentPage", Sura.SURAPAGES[item.getSuraID()]);
                        context.startActivity(intent2);

                        break;
                    case R.id.copy_image:
                        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("verse text", item.getVerse());
                        clipboard.setPrimaryClip(clip);
                        Toast.makeText(context, "تم نسخ الآية", Toast.LENGTH_SHORT).show();

                        break;
                    default:
                        break;
                }
            }

        });
        normalRecyclerView.setAdapter(normalversesAdapter);
        normalversesAdapter.notifyDataSetChanged();
        return rootView;
    }
    public void setVersesList(ArrayList<Verses> versesList){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        normalRecyclerView.setLayoutManager(layoutManager);
        normalRecyclerView.setItemAnimator(new DefaultItemAnimator());
        normalversesAdapter = new VersesAdapter(versesList,getActivity() ,new VersesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Verses item, int id) {
                switch (id) {

                    case R.id.verse_text_view:
                        Intent intent = new Intent(context,TafaseerActivity.class);
                        intent.putExtra("book_id",0);
                        intent.putExtra("sura_id",item.getSuraID());
                        intent.putExtra("verse_id",item.getVerseID());
                        context.startActivity(intent);
                        break;
                    case R.id.page_num:
                        Toast.makeText(context, "page", Toast.LENGTH_SHORT).show();

                        break;
                    case R.id.sura_name:

                        Toast.makeText(context, "sura", Toast.LENGTH_SHORT).show();

                        break;
                    case R.id.copy_image:
                        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("verse text", item.getVerse());
                        clipboard.setPrimaryClip(clip);
                        Toast.makeText(context, "تم نسخ الآية", Toast.LENGTH_SHORT).show();

                        break;
                    default:
                        break;
                }
            }

        });
        normalRecyclerView.setAdapter(normalversesAdapter);
        normalversesAdapter.notifyDataSetChanged();
    }
}
