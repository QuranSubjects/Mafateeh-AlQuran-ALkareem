package com.example.lenovo.quransubjectsapp;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.example.lenovo.quransubjectsapp.adapters.MainSubjectHistoryAdapter;
import com.example.lenovo.quransubjectsapp.adapters.MainSuraHistoryAdapter;
import com.example.lenovo.quransubjectsapp.adapters.MainVerseHistoryAdapter;
import com.example.lenovo.quransubjectsapp.adapters.SubjectDatabase;
import com.example.lenovo.quransubjectsapp.adapters.SubjectTreeAdapter;
import com.example.lenovo.quransubjectsapp.adapters.ViewPagerAdapter;
import com.example.lenovo.quransubjectsapp.fragments.ExpandedVersesFragment;
import com.example.lenovo.quransubjectsapp.fragments.NormalVersesFragment;
import com.example.lenovo.quransubjectsapp.interfaces.SubjectDao;
import com.example.lenovo.quransubjectsapp.models.SearchedSubject;
import com.example.lenovo.quransubjectsapp.models.SubjectTree;
import com.example.lenovo.quransubjectsapp.models.Subjects;
import com.example.lenovo.quransubjectsapp.models.SubjectsSearch;
import com.example.lenovo.quransubjectsapp.models.SuraAsSubject;
import com.example.lenovo.quransubjectsapp.models.VerseAsSubject;
import com.example.lenovo.quransubjectsapp.models.Verses;
import com.example.lenovo.quransubjectsapp.models.VersesOfSuras;
import com.example.lenovo.quransubjectsapp.volley.GsonRequest;
import com.example.lenovo.quransubjectsapp.volley.MySingleton;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.victor.loading.book.BookLoading;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    ImageView actionSettings;
    ImageView actionQuran,actionContactUs;
    LinearLayout quranInstall;
    int lastProgress = 0;
    NumberProgressBar numberProgressBar;
    Button motabiqSearchButton;
    ProgressBar mainProgressBar;
    BookLoading loading;
    Animation mShakeAnimation;
    public static String[] quranPages;
    public static String[] quranPageNum;
    static List<Quran> qurans;
    SharedPreferences prefs;
    Boolean getSubjectFromHistory = false;
    TextView toolbarTitle;
    String treeUrl = "http://quransubjects.com/ng--QuranService/web/subjects/subject-file";
    String vslinkUrl = "http://quransubjects.com/ng--QuranService/web/subjects/vslink";
    String suraVersesUrl = "http://quransubjects.com/ng--QuranService/web/subjects/sura-verses";
    String wordMotabiqUrl = "http://quransubjects.com/ng--QuranService/web/subjects/motabeq-data";
    Boolean noRequest = false;
    String searchType = "";
    RelativeLayout versesPage;
    SwipeRefreshLayout swipeRefreshLayout;
    ProgressBar versesProgress;
    DrawerLayout drawer;
    public static ArrayList<VersesOfSuras> vos = new ArrayList<>();
    static final int FIRST_STATE = 0;
    static final int SECOND_STATE_WITH_SEARCH_KIND_LAYOUT = 1;
    static final int THIRD_STATE_WITH_VERSES_SURAS_TABS = 2;
    SharedPreferences.Editor editor;
    RecyclerView homeRecyclerView;
    EditText quranSubjectSearchText;
    ArrayList<Subjects> subjectsAutoCompleteList;
    ArrayList<SuraAsSubject> surasAutoCompleteList;
    ArrayList<VerseAsSubject> versesAutoCompleteList;
    RecyclerView.LayoutManager layoutManager;
    List<Subjects> titlesList;
    List<Subjects> subtitlesList;
    List<Verses> versesList;
    TextView drawerTitle;
    Map<String, String> params = new HashMap<String, String>();
    MainSubjectHistoryAdapter subjectsAdapter;
    MainSuraHistoryAdapter suraAdapter;
    MainVerseHistoryAdapter verseAdapter;
    Gson gson = new Gson();
    RelativeLayout mainLayout;
    String subjectAutoCompleteUrl = "http://quransubjects.com/ng--QuranService/web/api/subjects/subjects";
    String suraAutoCompleteUrl = "http://quransubjects.com/ng--QuranService/web/api/subjects/suras-autocomplete";
    String verseAutoCompleteUrl = "http://quransubjects.com/ng--QuranService/web/api/subjects/verses-autocomplete";
    Handler handler;
    ArrayList<SearchedSubject> searchedSubjectsHistory = new ArrayList<>();
    private static final int TRIGGER_AUTO_COMPLETE = 100;
    private static final long AUTO_COMPLETE_DELAY = 300;
    ProgressBar searchProgressBar;
    ImageView mafateehLogo;
    public static RelativeLayout searchLayout;
    int translationHight;
    static int status = FIRST_STATE;
    static int orderInPref = 0;
    final int preferenceSize = 10;
    SubjectDao subjectDao;
    Boolean fromHistory = true;
    ImageView clearText;
    RequestQueue mRequestQueue;
    String subject_name, subject_id;
    Subjects searchedSubject;
    FloatingActionButton floatingTreeButton;
    TextView titleNum, subtitleNum;
    TextView subjectName;
    public TabLayout versesTabLayout;
    ViewPager versesViewPager;
    RecyclerView titleRecycler, subtitleRecycler;
    SubjectTreeAdapter titleTreeAdapter, subtitleTreeAdapter;
    LinearLayout titleLinearLayout, subtitleLinearLayout;
    LinearLayout searchMenu;
    LinearLayout searchKind;
    private TabLayout searchKindTabs;
    int[] motabiqWords;
    ViewPagerAdapter viewPagerAdapter;
    NormalVersesFragment normalVersesFragment;
    ExpandedVersesFragment expandedVersesFragment;
    RelativeLayout connectionLayout;
    ImageView homeButton;
    int h;

    @Override
    public void onBackPressed() {


        mainProgressBar.setVisibility(View.GONE);
        mRequestQueue.cancelAll("main_request");
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            switch (status) {
                case MainActivity.FIRST_STATE:
                    mRequestQueue.cancelAll("main_request");
                    homeRecyclerView.setVisibility(View.GONE);
                    searchProgressBar.setVisibility(View.GONE);
                    super.onBackPressed();
                    break;
                case MainActivity.SECOND_STATE_WITH_SEARCH_KIND_LAYOUT:
//                    quranSubjectSearchText.setFocusable(false);
                    hideKeyboard();
                    versesPage.setVisibility(View.GONE);
                    searchMenu.setVisibility(View.GONE);
                    homeRecyclerView.setVisibility(View.GONE);
                    clearText.setVisibility(View.GONE);
                    searchKind.setVisibility(View.GONE);
                    motabiqSearchButton.setVisibility(View.GONE);
                    toolbarTitle.setText("");
                    quranSubjectSearchText.setHint(getString(R.string.choose_search_word));
                    ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(searchLayout, "translationY", translationHight, 0);
                    objectAnimator.setDuration(500);
                    objectAnimator.start();
                    objectAnimator.addListener(new Animator.AnimatorListener() {

                        @Override
                        public void onAnimationStart(Animator animator) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            mafateehLogo.setVisibility(View.VISIBLE);

                            status = FIRST_STATE;
                            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

                            homeRecyclerView.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {

                        }
                    });
                    break;
                case MainActivity.THIRD_STATE_WITH_VERSES_SURAS_TABS:

                    if (!searchedSubjectsHistory.isEmpty()) {
                        int pos = searchedSubjectsHistory.size() - 1;
                        searchedSubjectsHistory.remove(pos);
                        if (!searchedSubjectsHistory.isEmpty()) {
                            pos = searchedSubjectsHistory.size() - 1;
                            SearchedSubject s = searchedSubjectsHistory.get(pos);

                            mainProgressBar.setVisibility(View.VISIBLE);
                            versesPage.setVisibility(View.GONE);
                            getSubjectFromHistory = true;
                            requestVersesData(s.getSearched_subject_id(), s.getSearched_subject_name(), s.getSearched_Type());

                        } else {
                            versesPage.setVisibility(View.GONE);
                            versesTabLayout.setVisibility(View.GONE);
                            searchKind.setVisibility(View.VISIBLE);
                            if (!searchType.equals("كلمة")) {
                                homeRecyclerView.setVisibility(View.VISIBLE);
                            } else {
                                motabiqSearchButton.setVisibility(View.VISIBLE);
                            }
                            status = MainActivity.SECOND_STATE_WITH_SEARCH_KIND_LAYOUT;
                            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

                        }
                    } else {
                        status = MainActivity.SECOND_STATE_WITH_SEARCH_KIND_LAYOUT;
                        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

                        onBackPressed();
                    }
                    break;

                default:
                    break;
            }
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDefaultLanguage(this, "ar");
//        Toast.makeText(this, MainActivity.status+"", Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_main);

        showActionBar();
        mShakeAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
        numberProgressBar = findViewById(R.id.number_progress_bar);
        actionSettings = findViewById(R.id.action_help);
        actionQuran = findViewById(R.id.action_quran);
        actionContactUs = findViewById(R.id.action_contact_us);
        quranInstall = findViewById(R.id.quranInstall);
        floatingTreeButton = findViewById(R.id.floating_tree_button);
        mainProgressBar = findViewById(R.id.main_progress_bar);
        editor = getSharedPreferences("SearchedSubjects", MODE_PRIVATE).edit();
        subjectDao = SubjectDatabase.getDatabase(getApplicationContext()).subjectDao();
        searchLayout = findViewById(R.id.search_layout);
        mafateehLogo = findViewById(R.id.mafateeh_logo);
        clearText = findViewById(R.id.clear_text);
        searchProgressBar = findViewById(R.id.search_progresss_bar);
        homeRecyclerView = findViewById(R.id.home_page_recycler_view);
        mRequestQueue = MySingleton.getInstance(getApplicationContext()).getRequestQueue();
        quranSubjectSearchText = findViewById(R.id.quran_subject_search_text_view);
        toolbarTitle = findViewById(R.id.toolbar_title);
        searchMenu = findViewById(R.id.search_menu);
        titleNum = findViewById(R.id.title_num);
        homeButton = findViewById(R.id.action_main);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionMain(view);
            }
        });
        prefs = this.getSharedPreferences("sorting", Context.MODE_PRIVATE);
        motabiqSearchButton = findViewById(R.id.motabiq_search_button);
        subtitleNum = findViewById(R.id.subtitle_num);
        versesViewPager = findViewById(R.id.verses_viewpager);
        versesPage = findViewById(R.id.verses_page);
        swipeRefreshLayout = findViewById(R.id.activity_main_swipe_refresh_layout);
        swipeRefreshLayout.setEnabled(false);
        subjectName = findViewById(R.id.subject_name);
        searchKind = findViewById(R.id.search_kind);
        drawerTitle = findViewById(R.id.drawer_title);
        String fontPath = "fonts/Al-QuranAlKareem.ttf";
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        quranSubjectSearchText.setTypeface(tf);
        toolbarTitle.setTypeface(tf);
        subjectsAutoCompleteList = new ArrayList<>();
        surasAutoCompleteList = new ArrayList<>();
        versesAutoCompleteList = new ArrayList<>();
        layoutManager = new LinearLayoutManager(this);
        homeRecyclerView.setLayoutManager(layoutManager);
        drawer = findViewById(R.id.drawer_layout);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        titleRecycler = findViewById(R.id.title_recycler);
        subtitleRecycler = findViewById(R.id.subtitle_recycler);
        titleLinearLayout = findViewById(R.id.title_linear_layout);
        subtitleLinearLayout = findViewById(R.id.subtitle_linear_layout);
        versesTabLayout = findViewById(R.id.verses_tabs);
        searchKindTabs = findViewById(R.id.search_kind_tabs);
        connectionLayout = findViewById(R.id.connection_layout);
        setSearchKindTabs();
        TypedArray a = getApplicationContext().getTheme().obtainStyledAttributes(new int[]{android.R.attr.actionBarSize});
        final int actionBarSize = a.getDimensionPixelSize(0, 0);
        a.recycle();
        drawer.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

                if (slideOffset == 0) {
                    ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(floatingTreeButton, "translationX", -drawer.getWidth() + actionBarSize + 50, 0);
                    objectAnimator.setDuration(300);
                    objectAnimator.start();


                }
                if (slideOffset == 1) {
                    ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(floatingTreeButton, "translationX", 0, -drawer.getWidth() + actionBarSize + 50);
                    objectAnimator.setDuration(100);
                    objectAnimator.start();
                }
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        expandedVersesFragment = new ExpandedVersesFragment();
        normalVersesFragment = new NormalVersesFragment();

        subjectsAdapter = new MainSubjectHistoryAdapter(getApplicationContext(), subjectsAutoCompleteList, true, new MainSubjectHistoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Subjects item, int id) {
                switch (id) {
                    case R.id.subject_title_main:
                        quranSubjectSearchText.setOnTouchListener(null);

                        Intent intent = new Intent(getApplicationContext(), VersesActivity.class);
                        if (item.getSubject_type().equals("مرادف موضوع")) {
                            subject_name = item.getSubject();
                            subject_id = item.getMaster();

                        } else {
                            subject_name = item.getSubject();
                            subject_id = item.getID();
                        }
                        saveInPreference(item);
                        getSubjectFromHistory = false;
                        requestVersesData(subject_id, subject_name, "موضوع");

                        break;
                    case R.id.arrow_image:
                        quranSubjectSearchText.setText(item.getSubject());
                        break;

                    default:
                        break;
                }

            }

            @Override
            public void onLongClick(Subjects item, int clickedView) {
                AlertDialog diaBox = AskOption(item);
                diaBox.show();
            }

        });
        homeRecyclerView.setAdapter(subjectsAdapter);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        final int hight = size.y / 2;

        h = 2 * actionBarSize - hight;
        translationHight = h;
        clearText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quranSubjectSearchText.setText("");
                clearText.setVisibility(View.GONE);
            }
        });
        showMainLoading();
        quranSubjectSearchText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                setQuranSearchListener();
                return true;
            }
        });

        quranSubjectSearchText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    if (searchType.equals("كلمة")) {
                        getSubjectFromHistory = false;
                        requestVersesData("", quranSubjectSearchText.getText().toString(), "كلمة");

                    }
                }
                return false;

            }
        });
        quranSubjectSearchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int
                    count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

                if (s.toString().equals("")) {
                    clearText.setVisibility(View.GONE);
                    if (searchType.equals("موضوع")) {
                        fromHistory = true;
                        getHistorySearch();
                    }
                } else {
                    showClearTextImage();
                    handler.removeMessages(TRIGGER_AUTO_COMPLETE);
                    handler.sendEmptyMessageDelayed(TRIGGER_AUTO_COMPLETE,
                            AUTO_COMPLETE_DELAY);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("")) {
                    clearText.setVisibility(View.GONE);
                    if (searchType.equals("موضوع")) {
                        fromHistory = true;
                        getHistorySearch();
                    }
                } else {
                    showClearTextImage();
                }
                quranSubjectSearchText.setSelection(s.length());
            }
        });
        motabiqSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (searchType.equals("كلمة") && !quranSubjectSearchText.getText().toString().equals("")) {
                    getSubjectFromHistory = false;
                    requestVersesData("", quranSubjectSearchText.getText().toString(), "كلمة");
                }
            }
        });
