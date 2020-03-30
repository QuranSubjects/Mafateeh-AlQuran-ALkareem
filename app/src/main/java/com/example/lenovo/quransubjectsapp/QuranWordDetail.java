package com.example.lenovo.quransubjectsapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

public class QuranWordDetail extends AppCompatActivity {
TextView meaning,details,title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quran_word_detail);
        setDefaultLanguage(this, "ar");
        showActionBar();
        String fontPath = "fonts/Al-QuranAlKareem.ttf";

        Typeface tfLight = Typeface.createFromAsset(getAssets(), fontPath);
        meaning = findViewById(R.id.meaning);
        details=findViewById(R.id.details);
        title = findViewById(R.id.title);
        meaning.setText(getIntent().getStringExtra("meaning"));
        details.setText(getIntent().getStringExtra("detail"));
        title.setText("معنى كلمة: " +getIntent().getStringExtra("word") +"\n");
        ImageView homeButton = findViewById(R.id.action_main);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        TextView toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarTitle.setText("القاموس");
        ImageView action_help = findViewById(R.id.action_help);
        action_help.setVisibility(View.GONE);
        toolbarTitle.setTypeface(tfLight);
    }

    private void showActionBar() {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.main_toolbar, null);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setCustomView(v);
    }

    public static void setDefaultLanguage(Context context, String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config,
                context.getResources().getDisplayMetrics());
    }}
