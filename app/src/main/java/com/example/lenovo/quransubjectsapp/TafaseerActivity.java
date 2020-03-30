package com.example.lenovo.quransubjectsapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.example.lenovo.quransubjectsapp.adapters.ViewPagerAdapter;
import com.example.lenovo.quransubjectsapp.fragments.TafaseerFragment;
import com.example.lenovo.quransubjectsapp.models.Sura;
import com.example.lenovo.quransubjectsapp.models.Tafseer;
import com.example.lenovo.quransubjectsapp.volley.GsonRequest;
import com.example.lenovo.quransubjectsapp.volley.MySingleton;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;


public class TafaseerActivity extends AppCompatActivity {
    TextView verseText, versePageSura;
    ImageView quran;
    String tafseerUrl = "https://quransubjects.com/ng--QuranService/web/api/subjects/tafaseer-text";
    String verseUrl = "https://quransubjects.com/ng--QuranService/web/subjects/verse";
    int verseId, suraId;
    ViewPager viewPager;
    ImageView nextVerse, previousVerse;
    RelativeLayout connectionLayout;
    TabLayout tabLayout;
    static public TafaseerFragment[] fragments;
    String[] tafaseerText = new String[8];
    RelativeLayout tafseer_layout;
    ProgressBar tafaseerProgress;
    SwipeRefreshLayout tafaseerSwipeRefreshLayout;
    //TextView tafseerText;
    Map<String, String> params = new HashMap<>();
    static int numberOfRequests = 0;
    public static int pos = 0;
    ImageView homeButton;
    RelativeLayout tafaseerLayout;
    @Override
    protected void onResume() {
        super.onResume();
//        Toast.makeText(this, Locale.getDefault().getLanguage(), Toast.LENGTH_SHORT).show();
        if(Locale.getDefault().getLanguage().equals("en")){
            Intent i = getBaseContext().getPackageManager().
                    getLaunchIntentForPackage(getBaseContext().getPackageName());
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            MainActivity.status=MainActivity.FIRST_STATE;
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        }
//        setDefaultLanguage(this, "ar");

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tafaseer);
        setDefaultLanguage(this, "ar");


        showActionBar();
        quran = findViewById(R.id.action_quran);
        quran.setVisibility(View.VISIBLE);
        quran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), QuranActivity.class);
                startActivity(intent);
            }
        });
        tafaseerLayout = findViewById(R.id.tafseer_layout);
        nextVerse = findViewById(R.id.nextVerse);
        previousVerse = findViewById(R.id.prevVerse);
        if (getIntent().hasExtra("book_id") && getIntent().hasExtra("sura_id") && getIntent().hasExtra("verse_id")) {
            if(getIntent().getStringExtra("sura_id")!=null){
                suraId = Integer.parseInt(getIntent().getStringExtra("sura_id"));
                verseId= Integer.parseInt(getIntent().getStringExtra("verse_id"));
            }else {
                suraId = getIntent().getIntExtra("sura_id", 0);
                verseId = getIntent().getIntExtra("verse_id", 0);
            }
        }
        tabLayout = findViewById(R.id.tafaseer_tabs);
        tafaseerSwipeRefreshLayout = findViewById(R.id.activity_tafaseer_swipe_refresh_layout);
        homeButton = findViewById(R.id.action_main);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionMain(view);
            }
        });
        verseText = findViewById(R.id.verse_tafseer_text);
        versePageSura = findViewById(R.id.verse_page_sura_num);

        viewPager = findViewById(R.id.tafaseer_viewpager);
        tafseer_layout = findViewById(R.id.tafseer_layout);
        tafaseerProgress = findViewById(R.id.tafaseer_progress_bar);
        connectionLayout = findViewById(R.id.tafaseer_connection_layout);
        tafaseerSwipeRefreshLayout.setEnabled(false);
        tafaseerSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewPager.setVisibility(View.GONE);
                refreshContent();
            }
        });

        getVerseText(suraId, verseId);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            tabLayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }
        nextVerse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (verseId != Sura.SURASNUMVERSES[suraId]) {

                    tafaseerLayout.setVisibility(View.GONE);
                    tafaseerProgress.setVisibility(View.VISIBLE);
                    viewPager.setVisibility(View.GONE);
                    verseId++;
                    getVerseText(suraId, verseId);
                } else {
                    Toast.makeText(TafaseerActivity.this, "لا مزيد من الآيات في هذه السورة", Toast.LENGTH_SHORT).show();
                }
            }
        });
        previousVerse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (verseId != 1) {

                    viewPager.setVisibility(View.GONE);
                    tafaseerLayout.setVisibility(View.GONE);
                    tafaseerProgress.setVisibility(View.VISIBLE);
                    verseId--;
                    getVerseText(suraId, verseId);
                }
            }
        });


    }


    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        fragments = new TafaseerFragment[8];
        Bundle b;
        for (int i = 0; i < 8; i++) {
            fragments[i] = new TafaseerFragment();
            b = new Bundle();
            b.putString("tafseer", tafaseerText[i]);
            fragments[i].setArguments(b);
        }


        adapter.addFragment(fragments[7], getResources().getString(R.string.ibn_kaseer));
        adapter.addFragment(fragments[6], getResources().getString(R.string.altahreer_waltanweer_libn_ashoor));
        adapter.addFragment(fragments[5], getResources().getString(R.string.alrazi));
        adapter.addFragment(fragments[4], getResources().getString(R.string.altabari));
        adapter.addFragment(fragments[3], getResources().getString(R.string.altibyan_fi_tafseer_alqoran));
        adapter.addFragment(fragments[2], getResources().getString(R.string.majmaa_albayan));
        adapter.addFragment(fragments[1], getResources().getString(R.string.meezan));
        adapter.addFragment(fragments[0], getResources().getString(R.string.amthal));


        viewPager.setAdapter(adapter);

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

    public void requestTafseerData(int suraId, int verseId) {
        tafaseerProgress.setVisibility(View.VISIBLE);
        connectionLayout.setVisibility(View.GONE);
        params.clear();
        params.put("sura_id", suraId + "");
        params.put("verse_id", verseId + "");
        GsonRequest<JsonObject> tafseerGsonRequest1 = new GsonRequest<JsonObject>(tafseerUrl, Request.Method.POST, JsonObject.class, null, params, new Response.Listener<JsonObject>() {
            @Override
            public void onResponse(JsonObject response) {
                Gson gson = new Gson();
                Set<String> s = response.keySet();
                Tafseer tafseer;
                String tafseerText;
                numberOfRequests = 0;
                for (String book : s) {

                    tafseer = gson.fromJson(response.getAsJsonObject(book), Tafseer.class);
                    tafaseerText[numberOfRequests] = tafseer.getTafseerText();
                    numberOfRequests++;
                }
                viewPager.setVisibility(View.VISIBLE);
                tafaseerProgress.setVisibility(View.GONE);
                tafseer_layout.setVisibility(View.VISIBLE);
                setupViewPager(viewPager);
                tabLayout.setupWithViewPager(viewPager);
                viewPager.setCurrentItem(7);

//                Toast.makeText(TafaseerActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tafaseerProgress.setVisibility(View.GONE);
                connectionLayout.setVisibility(View.VISIBLE);
                getErrorMessage(error);
            }
        });
        tafseerGsonRequest1.setShouldCache(false);
        tafseerGsonRequest1.setTag("tafseer");
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(tafseerGsonRequest1);


    }

    public void getVerseText(int suraId, int verseId) {

//        params.clear();
//        params.put("sura_id", suraId+"");
//        params.put("verse_id", verseId+"");
        List<Quran> q = Quran.find(Quran.class, "SURA_ID = " + suraId + " and VERSE_ID = " + verseId);
        String fontPath = "fonts/Amiri-Regular.ttf";
        Quran aya = q.get(0);

        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        verseText.setTypeface(tf);
        tafaseerLayout.setVisibility(View.VISIBLE);
        tafaseerProgress.setVisibility(View.GONE);
        verseText.setText(aya.getVerse() + " (" + aya.getVerseID() + ")");
        fontPath = "fonts/Kufi-LT-Regular.ttf";
        tf = Typeface.createFromAsset(getAssets(), fontPath);
        versePageSura.setTypeface(tf);
        versePageSura.setText(aya.getSura() +"  "+" آية "+aya.getVerseID()+ "    ص" + aya.getPage());
        versePageSura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),QuranActivity.class);
                intent.putExtra("currentPage", aya.getPage());
                startActivity(intent);
            }
        });
        requestTafseerData(suraId, verseId);