//        subjectsAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
////                Toast.makeText(MainActivity.this, subjectsAutoCompleteList[i].getID(), Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(getApplicationContext(), VersesActivity.class);
//                String subject_name, subject_id;
//                if (subjectsAutoCompleteList.get(i).getSubject_type().equals("مرادف موضوع")) {
//                    subject_name = subjectsAutoCompleteList.get(i).getSubject();
//                    subject_id = subjectsAutoCompleteList.get(i).getMaster();
//
//                } else {
//                    subject_name = subjectsAutoCompleteList.get(i).getSubject();
//                    subject_id = subjectsAutoCompleteList.get(i).getID();
//                }
//
//                intent.putExtra("subject_id", subject_id);
//                intent.putExtra("subject_name", subject_name);
//                saveInPreference(subjectsAutoCompleteList.get(i));
//                startActivity(intent);
//            }
//        });

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == TRIGGER_AUTO_COMPLETE) {
                    if (status == MainActivity.SECOND_STATE_WITH_SEARCH_KIND_LAYOUT) {
                        if (!TextUtils.isEmpty(quranSubjectSearchText.getText()) && !noRequest) {
                            fromHistory = false;
                            String text = quranSubjectSearchText.getText().toString();
                            switch (searchType) {
                                case "موضوع":
                                    subjectAutoCompleteRequest(text);
                                    break;
                                case "سورة":
                                    suraAutoCompleteRequest(text);
                                    break;
                                case "آية":
                                    verseAutoCompleteRequest(text);
                                    break;
                                case "كلمة":
                                    break;
                            }
                        }
                    }
                }
                return false;
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });

        actionSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                intent.putExtra("subject", subject_name);
                intent.putExtra("subject_id", subject_id);
                intent.putExtra("activity", "main");
                intent.putExtra("searchType", searchType);
                startActivityForResult(intent, 1);
            }
        });
        actionQuran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), QuranActivity.class);
                startActivity(intent);
            }
        });

        actionContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, "info@quransubjects.com");
                intent.putExtra(Intent.EXTRA_SUBJECT, "الموضوع...");
                intent.putExtra(Intent.EXTRA_TEXT, "نص الموضوع...");

                startActivity(Intent.createChooser(intent, "Send Email"));
            }
        });

    }
    private void setQuranSearchListener() {

        showKeyboard();
        quranSubjectSearchText.setCursorVisible(true);
        switch (status) {
            case MainActivity.FIRST_STATE:
                toolbarTitle.setText("بحث عن " + getString(R.string.subject));

                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(searchLayout, "translationY", 0, h);
                objectAnimator.setDuration(800);
                objectAnimator.start();
                mafateehLogo.setVisibility(View.GONE);

                objectAnimator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        quranSubjectSearchText.setVisibility(View.VISIBLE);
                        status = SECOND_STATE_WITH_SEARCH_KIND_LAYOUT;
                        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                        TabLayout.Tab tab = searchKindTabs.getTabAt(0);
                        tab.select();

                        quranSubjectSearchText.requestFocus();
                        homeRecyclerView.setVisibility(View.VISIBLE);
                        fromHistory = true;
                        searchKind.setVisibility(View.VISIBLE);
                        getHistorySearch();
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
                break;
            case MainActivity.SECOND_STATE_WITH_SEARCH_KIND_LAYOUT:
                connectionLayout.setVisibility(View.GONE);
                if (quranSubjectSearchText.getText().toString().equals("")) {
                    fromHistory = true;
                    if (searchType.equals("موضوع")) {
                        getHistorySearch();
                    }
                } else {
                    clearText.setVisibility(View.VISIBLE);
                }
                break;
            case MainActivity.THIRD_STATE_WITH_VERSES_SURAS_TABS:
                clearText.setVisibility(View.VISIBLE);
                versesPage.setVisibility(View.GONE);
                versesTabLayout.setVisibility(View.GONE);
                searchKind.setVisibility(View.VISIBLE);
                if (searchKindTabs.getSelectedTabPosition() != 1) {
                    homeRecyclerView.setVisibility(View.VISIBLE);
                } else {
                    homeRecyclerView.setVisibility(View.GONE);
                    motabiqSearchButton.setVisibility(View.VISIBLE);
                }
                status = MainActivity.SECOND_STATE_WITH_SEARCH_KIND_LAYOUT;
                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                break;

        }
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
    private void showMainLoading() {

        loading = findViewById(R.id.bookloading);
        loading.setVisibility(View.VISIBLE);
        loading.start();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                qurans = Quran.listAll(Quran.class);
                if (qurans.size() != 6236) {
                    lastProgress = qurans.size();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            quranInstall.setVisibility(View.VISIBLE);
                            loading.setVisibility(View.GONE);
                            numberProgressBar.setProgress(lastProgress);
                        }
                    });

                    new MyTask().execute("");
                } else {
                    int last = 1;
                    List<Quran> list = Quran.find(Quran.class, "id = 1");
                    String bismillah = list.get(0).getVerse();
                    if (quranPages == null) {
                        quranPages = new String[605];
                        int index = 0;
                        for (Quran q : qurans) {
                            index = q.getPage() - 1;
                            if (quranPages[index] == null) {
                                quranPages[index] = "";
                            }
//                            if(q.getPage()!=1 && quranPages[q.getPage()-1].equals("") ){
//                                quranPages[q.getPage()-2]+="\nصفحة - "+(q.getPage()-2)+" -";
//                            }
                            if (q.getVerseID() == 1) {
                                quranPages[index] += "\n\t" + q.getSura() + "\n";

                                if (!q.getSura().equals("سورة   الفاتحة")) {
                                    quranPages[index] += "\t" + bismillah + "\n";
                                }
                            }
                            quranPages[index] += q.getVerse() + " (" + q.getVerseID() + ") ";
                        }
//                        for(String q : quranPages){
//                            if(q!=null) {
//                                SpannableString str = new SpannableString(q);
//                                int indexOf = q.indexOf("@");
//                                str.setSpan(new ImageSpan(getResources().getDrawable(R.drawable.collapse_arrow)), indexOf, indexOf + 1, ImageSpan.ALIGN_BASELINE);
//                            }
//                        }
//                        quranPages[603]+="\nصفحة - 604 -";
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            loading.setVisibility(View.GONE);
                            quranSubjectSearchText.setVisibility(View.VISIBLE);
                            actionQuran.setVisibility(View.VISIBLE);
                            actionContactUs.setVisibility(View.VISIBLE);
                            quranInstall.setVisibility(View.GONE);
                        }
                    });

                }
            }
        });
    }

    private void subjectAutoCompleteRequest(String text) {
        disableTabLayout(searchKindTabs);
        searchProgressBar.setVisibility(View.VISIBLE);
        params.clear();
        params.put("term", text);
        GsonRequest<SubjectsSearch> subjectsGsonRequest = new GsonRequest<SubjectsSearch>(subjectAutoCompleteUrl, Request.Method.POST, SubjectsSearch.class, null, params, new Response.Listener<SubjectsSearch>() {
            @Override
            public void onResponse(SubjectsSearch response) {
                if (response != null) {
                    enableTabLayout(searchKindTabs);
                    JsonElement data = response.getData();
                    subjectsAutoCompleteList = new ArrayList<>(Arrays.asList(gson.fromJson(data, Subjects[].class)));
                    ArrayList<String> subjectTextList = new ArrayList<>();
                    for (Subjects subject : subjectsAutoCompleteList) {
                        subjectTextList.add(subject.getSubject());
                    }
                    homeRecyclerView.setVisibility(View.VISIBLE);
                    searchProgressBar.setVisibility(View.GONE);
                    subjectsAdapter = new MainSubjectHistoryAdapter(getApplicationContext(), subjectsAutoCompleteList, fromHistory, new MainSubjectHistoryAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Subjects item, int id) {
                            switch (id) {
                                case R.id.subject_title_main:
                                    quranSubjectSearchText.setOnTouchListener(null);

                                    if (item.getSubject_type().equals("مرادف موضوع")) {
                                        subject_name = item.getSubject();
                                        subject_id = item.getMaster();

                                    } else {
                                        subject_name = item.getSubject();
                                        subject_id = item.getID();
                                    }
                                    quranSubjectSearchText.setOnTouchListener(null);
                                    quranSubjectSearchText.setText(item.getSubject());
                                    saveInPreference(item);
                                    getSubjectFromHistory = false;
                                    requestVersesData(subject_id, subject_name, "موضوع");

                                    break;
                                case R.id.arrow_image:
                                    quranSubjectSearchText.setText(item.getSubject());
                                    break;

                                default:
                                    break;
                            }

                        }

                        @Override
                        public void onLongClick(Subjects item, int clickedView) {
                        }
                    });

                    homeRecyclerView.setAdapter(subjectsAdapter);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                searchProgressBar.setVisibility(View.GONE);
                getErrorMessage(error);
            }
        });
        subjectsGsonRequest.setShouldCache(false);
        subjectsGsonRequest.setTag("main_request");
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(subjectsGsonRequest);

    }


    private void verseAutoCompleteRequest(String text) {
        disableTabLayout(searchKindTabs);
        searchProgressBar.setVisibility(View.VISIBLE);
        params.clear();
        params.put("term", text);
        GsonRequest<SubjectsSearch> versesGsonRequest = new GsonRequest<SubjectsSearch>(verseAutoCompleteUrl, Request.Method.POST, SubjectsSearch.class, null, params, new Response.Listener<SubjectsSearch>() {
            @Override
            public void onResponse(SubjectsSearch response) {
                if (response != null) {
                    enableTabLayout(searchKindTabs);
                    JsonElement data = response.getData();
                    versesAutoCompleteList = new ArrayList<>(Arrays.asList(gson.fromJson(data, VerseAsSubject[].class)));
                    ArrayList<String> versesTextList = new ArrayList<>();
                    for (VerseAsSubject verse : versesAutoCompleteList) {
                        versesTextList.add(verse.getSubject());
                    }
                    homeRecyclerView.setVisibility(View.VISIBLE);
                    searchProgressBar.setVisibility(View.GONE);
                    verseAdapter = new MainVerseHistoryAdapter(getApplicationContext(), versesAutoCompleteList, fromHistory, new MainVerseHistoryAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(VerseAsSubject item, int id) {
                            switch (id) {
                                case R.id.subject_title_main:
                                    quranSubjectSearchText.setOnTouchListener(null);
                                    subject_name = item.getSubject();
                                    subject_id = item.getID() + "";
                                    quranSubjectSearchText.setText(item.getSubject());
                                    getSubjectFromHistory = false;
                                    requestVersesData(subject_id, subject_name, "آية");

                                    break;
                                case R.id.arrow_image:
                                    quranSubjectSearchText.setText(item.getSubject());
                                    break;

                                default:
                                    break;
                            }

                        }

                        @Override
                        public void onLongClick(VerseAsSubject item, int clickedView) {
                        }
                    });

                    homeRecyclerView.setAdapter(verseAdapter);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                searchProgressBar.setVisibility(View.GONE);
                getErrorMessage(error);
            }
        });
        versesGsonRequest.setShouldCache(false);
        versesGsonRequest.setTag("main_request");
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(versesGsonRequest);

    }

    private void suraAutoCompleteRequest(String text) {
        disableTabLayout(searchKindTabs);
        searchProgressBar.setVisibility(View.VISIBLE);
        params.clear();
        params.put("term", text);
        GsonRequest<SubjectsSearch> subjectsGsonRequest = new GsonRequest<SubjectsSearch>(suraAutoCompleteUrl, Request.Method.POST, SubjectsSearch.class, null, params, new Response.Listener<SubjectsSearch>() {
            @Override
            public void onResponse(SubjectsSearch response) {
                if (response != null) {
                    enableTabLayout(searchKindTabs);
                    JsonElement data = response.getData();
                    surasAutoCompleteList = new ArrayList<>(Arrays.asList(gson.fromJson(data, SuraAsSubject[].class)));
                    ArrayList<String> suraTextList = new ArrayList<>();
                    for (SuraAsSubject suraAsSubject : surasAutoCompleteList) {
                        suraTextList.add(suraAsSubject.getSubject());
                    }
                    homeRecyclerView.setVisibility(View.VISIBLE);
                    searchProgressBar.setVisibility(View.GONE);
                    suraAdapter = new MainSuraHistoryAdapter(getApplicationContext(), surasAutoCompleteList, fromHistory, new MainSuraHistoryAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(SuraAsSubject item, int id) {
                            switch (id) {
                                case R.id.subject_title_main:
                                    quranSubjectSearchText.setOnTouchListener(null);
                                    quranSubjectSearchText.setText(item.getSubject());
                                    subject_id = item.getID();
                                    subject_name = item.getSubject();
                                    getSubjectFromHistory = false;
                                    requestVersesData(subject_id, subject_name, "سورة");

                                    break;
                                case R.id.arrow_image:
                                    quranSubjectSearchText.setText(item.getSubject());
                                    break;

                                default:
                                    break;
                            }

                        }

                        @Override
                        public void onLongClick(SuraAsSubject item, int clickedView) {
                        }
                    });

                    homeRecyclerView.setAdapter(suraAdapter);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                searchProgressBar.setVisibility(View.GONE);
                getErrorMessage(error);
            }
        });
        subjectsGsonRequest.setShouldCache(false);
        subjectsGsonRequest.setTag("main_request");
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(subjectsGsonRequest);


    }

    private void setUpDrawerRecycler() {
        String fontPath = "fonts/Al-QuranAlKareem.ttf";
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        subjectName.setText(subject_name);
        subjectName.setTypeface(tf);
        drawerTitle.setTypeface(tf);
        RecyclerView.LayoutManager titlelayoutManager = new LinearLayoutManager(getApplicationContext());
        titleRecycler.setLayoutManager(titlelayoutManager);
        titleRecycler.setItemAnimator(new DefaultItemAnimator());
        titleTreeAdapter = new SubjectTreeAdapter(titlesList, getApplicationContext(), new SubjectTreeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Subjects item, int clickedView) {
                subject_id = item.getID();
                subject_name = item.getSubject();
                versesTabLayout.setupWithViewPager(versesViewPager);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    versesTabLayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                }
//                noRequest = true;
//                quranSubjectSearchText.setText(subject_name);
//                noRequest=false;
                versesPage.setVisibility(View.GONE);
                getSubjectFromHistory = false;
                requestVersesData(subject_id, subject_name, "موضوع");
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        titleRecycler.setAdapter(titleTreeAdapter);
        titleTreeAdapter.notifyDataSetChanged();
        titleRecycler.setVisibility(View.VISIBLE);

        if (!titlesList.isEmpty()) {
            titleNum.setText("متفرع من " + getTextFromNum(titlesList.size()));
            titleNum.setTypeface(tf);
            titleLinearLayout.setVisibility(View.VISIBLE);
        } else {
            titleLinearLayout.setVisibility(View.GONE);

        }

        RecyclerView.LayoutManager subtitlelayoutManager = new LinearLayoutManager(getApplicationContext());
        subtitleRecycler.setLayoutManager(subtitlelayoutManager);
        subtitleRecycler.setItemAnimator(new DefaultItemAnimator());
        subtitleTreeAdapter = new SubjectTreeAdapter(subtitlesList, getApplicationContext(), new SubjectTreeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Subjects item, int clickedView) {
                subject_id = item.getID();
                subject_name = item.getSubject();
                versesTabLayout.setupWithViewPager(versesViewPager);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    versesTabLayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                }
//                noRequest = true;
//                quranSubjectSearchText.setText(subject_name);
//                noRequest=false;
                versesPage.setVisibility(View.GONE);
                getSubjectFromHistory = false;
                requestVersesData(subject_id, subject_name, "موضوع");
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        subtitleRecycler.setAdapter(subtitleTreeAdapter);
        subtitleTreeAdapter.notifyDataSetChanged();
        subtitleRecycler.setVisibility(View.VISIBLE);
        if (!subtitlesList.isEmpty()) {
            subtitleNum.setText("يتفرع منه " + getTextFromNum(subtitlesList.size()));
            subtitleNum.setTypeface(tf);
            subtitleLinearLayout.setVisibility(View.VISIBLE);

        } else {
            subtitleLinearLayout.setVisibility(View.GONE);

        }
    }

    private void requestTreeData() {
        params.clear();
        params.put("subject_id", subject_id);
        GsonRequest<SubjectTree> subjectsTreeGsonRequest = new GsonRequest<SubjectTree>(treeUrl, Request.Method.POST, SubjectTree.class, null, params, new Response.Listener<SubjectTree>() {
            @Override
            public void onResponse(SubjectTree response) {
                if (response != null) {

                    JsonElement titles = response.getTitles();
                    titlesList = new ArrayList<>(Arrays.asList(gson.fromJson(titles, Subjects[].class)));
                    JsonElement subtitles = response.getSubtitles();
                    subtitlesList = new ArrayList<>(Arrays.asList(gson.fromJson(subtitles, Subjects[].class)));
                    JsonElement subject = response.getSubject();
                    searchedSubject = gson.fromJson(subject, Subjects.class);
                    if (searchedSubject != null) {
                        subject_name = searchedSubject.getSubject();
                    }
                    setUpDrawerRecycler();
                    floatingTreeButton.setVisibility(View.VISIBLE);
                    floatingTreeButton.startAnimation(mShakeAnimation);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getErrorMessage(error);
            }
        });
        subjectsTreeGsonRequest.setShouldCache(false);
        subjectsTreeGsonRequest.setTag("main_request");
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(subjectsTreeGsonRequest);


    }

    private void showClearTextImage() {
        clearText.setVisibility(View.VISIBLE);

    }

    private void saveInPreference(final Subjects newSubject) {

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                if (subjectDao.getNumberOfExistance(newSubject.getID()) != 0) {
                    subjectDao.delete(subjectDao.get(newSubject.getID()));
//                    getHistorySearch();
                }
                subjectDao.insert(newSubject);
            }
        });
    }

    private void hideKeyboard() {
        quranSubjectSearchText.setCursorVisible(false);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(quranSubjectSearchText.getWindowToken(), 0);
//        quranSubjectSearchText.setFocusableInTouchMode(false);
//        quranSubjectSearchText.setFocusable(false);
//        quranSubjectSearchText.setCursorVisible(false);

    }

    private void showKeyboard() {
//        quranSubjectSearchText.setCursorVisible(true);
//        quranSubjectSearchText.setFocusableInTouchMode(true);
//        quranSubjectSearchText.setFocusable(true);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(quranSubjectSearchText, InputMethodManager.SHOW_IMPLICIT);
    }


    private void getHistorySearch() {

        mRequestQueue.cancelAll("main_request");
        searchProgressBar.setVisibility(View.GONE);
        AsyncTask<Void, Void, Void> asyncTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                final int stringID = getResources().getIdentifier(searchType, "string", getPackageName());
                switch (stringID) {
                    case R.string.subject:
                        subjectsAutoCompleteList = (ArrayList<Subjects>) subjectDao.getAllSubjects();
                        break;
                    case R.string.word:
                        break;
                    case R.string.verse:
                        break;
                    case R.string.sura:
                        break;
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                fixDataOfTextList();

            }
        }.execute();
    }

    private String getTextFromNum(int size) {
        String message = "";
        switch (size) {
            case 1:
                message = "موضوع واحد";
                break;
            case 2:
                message = "موضوعان";
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                message = size + " مواضيع";
                break;
            default:
                message = size + "موضوع";
                break;
        }
        return message;
    }

    private void fixDataOfTextList() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                subjectTextList.clear();
