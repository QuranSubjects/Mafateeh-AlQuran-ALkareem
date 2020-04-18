package com.example.lenovo.quransubjectsapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {
    LinearLayout sortingLayout;
    RadioGroup sortingRadioGroup;
    TextView  chart;
    TextView subjectTree;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setDefaultLanguage(this, "ar");
        sortingLayout = findViewById(R.id.sorting_options_layout);
        sortingRadioGroup = findViewById(R.id.sorting_group);
//        subjectTree = findViewById(R.id.subject_tree);
        chart = findViewById(R.id.chart);
        Boolean m=false;
        Boolean k=false;
        if(getIntent()!=null && getIntent().hasExtra("activity")){
            m = getIntent().getStringExtra("activity").equals("main");
        }
//        if(getIntent()!=null && getIntent().hasExtra("subject_id") && getIntent().hasExtra("searchType")){
//            k = getIntent().getStringExtra("searchType").equals("موضوع");
//        }
        if(MainActivity.status==MainActivity.THIRD_STATE_WITH_VERSES_SURAS_TABS && m){
            sortingLayout.setVisibility(View.VISIBLE);
            chart.setVisibility(View.VISIBLE);
//            if(k){
//                subjectTree.setVisibility(View.VISIBLE);
//            }
        }else{
            sortingLayout.setVisibility(View.GONE);
            chart.setVisibility(View.GONE);
//            subjectTree.setVisibility(View.GONE);

        }
        SharedPreferences prefs = this.getSharedPreferences(
                "sorting", Context.MODE_PRIVATE);
        String s = prefs.getString("sort", "qurani");
        RadioButton b;
        switch (s) {
            case "makki-madani":
                b = (RadioButton) findViewById(R.id.makki_madani);
                b.setChecked(true);
                break;
            case "qurani":
                b = (RadioButton) findViewById(R.id.qur2ani);
                b.setChecked(true);
                break;
            case "alphabetical":

                b = (RadioButton) findViewById(R.id.alphabetical);
                b.setChecked(true);
                break;
        }
        sortingRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                Intent intent = new Intent();
                switch (i) {
                    case R.id.makki_madani:
                        intent.putExtra("sort", "makki-madani");
                        break;
                    case R.id.alphabetical:
                        intent.putExtra("sort", "alphabetical");

                        break;
                    case R.id.qur2ani:
                        intent.putExtra("sort", "qurani");

                        break;

                }
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    public static void setDefaultLanguage(Context context, String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config,
                context.getResources().getDisplayMetrics());
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        View view = getWindow().getDecorView();
        WindowManager.LayoutParams lp = (WindowManager.LayoutParams) view.getLayoutParams();
        lp.gravity = Gravity.LEFT | Gravity.TOP;
        lp.x = 10;
        lp.y = 10;
        getWindowManager().updateViewLayout(view, lp);
    }

    public void showSortingOptions(View view) {
        if (sortingRadioGroup.getVisibility() == View.GONE) {
            sortingRadioGroup.setVisibility(View.VISIBLE);
        } else {
            sortingRadioGroup.setVisibility(View.GONE);
        }
    }

    public void help(View view) {
        Intent intent = new Intent(this,HelpActivity.class);
        startActivity(intent);
        finish();
    }

    public void about(View view) {
        Intent intent = new Intent(this,AboutActivity.class);
        startActivity(intent);
        finish();
    }

    public void chart(View view) {
        if(MainActivity.vos.size()!=0) {
            Intent intent = new Intent(this, ChartActivity.class);
            intent.putExtra("subject", getIntent().getStringExtra("subject"));
            intent.putExtra("searchType", getIntent().getStringExtra("searchType"));
            startActivity(intent);
            finish();
        }
    }
//
//    public void subjectTree(View view) {
//        Intent intent = new Intent(this,SubjectTreeActivity.class);
//        intent.putExtra("searchType", getIntent().getStringExtra("searchType"));
//        intent.putExtra("subject_id", getIntent().getStringExtra("subject_id"));
//        startActivity(intent);
//        finish();
//    }
}