//        GsonRequest<Verses> tafseerGsonRequest1 = new GsonRequest<Verses>(verseUrl, Request.Method.POST, Verses.class, null, params, new Response.Listener<Verses>() {
//            @Override
//            public void onResponse(Verses response) {
//                String fontPath = "fonts/Amiri-Regular.ttf";
//                Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
//                verseText.setTypeface(tf);
//
//                tafaseerLayout.setVisibility(View.VISIBLE);
//                tafaseerSmallProgress.setVisibility(View.GONE);
//
//                verseText.setText(response.getVerse()+ " ("+response.getVerseID()+")");
//                fontPath = "fonts/Kufi-LT-Regular.ttf";
//                tf = Typeface.createFromAsset(getAssets(), fontPath);
//                versePageSura.setTypeface(tf);
//                versePageSura.setText(response.getSura() + "    ص"+ response.getPage());
//               requestTafseerData(suraId,verseId);
////                Toast.makeText(TafaseerActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
//            }
//        }
//                , new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                tafaseerProgress.setVisibility(View.GONE);
//                connectionLayout.setVisibility(View.VISIBLE);
//                getErrorMessage(error);
//            }
//        });
//        tafseerGsonRequest1.setShouldCache(false);
//        tafseerGsonRequest1.setTag("tafseer");
//        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(tafseerGsonRequest1);
    }

    private void getErrorMessage(VolleyError volleyError) {
        String message = null;
        if (volleyError instanceof NetworkError) {
            message = getString(R.string.check_connection);
        } else if (volleyError instanceof ServerError) {
            message = "The server could not be found. Please try again after some time!!";
        } else if (volleyError instanceof AuthFailureError) {
            message = getString(R.string.check_connection);
        } else if (volleyError instanceof ParseError) {
            message = "Parsing error! Please try again after some time!!";
        } else if (volleyError instanceof NoConnectionError) {
            message = getString(R.string.check_connection);
        } else if (volleyError instanceof TimeoutError) {
            message = getString(R.string.check_connection);
        }
        tafaseerSwipeRefreshLayout.setEnabled(true);

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void refreshContent() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                requestTafseerData(suraId, verseId);
                tafaseerSwipeRefreshLayout.setRefreshing(false);
                tafaseerSwipeRefreshLayout.setEnabled(false);

            }
        }, 100);
    }

    public void actionMain(View view) {

        finish();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.clear();
    }
    public static void setDefaultLanguage(Context context, String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config,
                context.getResources().getDisplayMetrics());
    }
}