//                for (Subjects s : subjectsAutoCompleteList) {
//                    subjectTextList.add(s.getSubject());
//                }
                connectionLayout.setVisibility(View.GONE);
                homeRecyclerView.setVisibility(View.VISIBLE);
                subjectsAdapter = new MainSubjectHistoryAdapter(getApplicationContext(), subjectsAutoCompleteList, fromHistory, new MainSubjectHistoryAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Subjects item, int id) {
                        switch (id) {
                            case R.id.subject_title_main:
                                quranSubjectSearchText.setOnTouchListener(null);

                                if (item.getSubject_type().equals("مرادف موضوع")) {
                                    subject_name = item.getSubject();
                                    subject_id = item.getMaster();

                                } else {
                                    subject_name = item.getSubject();
                                    subject_id = item.getID();
                                }
                                quranSubjectSearchText.setText(item.getSubject());
                                saveInPreference(item);
                                versesTabLayout.setupWithViewPager(versesViewPager);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                                    versesTabLayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                                }
                                fromHistory = false;
                                getSubjectFromHistory = false;
                                requestVersesData(subject_id, subject_name, "موضوع");

                                break;
                            case R.id.arrow_image:
                                quranSubjectSearchText.setText(item.getSubject());
                                break;

                            default:
                                break;
                        }
                    }

                    @Override
                    public void onLongClick(Subjects item, int clickedView) {
//                        Toast.makeText(MainActivity.this, "delete", Toast.LENGTH_SHORT).show();
                        AlertDialog diaBox = AskOption(item);
                        diaBox.show();
                    }

                });
                homeRecyclerView.setAdapter(subjectsAdapter);

            }
        });
    }

    private void requestVersesData(String id, String name, String type) {

        hideKeyboard();
        disableTabLayout(searchKindTabs);
        motabiqSearchButton.setVisibility(View.GONE);
        mainProgressBar.setVisibility(View.VISIBLE);
        homeRecyclerView.setVisibility(View.GONE);
        status = MainActivity.THIRD_STATE_WITH_VERSES_SURAS_TABS;
        homeRecyclerView.setVisibility(View.GONE);
        toolbarTitle.setText("بحث عن " + type);
        switch (type) {
            case "موضوع":
                requestTreeData();
                requestSubjectData("subject_id", id);
                floatingTreeButton.setVisibility(View.VISIBLE);
                floatingTreeButton.startAnimation(mShakeAnimation);

                noRequest = true;
                quranSubjectSearchText.setText(name);
                noRequest = false;
                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                if (!getSubjectFromHistory) {
                    searchedSubjectsHistory.add(new SearchedSubject(subject_id, subject_name, "موضوع"));
                }
                break;
            case "سورة":

                requestSuraVersesData("sura_id", id);
                floatingTreeButton.setVisibility(View.GONE);

                noRequest = true;
                quranSubjectSearchText.setText(name);
                noRequest = false;
                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                if (!getSubjectFromHistory) {
                    searchedSubjectsHistory.add(new SearchedSubject(subject_id, subject_name, "سورة"));
                }
                break;
            case "آية":
                requestSubjectData("subject_id", id);
                floatingTreeButton.setVisibility(View.GONE);
                noRequest = true;
                quranSubjectSearchText.setText(name);
                noRequest = false;
                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                if (!getSubjectFromHistory) {
                    searchedSubjectsHistory.add(new SearchedSubject(subject_id, subject_name, "آية"));
                }
                break;
            case "كلمة":
                motabiqSearchButton.setVisibility(View.GONE);
                noRequest = true;
                quranSubjectSearchText.setText(name);
                noRequest = false;
                requestWordMotabik();
                floatingTreeButton.setVisibility(View.GONE);
                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                break;
        }
    }


    private void requestWordMotabik() {
        params.clear();
        params.put("motabeq_title", quranSubjectSearchText.getText().toString());
        GsonRequest<JsonObject> motabiqGsonRequest = new GsonRequest<JsonObject>(wordMotabiqUrl, Request.Method.POST, JsonObject.class, null, params, new Response.Listener<JsonObject>() {
            @Override
            public void onResponse(JsonObject response) {
                Gson gson = new Gson();
                Set<String> s = response.keySet();
                String verses_id = "";
                for (String key : s) {
                    if (key.equals("versesIds")) {
                        motabiqWords = gson.fromJson(response.getAsJsonArray(key), int[].class);
                        verses_id += Arrays.toString(motabiqWords);
                    }
                }
                if (!getSubjectFromHistory) {
                    searchedSubjectsHistory.add(new SearchedSubject(verses_id, quranSubjectSearchText.getText().toString(), "كلمة"));
                }
                requestSubjectData("subject_id", verses_id);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mainProgressBar.setVisibility(View.GONE);
                connectionLayout.setVisibility(View.VISIBLE);
                getErrorMessage(error);
            }
        });
        motabiqGsonRequest.setShouldCache(false);
        motabiqGsonRequest.setTag("main_request");
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(motabiqGsonRequest);

    }

    private void requestSubjectData(String paramName, String val) {
        params.clear();
        if (val.contains("["))
            val = val.replaceAll("\\[|\\]", "");
        params.put(paramName, val);
        GsonRequest<Verses[]> subjectsExpandableGsonRequest = new GsonRequest<Verses[]>(vslinkUrl, Request.Method.POST, Verses[].class, null, params, new Response.Listener<Verses[]>() {
            @Override
            public void onResponse(Verses[] response) {
                if (response != null) {
                    vos.clear();
                    versesList = new ArrayList<>(Arrays.asList(response));
                    VersesOfSuras s = new VersesOfSuras(-1, "", new ArrayList<Verses>());
                    for (Verses verse : versesList) {
                        if (verse.getSuraID() == s.getSuraId()) {
                            s.getVerses().add(verse);
                        } else {
                            s = new VersesOfSuras(verse.getSuraID(), verse.getSura(), new ArrayList<Verses>());
                            s.getVerses().add(verse);
                            vos.add(s);
                        }
                    }
                    enableTabLayout(searchKindTabs);
                    motabiqSearchButton.setVisibility(View.GONE);

                    connectionLayout.setVisibility(View.GONE);
                    mainProgressBar.setVisibility(View.GONE);
                    prefs.edit().putString("sort", "qurani").apply();
                    setupVersesViewPager();
                    setUpVersesTabIcons();
                    versesViewPager.setCurrentItem(1);
                    versesTabLayout.setVisibility(View.VISIBLE);
                    versesPage.setVisibility(View.VISIBLE);
                    searchKind.setVisibility(View.GONE);
                    clearText.setVisibility(View.GONE);
                    hideKeyboard();
                    searchMenu.setVisibility(View.VISIBLE);
                    quranSubjectSearchText.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {

                            setQuranSearchListener();
                            return true;
                        }
                    });
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mainProgressBar.setVisibility(View.GONE);
                connectionLayout.setVisibility(View.VISIBLE);
                getErrorMessage(error);
            }
        });
        subjectsExpandableGsonRequest.setShouldCache(false);
        subjectsExpandableGsonRequest.setTag("main_request");
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(subjectsExpandableGsonRequest);

    }

    private void requestSuraVersesData(String paramName, String val) {
        params.clear();
//        val = val.replaceAll("\\[|\\]", "");
        params.put(paramName, val);
        GsonRequest<Verses[]> subjectsExpandableGsonRequest = new GsonRequest<Verses[]>(suraVersesUrl, Request.Method.POST, Verses[].class, null, params, new Response.Listener<Verses[]>() {
            @Override
            public void onResponse(Verses[] response) {
                if (response != null) {
                    vos.clear();
                    versesList = new ArrayList<>(Arrays.asList(response));
                    VersesOfSuras s = new VersesOfSuras(-1, "", new ArrayList<Verses>());
                    for (Verses verse : versesList) {
                        if (verse.getSuraID() == s.getSuraId()) {
                            s.getVerses().add(verse);
                        } else {
                            s = new VersesOfSuras(verse.getSuraID(), verse.getSura(), new ArrayList<Verses>());
                            s.getVerses().add(verse);
                            vos.add(s);
                        }
                    }
                    motabiqSearchButton.setVisibility(View.GONE);
                    enableTabLayout(searchKindTabs);
                    connectionLayout.setVisibility(View.GONE);
                    mainProgressBar.setVisibility(View.GONE);
                    prefs.edit().putString("sort", "qurani").apply();
                    setupVersesViewPager();
                    setUpVersesTabIcons();
                    versesViewPager.setCurrentItem(1);
                    versesTabLayout.setVisibility(View.VISIBLE);
                    versesPage.setVisibility(View.VISIBLE);
                    searchKind.setVisibility(View.GONE);
                    clearText.setVisibility(View.GONE);
                    hideKeyboard();
                    searchMenu.setVisibility(View.VISIBLE);
                    quranSubjectSearchText.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            setQuranSearchListener();
                            return true;
                        }
                    });
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mainProgressBar.setVisibility(View.GONE);
                connectionLayout.setVisibility(View.VISIBLE);
                getErrorMessage(error);
            }
        });
        subjectsExpandableGsonRequest.setShouldCache(false);
        subjectsExpandableGsonRequest.setTag("main_request");
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(subjectsExpandableGsonRequest);


    }

    private void setUpVersesTabIcons() {
        versesTabLayout.setupWithViewPager(versesViewPager);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            versesTabLayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }
        TypedValue typedValue = new TypedValue();

        TypedArray a = obtainStyledAttributes(typedValue.data, new int[]{R.attr.colorPrimaryDark});
        int color = a.getColor(0, 0);
        a.recycle();
        versesTabLayout.setSelectedTabIndicatorColor(color);
        String fontPath = "fonts/Al-QuranAlKareem.ttf";
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        LinearLayout layout0 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.custom_tab_with_label, null);
        TextView text2 = layout0.findViewById(R.id.tab_text);
        TextView count2 = layout0.findViewById(R.id.tab_count);
        text2.setText(getResources().getString(R.string.suras));
        text2.setTypeface(tf);
        count2.setText("(" + vos.size() + ")");
        count2.setTypeface(tf);
        versesTabLayout.getTabAt(0).setCustomView(layout0);
        LinearLayout layout1 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.custom_tab_with_label, null);
        TextView text1 = layout1.findViewById(R.id.tab_text);
        TextView count1 = layout1.findViewById(R.id.tab_count);
        text1.setText(getResources().getString(R.string.verses));
        text1.setTypeface(tf);
        count1.setText("(" + versesList.size() + ")");
        count1.setTypeface(tf);
        versesTabLayout.getTabAt(1).setCustomView(layout1);
        TabLayout.Tab tab = versesTabLayout.getTabAt(1);
        tab.select();

    }

    private void setSearchKindTabs() {
        searchKindTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mRequestQueue.cancelAll("main_request");
                searchProgressBar.setVisibility(View.GONE);
                String search = quranSubjectSearchText.getText().toString();
                searchType = tab.getText().toString();
                switch (searchType) {
                    case "موضوع":
                        motabiqSearchButton.setVisibility(View.GONE);

                        quranSubjectSearchText.setHint(getString(R.string.choose_search_word));
                        toolbarTitle.setText("بحث عن " + getString(R.string.subject));
                        if (!search.equals("")) {
                            subjectAutoCompleteRequest(search);
                        } else {
                            getHistorySearch();
                            if (subjectsAdapter != null) {
                                subjectsAdapter.notifyDataSetChanged();
                            }
                            homeRecyclerView.setVisibility(View.VISIBLE);
                        }
                        break;
                    case "سورة":
                        motabiqSearchButton.setVisibility(View.GONE);

                        quranSubjectSearchText.setHint(getString(R.string.search_sura));
                        toolbarTitle.setText("بحث عن " + getString(R.string.sura));
                        if (search.equals("")) {
                            homeRecyclerView.setVisibility(View.GONE);
                        } else {
                            suraAutoCompleteRequest(search);
                        }
                        break;
                    case "آية":
                        motabiqSearchButton.setVisibility(View.GONE);

                        quranSubjectSearchText.setHint(getString(R.string.search_verse));
                        toolbarTitle.setText("بحث عن " + getString(R.string.verse));
                        if (search.equals("")) {
                            homeRecyclerView.setVisibility(View.GONE);
                        } else {
                            verseAutoCompleteRequest(search);
                        }
                        break;
                    case "كلمة":
                        motabiqSearchButton.setVisibility(View.VISIBLE);
                        quranSubjectSearchText.setHint(getString(R.string.search_word));
                        toolbarTitle.setText("بحث عن " + getString(R.string.word));
                        homeRecyclerView.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    searchType = "موضوع";
                }
            }
        });
        String fontPath = "fonts/Al-QuranAlKareem.ttf";
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        LinearLayout layout0 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.custom_tab_with_label, null);
        TextView text2 = layout0.findViewById(R.id.tab_text);
        text2.setText(getResources().getString(R.string.subject));
        text2.setTypeface(tf);
        searchKindTabs.getTabAt(0).setCustomView(layout0);
        LinearLayout layout1 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.custom_tab_with_label, null);
        TextView text1 = layout1.findViewById(R.id.tab_text);
        text1.setText(getResources().getString(R.string.word));
        text1.setTypeface(tf);
        searchKindTabs.getTabAt(1).setCustomView(layout1);
