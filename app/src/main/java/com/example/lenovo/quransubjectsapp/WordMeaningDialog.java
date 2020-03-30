package com.example.lenovo.quransubjectsapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.lenovo.quransubjectsapp.models.SubjectsSearch;
import com.example.lenovo.quransubjectsapp.volley.GsonRequest;
import com.example.lenovo.quransubjectsapp.volley.MySingleton;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.HashMap;
import java.util.Map;

public class    WordMeaningDialog extends AppCompatActivity {
    TextView meaningText, title;
    ProgressBar progressBar;
    String word;
    Button more;
    String versePage,verseId;
    String meaning = "", detail = "";
    String wordMeaningUrl = "http://quransubjects.com/ng--QuranService/web/api/subjects/quran-word-meaning";
    String wordMeaningDetailUrl = "http://quransubjects.com/ng--QuranService/web/api/subjects/quran-word-detail";
    Map<String, String> params = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_meaning_dialog);
        meaningText = findViewById(R.id.word_meaning);
        more = findViewById(R.id.more_button);
        title = findViewById(R.id.title);
        String fontPath = "fonts/Kufi-LT-Regular.ttf";
        String fontPath1 = "fonts/Amiri-Regular.ttf";
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath1);
//        meaningText.setTypeface(tf);
        tf = Typeface.createFromAsset(getAssets(), fontPath);
        word = getIntent().getStringExtra("selectedtext");
        versePage = getIntent().getStringExtra("versePage");
        verseId = getIntent().getStringExtra("verseId");
        title.setTypeface(tf);
        more.setTypeface(tf);
        title.setText("معنى كلمة: " + word);
        progressBar = findViewById(R.id.progress);
        word=word.trim();
        wordMeaningRequest();

    }

    private void wordMeaningRequest() {
        Gson gson = new Gson();
        params.clear();
//        params.put("sura_id", sura_id);
//        params.put("verse_id", verse_id);
        params.put("word", word);
        params.put("versePage", versePage);
        params.put("verseId", verseId);
        GsonRequest<SubjectsSearch> subjectsGsonRequest = new GsonRequest<SubjectsSearch>(wordMeaningUrl, Request.Method.POST, SubjectsSearch.class, null, params, new Response.Listener<SubjectsSearch>() {
            @Override
            public void onResponse(SubjectsSearch response) {
                if (response != null) {
                    JsonElement data = response.getData();
                    meaning = gson.fromJson(data, String.class);
                    progressBar.setVisibility(View.GONE);
                    meaningText.setVisibility(View.VISIBLE);
                    if (meaning == null) {
                        meaning = "لا يوجد معنى لهذه الكلمة";
                    }
                    meaningText.setText(meaning);
                    wordDetailRequest();
////                    Toast.makeText(getApplicationContext(), meaning, Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(getApplicationContext(),WordMeaningDialog.class);
//                    intent.putExtra("wordmeaning",meaning);
//                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "response null", Toast.LENGTH_SHORT).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "لا يوجد إتصال بالشبكة", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                meaningText.setText("لا يوجد إتصال بالشبكة");
            }
        });
        subjectsGsonRequest.setShouldCache(false);
        subjectsGsonRequest.setTag("main_request");
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(subjectsGsonRequest);

    }

    private void wordDetailRequest() {
        Gson gson = new Gson();
        params.clear();
//        params.put("sura_id", sura_id);
//        params.put("verse_id", verse_id);
        params.put("word_text", word);
        params.put("versePage", versePage);
        params.put("verseId", verseId);
        GsonRequest<SubjectsSearch> subjectsGsonRequest = new GsonRequest<SubjectsSearch>(wordMeaningDetailUrl, Request.Method.POST, SubjectsSearch.class, null, params, new Response.Listener<SubjectsSearch>() {
            @Override
            public void onResponse(SubjectsSearch response) {
                if (response != null) {
                    JsonElement data = response.getData();
                    detail = gson.fromJson(data, String.class);
                    if(detail!=null){
                        more.setVisibility(View.VISIBLE);
                        more.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(getApplicationContext(), QuranWordDetail.class);
                                intent.putExtra("detail", detail);
                                intent.putExtra("word", word);
                                intent.putExtra("meaning", meaning);
                                startActivity(intent);
                            }
                        });
                    }
                    //                    Intent intent = new Intent(getApplicationContext(),WordMeaningDialog.class);
//                    intent.putExtra("wordmeaning",meaning);
//                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "response null", Toast.LENGTH_SHORT).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "لا يوجد إتصال بالشبكة", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                meaningText.setText("لا يوجد إتصال بالشبكة");
            }
        });
        subjectsGsonRequest.setShouldCache(false);
        subjectsGsonRequest.setTag("main_request");
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(subjectsGsonRequest);

    }
}
