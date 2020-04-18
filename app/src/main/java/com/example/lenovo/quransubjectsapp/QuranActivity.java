package com.example.lenovo.quransubjectsapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lenovo.quransubjectsapp.adapters.QuranAdapter;
import com.example.lenovo.quransubjectsapp.fragments.QuranPageFragment;
import com.wajahatkarim3.easyflipviewpager.BookFlipPageTransformer;

import java.util.Locale;

public class QuranActivity extends AppCompatActivity {
    ViewPager quranPager;
    QuranAdapter adapter;
    int currentSura = 0, currentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDefaultLanguage(this, "ar");
        setContentView(R.layout.activity_quran);
        showActionBar();
        if (getIntent() != null && getIntent().hasExtra("currentPage")) {
            currentPage = getIntent().getIntExtra("currentPage", 0);
        }
        ImageView homeButton = findViewById(R.id.action_main);
        ImageView quranImageView = findViewById(R.id.action_quran);
        ImageView contactUsImageView = findViewById(R.id.action_contact_us);
        quranImageView.setVisibility(View.GONE);
        contactUsImageView.setVisibility(View.GONE);
//        quranImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               Intent intent = new Intent(getApplicationContext(),QuranActivity.class);
//               startActivity(intent);
//               finish();
//            }
//        });
        ImageView actionSettings = findViewById(R.id.action_help);
        actionSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        quranPager = findViewById(R.id.quran_pager);
//        quranPager.setAdapter(adapter);
        quranPager.setClipToPadding(false);
        BookFlipPageTransformer bookFlipPageTransformer = new BookFlipPageTransformer();

// Enable / Disable scaling while flipping. If true, then next page will scale in (zoom in). By default, its true.
        bookFlipPageTransformer.setEnableScale(true);
// The amount of scale the page will zoom. By default, its 5 percent.
        bookFlipPageTransformer.setScaleAmountPercent(10f);

// Assign the page transformer to the ViewPager.
        quranPager.setPageTransformer(true, bookFlipPageTransformer);
        setUpViewPager();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Toast.makeText(this, Locale.getDefault().getLanguage(), Toast.LENGTH_SHORT).show();
        if(Locale.getDefault().getLanguage().equals("en")){
            Intent i = getBaseContext().getPackageManager().
                    getLaunchIntentForPackage(getBaseContext().getPackageName());
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            MainActivity.status=MainActivity.FIRST_STATE;
            startActivity(i);
            finish();
        }
//        setDefaultLanguage(this, "ar");

    }

    public void setUpViewPager() {
        QuranPageFragment q;
        Bundle b = new Bundle();
        adapter = new QuranAdapter(getSupportFragmentManager());
        if(MainActivity.quranPages.length!=0) {
            for (int i = 1; i <= MainActivity.quranPages.length; i++) {
//            b.clear();
                q = new QuranPageFragment();

//            q.setArguments(b);
                adapter.addFragment(q);
            }
        }
        quranPager.setAdapter(adapter);
        if (currentPage != 0) {
            quranPager.setCurrentItem(currentPage);
        }
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
        context.getResources().updateConfiguration(config,context.getResources().getDisplayMetrics());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Clear the Activity's bundle of the subsidiary fragments' bundles.
        outState.clear();
    }
//
//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//
//        Toast.makeText(this, "xxx", Toast.LENGTH_SHORT).show();
//        super.onWindowFocusChanged(hasFocus);
//    }
}