//        LinearLayout layout3 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.custom_tab_with_label, null);
//        TextView text3 = layout3.findViewById(R.id.tab_text);
//        text3.setText(getResources().getString(R.string.verse));
//        text3.setTypeface(tf);
//        layout3.setVisibility(View.GONE);
//        searchKindTabs.getTabAt(2).setCustomView(layout3);
        LinearLayout layout4 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.custom_tab_with_label, null);
        TextView text4 = layout4.findViewById(R.id.tab_text);
        text4.setText(getResources().getString(R.string.sura));
        text4.setTypeface(tf);
        searchKindTabs.getTabAt(2).setCustomView(layout4);

        TypedValue typedValue = new TypedValue();

        TypedArray a = obtainStyledAttributes(typedValue.data, new int[]{R.attr.colorPrimaryDark});
        int color = a.getColor(0, 0);
        a.recycle();
        searchKindTabs.setSelectedTabIndicatorColor(color);

    }


    private void setupVersesViewPager() {
        searchKind.setVisibility(View.GONE);


        if (expandedVersesFragment.expandableRecyclerAdapter != null) {
            expandedVersesFragment.setVOS(vos);
        } else {

            Bundle expandedBundle = new Bundle();
            expandedBundle.putParcelableArrayList("vos", (ArrayList<? extends Parcelable>) vos);
            expandedVersesFragment.setArguments(expandedBundle);
            viewPagerAdapter.addFragment(expandedVersesFragment, getResources().getString(R.string.suras));
        }
        Bundle normalBundle = new Bundle();
        normalBundle.putParcelableArrayList("verses", (ArrayList<? extends Parcelable>) versesList);
        normalVersesFragment.setArguments(normalBundle);
        if (viewPagerAdapter.getCount() != 2) {
            viewPagerAdapter.addFragment(normalVersesFragment, getResources().getString(R.string.verses));
        }


        versesViewPager.setAdapter(viewPagerAdapter);
        viewPagerAdapter.notifyDataSetChanged();

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_quran:
            case R.id.action_contact_us:
            case R.id.action_main:

            case R.id.action_help:
            default:

                return super.onOptionsItemSelected(item);
        }
    }

    public void setDefaultLanguage(Context context, String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config,
                context.getResources().getDisplayMetrics());

    }

    private AlertDialog AskOption(final Subjects item) {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                //set message, title, and icon
                .setTitle("حذف")
                .setMessage("هل تريد حذف الموضوع من بياناتك:" + item.getSubject())
                .setIcon(R.drawable.delete)

                .setPositiveButton("حذف", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
                                if (subjectDao.getNumberOfExistance(item.getID()) != 0) {
                                    subjectDao.delete(item);
                                    subjectsAutoCompleteList.remove(item);
                                    fixDataOfTextList();
                                }
                            }
                        });
                        dialog.dismiss();
                    }

                })


                .setNegativeButton("الغاء", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();
        return myQuittingDialogBox;

    }

    public static void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    public void toggleDrawer(View view) {

        if (drawer.isDrawerOpen(GravityCompat.START)) {

            drawer.closeDrawer(GravityCompat.START);
        } else {
            drawer.openDrawer(GravityCompat.START);
        }
    }

    public void actionMain(View view) {
        searchedSubjectsHistory.clear();
        switch (status) {
            case MainActivity.SECOND_STATE_WITH_SEARCH_KIND_LAYOUT:
                onBackPressed();
                break;
            case MainActivity.THIRD_STATE_WITH_VERSES_SURAS_TABS:
                onBackPressed();
                break;
            default:
                break;
        }
    }

    private void getErrorMessage(VolleyError volleyError) {
        quranSubjectSearchText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                quranSubjectSearchText.setTextIsSelectable(true);
                showKeyboard();
                quranSubjectSearchText.setCursorVisible(true);
                switch (status) {
                    case MainActivity.FIRST_STATE:
                        toolbarTitle.setText("بحث عن " + getString(R.string.subject));

                        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(searchLayout, "translationY", 0, h);
                        objectAnimator.setDuration(800);
                        objectAnimator.start();
                        mafateehLogo.setVisibility(View.GONE);

                        objectAnimator.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animator) {
                                quranSubjectSearchText.setVisibility(View.VISIBLE);
                                status = SECOND_STATE_WITH_SEARCH_KIND_LAYOUT;
                                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                                TabLayout.Tab tab = searchKindTabs.getTabAt(0);
                                tab.select();

                                quranSubjectSearchText.requestFocus();
                                homeRecyclerView.setVisibility(View.VISIBLE);
                                fromHistory = true;
                                searchKind.setVisibility(View.VISIBLE);
                                getHistorySearch();
                            }

                            @Override
                            public void onAnimationCancel(Animator animator) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animator) {

                            }
                        });
                        break;
                    case MainActivity.SECOND_STATE_WITH_SEARCH_KIND_LAYOUT:
                        connectionLayout.setVisibility(View.GONE);
                        if (quranSubjectSearchText.getText().toString().equals("")) {
                            fromHistory = true;
                            if (searchType.equals("موضوع")) {
                                getHistorySearch();
                            }
                        } else {
                            clearText.setVisibility(View.VISIBLE);
                        }
                        break;
                    case MainActivity.THIRD_STATE_WITH_VERSES_SURAS_TABS:
                        clearText.setVisibility(View.VISIBLE);
                        versesPage.setVisibility(View.GONE);
                        versesTabLayout.setVisibility(View.GONE);
                        searchKind.setVisibility(View.VISIBLE);
                        if (searchKindTabs.getSelectedTabPosition() != 1) {
                            homeRecyclerView.setVisibility(View.VISIBLE);
                        } else {
                            homeRecyclerView.setVisibility(View.GONE);
                            motabiqSearchButton.setVisibility(View.VISIBLE);
                        }
                        status = MainActivity.SECOND_STATE_WITH_SEARCH_KIND_LAYOUT;
                        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                        break;

                }
                return true;
            }
        });
        enableTabLayout(searchKindTabs);
        hideKeyboard();
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
        swipeRefreshLayout.setEnabled(true);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void refreshContent() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideKeyboard();
                enableTabLayout(searchKindTabs);
                if (!searchedSubjectsHistory.isEmpty()) {
                    SearchedSubject s = searchedSubjectsHistory.get(searchedSubjectsHistory.size() - 1);
                    getSubjectFromHistory = true;
                    requestVersesData(s.getSearched_subject_id(), s.getSearched_subject_name(), s.getSearched_Type());
                }

                swipeRefreshLayout.setRefreshing(false);
                swipeRefreshLayout.setEnabled(false);
            }
        }, 100);
    }

    public void disableTabLayout(TabLayout tabLayout) {

        LinearLayout tabStrip = ((LinearLayout) tabLayout.getChildAt(0));
        tabStrip.setEnabled(false);
        for (int i = 0; i < tabStrip.getChildCount(); i++) {
            tabStrip.getChildAt(i).setClickable(false);
        }
    }

    public void enableTabLayout(TabLayout tabLayout) {
        LinearLayout tabStrip = ((LinearLayout) tabLayout.getChildAt(0));
        tabStrip.setEnabled(true);
        for (int i = 0; i < tabStrip.getChildCount(); i++) {
            tabStrip.getChildAt(i).setClickable(true);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.clear();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && !versesList.isEmpty()) {

            if (data.getStringExtra("sort") != null) {
                switch (data.getStringExtra("sort")) {
                    case "qurani":
                        Collections.sort(versesList, new Comparator<Verses>() {
                            @Override
                            public int compare(Verses s1, Verses s2) {
                                if (s1.getSura() == s2.getSura()) {
                                    if (s1.getVerseID() < s2.getVerseID()) {
                                        return -1;
                                    } else {
                                        return 1;
                                    }
                                } else {
                                    if (s1.getSuraID() < s2.getSuraID()) {
                                        return -1;
                                    } else {
                                        return 1;
                                    }
                                }
                            }
                        });

                        break;
                    case "alphabetical":
                        Collections.sort(versesList, new Comparator<Verses>() {
                            @Override
                            public int compare(Verses s1, Verses s2) {
                                return s1.getVerse().compareToIgnoreCase(s2.getVerse());
                            }
                        });
                        break;
                    case "makki-madani":
                        Collections.sort(versesList, new Comparator<Verses>() {
                            @Override
                            public int compare(Verses s1, Verses s2) {
                                if (s1.getSura().contains("مدنية") && s2.getSura().contains("مكية")) {
                                    return 1;
                                }
                                if (s1.getSura().contains("مكية") && s2.getSura().contains("مدنية")) {
                                    return -1;
                                }

                                return 0;
                            }
                        });
                        break;
                }
                setupVersesViewPager();
                setUpVersesTabIcons();

                prefs.edit().putString("sort", data.getStringExtra("sort")).apply();
            }
        }
    }


    private class MyTask extends AsyncTask<String, Integer, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            String json = null;
            int progress = lastProgress;
            try {
                Gson gson = new Gson();
                InputStream inputStream = getAssets().open("quran-verses.json");
                int size = inputStream.available();
                byte[] buffer = new byte[size];
                inputStream.read(buffer);
                inputStream.close();
                json = new String(buffer, "UTF-8");
                JSONObject jsonObject = new JSONObject(json);
                JSONArray verses = jsonObject.getJSONArray("verses");
                Quran q = null;
                for (int j = lastProgress; j < verses.length(); j++) {
                    q = gson.fromJson(String.valueOf(verses.getJSONObject(j)), Quran.class);
                    progress = (100 * j) / 6236;
                    publishProgress(progress);
                    q.save();
                    Log.d("Verse", "*** " + q.getVerse());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            quranInstall.setVisibility(View.GONE);
            loading.setVisibility(View.VISIBLE);
            showMainLoading();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            numberProgressBar.setProgress(values[0]);
        }
    }


}
