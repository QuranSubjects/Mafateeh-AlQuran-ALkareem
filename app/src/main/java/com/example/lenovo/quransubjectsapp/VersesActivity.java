package com.example.lenovo.quransubjectsapp;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.lenovo.quransubjectsapp.adapters.MainSubjectHistoryAdapter;
import com.example.lenovo.quransubjectsapp.adapters.SubjectDatabase;
import com.example.lenovo.quransubjectsapp.adapters.SubjectTreeAdapter;
import com.example.lenovo.quransubjectsapp.adapters.ViewPagerAdapter;
import com.example.lenovo.quransubjectsapp.fragments.ExpandedVersesFragment;
import com.example.lenovo.quransubjectsapp.fragments.NormalVersesFragment;
import com.example.lenovo.quransubjectsapp.interfaces.SubjectDao;
import com.example.lenovo.quransubjectsapp.models.SubjectTree;
import com.example.lenovo.quransubjectsapp.models.Subjects;
import com.example.lenovo.quransubjectsapp.models.SubjectsSearch;
import com.example.lenovo.quransubjectsapp.models.Verses;
import com.example.lenovo.quransubjectsapp.models.VersesOfSuras;
import com.example.lenovo.quransubjectsapp.volley.GsonRequest;
import com.example.lenovo.quransubjectsapp.volley.MySingleton;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.blox.graphview.GraphView;

public class VersesActivity extends AppCompatActivity {
    Boolean noRequest = false;
    Subjects searchedSubject;
    DrawerLayout drawer;
    ArrayList<VersesOfSuras> vos = new ArrayList<>();
    static final int FIRST_STATE = 0;
    static final int SECOND_STATE_WITH_SEARCH_KIND_LAYOUT = 1;
    static final int THIRD_STATE_WITH_VERSES_SURAS_TABS = 2;
    TextView drawerTitle;
    ArrayList<Subjects> subjectsList = new ArrayList<>();
    GraphView graphView;
    Animation mShakeAnimation;
    Map<String, String> params = new HashMap<String, String>();
    String treeUrl = "http://quransubjects.com/ng--QuranService/web/subjects/subject-file";
    String expandableRecyclerUrl = "http://quransubjects.com/ng--QuranService/web/subjects/vslink";
    String subject_id, subject_name;
    List<Subjects> titlesList;
    List<Subjects> subtitlesList;
    List<Verses> versesList;
    Gson gson = new Gson();
    FloatingActionButton floatingTreeButton;
    TextView noDataTreeText;
    int actionBarSize;
    RelativeLayout versesPage;
    ProgressBar versesProgress;
    TextView subjectName;
    private TabLayout versesTabLayout;
    ViewPager versesViewPager;
    RecyclerView titleRecycler, subtitleRecycler;
    SubjectTreeAdapter titleTreeAdapter, subtitleTreeAdapter;
    LinearLayout titleLinearLayout, subtitleLinearLayout;
    TextView titleNum, subtitleNum;
    EditText subjectSearch;
    ImageView clear;
    ProgressBar progressSearch;
    RecyclerView searchRecyclerView;
    MainSubjectHistoryAdapter searchAdapter;
    SubjectDao subjectDao;
    RelativeLayout searchLayout;
    RequestQueue mRequestQueue;
    Boolean fromHistory = true;
    int translationHight;
    static final int SEARCH_STATE_BEFORE_CLICK = 0;
    static final int SEARCH_STATE_AFTER_CLICK_WITH_KEYBOARD_CLOSE = 1;
    static final int SEARCH_STATE_AFTER_CLICK_WITH_KEYBOARD_OPEN = 2;
    int status = SEARCH_STATE_BEFORE_CLICK;
    Handler handler;
    private static final int TRIGGER_AUTO_COMPLETE = 100;
    private static final long AUTO_COMPLETE_DELAY = 300;
    Gson gsonRequest = new Gson();
    String url = "http://quransubjects.com/ng--QuranService/web/api/subjects/subjects";
    TextView horizoTextView;
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
        setContentView(R.layout.activity_verses);
        drawer = findViewById(R.id.drawer_layout);
        mShakeAnimation=AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);

        floatingTreeButton = findViewById(R.id.floating_tree_button);
        horizoTextView = findViewById(R.id.horizontal_line);

        versesPage = findViewById(R.id.verses_page);
        titleRecycler = findViewById(R.id.title_recycler);
        subtitleRecycler = findViewById(R.id.subtitle_recycler);
        titleLinearLayout = findViewById(R.id.title_linear_layout);
        subtitleLinearLayout = findViewById(R.id.subtitle_linear_layout);
        titleNum = findViewById(R.id.title_num);
        subtitleNum = findViewById(R.id.subtitle_num);
//        versesProgress = findViewById(R.id.verses_progress_bar);
        versesViewPager = findViewById(R.id.verses_viewpager);
        versesTabLayout = findViewById(R.id.verses_tabs);
        subjectName = findViewById(R.id.subject_name);
//        drawerTitle = findViewById(R.id.drawer_title);
//        subjectSearch = findViewById(R.id.subject_search);
//        clear = findViewById(R.id.clear_image);
//        searchRecyclerView = findViewById(R.id.search_recycler);
//        progressSearch = findViewById(R.id.search_progress);
        searchLayout = findViewById(R.id.search_layout);
        subjectDao = SubjectDatabase.getDatabase(getApplicationContext()).subjectDao();
        mRequestQueue = MySingleton.getInstance(getApplicationContext()).getRequestQueue();
        TypedArray a = getApplicationContext().getTheme().obtainStyledAttributes(new int[]{android.R.attr.actionBarSize});
        final int actionBarSize = a.getDimensionPixelSize(0, 0);
        a.recycle();
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        final int hight = size.y / 2;
        final int h = actionBarSize - hight + 100;
        translationHight = h;
        initializeSearchBar();
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
        if (getIntent().hasExtra("subject_id") && getIntent().hasExtra("subject_name")) {
            subject_id = getIntent().getStringExtra("subject_id");
        }
        showActionBar();
        versesTabLayout.setupWithViewPager(versesViewPager);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            versesTabLayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }
        requestTreeData();
        requestVersesData();

    }

    @Override
    public void onBackPressed() {

        switch (status) {

            case VersesActivity.SECOND_STATE_WITH_SEARCH_KIND_LAYOUT:
                noRequest=true;
                subjectSearch.setText(subject_name);
                noRequest=false;
                clear.setVisibility(View.GONE);
                searchRecyclerView.setVisibility(View.GONE);
                versesTabLayout.setVisibility(View.VISIBLE);
                horizoTextView.setVisibility(View.VISIBLE);
                floatingTreeButton.setVisibility(View.VISIBLE);
                floatingTreeButton.startAnimation(mShakeAnimation);

                versesViewPager.setVisibility(View.VISIBLE);
                status = SEARCH_STATE_BEFORE_CLICK;

                break;
            case VersesActivity.SEARCH_STATE_BEFORE_CLICK:
                searchRecyclerView.setVisibility(View.GONE);

                super.onBackPressed();
                break;
            default:
                break;
        }
    }

    private void initializeSearchBar() {
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noRequest=true;
                subjectSearch.setText(subject_name);
                noRequest=false;
                clear.setVisibility(View.GONE);
                searchRecyclerView.setVisibility(View.GONE);
                versesTabLayout.setVisibility(View.VISIBLE);
                horizoTextView.setVisibility(View.VISIBLE);
                floatingTreeButton.setVisibility(View.VISIBLE);
                floatingTreeButton.startAnimation(mShakeAnimation);

                versesViewPager.setVisibility(View.VISIBLE);
                status = SEARCH_STATE_BEFORE_CLICK;

            }
        });

        subjectsList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        searchRecyclerView.setLayoutManager(layoutManager);

        searchAdapter = new MainSubjectHistoryAdapter(getApplicationContext(), subjectsList, true, new MainSubjectHistoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Subjects item, int id) {
                switch (id) {
                    case R.id.subject_title_main:
                        Intent intent = new Intent(getApplicationContext(), VersesActivity.class);
                        String subject_name, subject_id;
                        if (item.getSubject_type().equals("مرادف موضوع")) {
                            subject_name = item.getSubject();
                            subject_id = item.getMaster();

                        } else {
                            subject_name = item.getSubject();
                            subject_id = item.getID();
                        }

                        intent.putExtra("subject_id", subject_id);
                        intent.putExtra("subject_name", subject_name);
                        saveInPreference(item);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.arrow_image:
                        subjectSearch.setText(item.getSubject());
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
//        quranSubjectSearchText.setThreshold(0);
//        quranSubjectSearchText.setAdapter(subjectsAdapter);
        searchRecyclerView.setAdapter(searchAdapter);
        subjectSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (status == SEARCH_STATE_BEFORE_CLICK) {
                    versesTabLayout.setVisibility(View.GONE);
                    horizoTextView.setVisibility(View.GONE);
                    floatingTreeButton.setVisibility(View.GONE);
                    versesViewPager.setVisibility(View.GONE);
                    status = SEARCH_STATE_AFTER_CLICK_WITH_KEYBOARD_CLOSE;
                    subjectSearch.setFocusableInTouchMode(true);
                    subjectSearch.requestFocus();
                    subjectSearch.setSelection(subjectSearch.getText().length());
                    searchRecyclerView.setVisibility(View.VISIBLE);
                    showKeyboard();
                    fromHistory = true;
                    getHistorySearch();

                } else {
                    if (subjectSearch.getText().toString().equals("")) {
                        fromHistory = true;
                        getHistorySearch();
                    }
                    showKeyboard();
                }
                return true;
            }
        });

        subjectSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int
                    count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (!noRequest) {
                    if (s.toString().equals("")) {
                        clear.setVisibility(View.GONE);
                        fromHistory = true;
                        getHistorySearch();
                    } else {
                        showClearTextImage();
                        handler.removeMessages(TRIGGER_AUTO_COMPLETE);
                        handler.sendEmptyMessageDelayed(TRIGGER_AUTO_COMPLETE,
                                AUTO_COMPLETE_DELAY);

                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!noRequest) {
                    if (s.toString().equals("")) {
                        clear.setVisibility(View.GONE);
                        fromHistory = true;
                        getHistorySearch();
                    } else {
                        showClearTextImage();
                    }
                    subjectSearch.setSelection(s.length());

                }
            }
        });

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == TRIGGER_AUTO_COMPLETE) {
                    if (!TextUtils.isEmpty(subjectSearch.getText())) {
                        fromHistory = false;
                        makeRequest(subjectSearch.getText().toString());
                    }
                }
                return false;
            }
        });


    }

    private void showKeyboard() {

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(subjectSearch, InputMethodManager.SHOW_IMPLICIT);
    }

    private void showClearTextImage() {
        clear.setVisibility(View.VISIBLE);

    }

    private void saveInPreference(final Subjects newSubject) {

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                if (subjectDao.getNumberOfExistance(newSubject.getID()) != 0) {
                    subjectDao.delete(subjectDao.get(newSubject.getID()));
                    getHistorySearch();
                }
                subjectDao.insert(newSubject);
            }
        });
    }

    private void getHistorySearch() {
        mRequestQueue.cancelAll("main_request");
        progressSearch.setVisibility(View.GONE);
        AsyncTask<Void, Void, Void> asyncTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                subjectsList = (ArrayList<Subjects>) subjectDao.getAllSubjects();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                fixDataOfTextList();

            }
        }.execute();
    }

    private void fixDataOfTextList() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                subjectTextList.clear();
//                for (Subjects s : subjectsAutoCompleteList) {
//                    subjectTextList.add(s.getSubject());
//                }

//                searchRecyclerView.setVisibility(View.VISIBLE);
                searchAdapter = new MainSubjectHistoryAdapter(getApplicationContext(), subjectsList, fromHistory, new MainSubjectHistoryAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Subjects item, int id) {
                        switch (id) {
                            case R.id.subject_title_main:
                                Intent intent = new Intent(getApplicationContext(), VersesActivity.class);
                                String subject_name, subject_id;
                                if (item.getSubject_type().equals("مرادف موضوع")) {
                                    subject_name = item.getSubject();
                                    subject_id = item.getMaster();

                                } else {
                                    subject_name = item.getSubject();
                                    subject_id = item.getID();
                                }

                                intent.putExtra("subject_id", subject_id);
                                intent.putExtra("subject_name", subject_name);
                                subjectSearch.setText(item.getSubject());
                                saveInPreference(item);
                                startActivity(intent);
                                finish();

                                break;
                            case R.id.arrow_image:
                                subjectSearch.setText(item.getSubject());
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
                searchRecyclerView.setAdapter(searchAdapter);

            }
        });
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
                                    subjectsList.remove(item);
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

    private void makeRequest(String s) {
        progressSearch.setVisibility(View.VISIBLE);
        params.put("term", s);
        GsonRequest<SubjectsSearch> subjectsGsonRequest = new GsonRequest<SubjectsSearch>(url, Request.Method.POST, SubjectsSearch.class, null, params, new Response.Listener<SubjectsSearch>() {
            @Override
            public void onResponse(SubjectsSearch response) {
                if (response != null) {
                    JsonElement data = response.getData();
//                    subjectTextList.clear();
                    subjectsList = new ArrayList<>(Arrays.asList(gsonRequest.fromJson(data, Subjects[].class)));
                    ArrayList<String> subjectTextList = new ArrayList<>();
                    for (Subjects subject : subjectsList) {
                        subjectTextList.add(subject.getSubject());
                    }
                    searchRecyclerView.setVisibility(View.VISIBLE);

//                    subjectsAdapter.setSubjects(subjectsAutoCompleteList);
                    progressSearch.setVisibility(View.GONE);
//                    subjectsAdapter.notifyDataSetChanged();


                    searchAdapter = new MainSubjectHistoryAdapter(getApplicationContext(), subjectsList, fromHistory, new MainSubjectHistoryAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Subjects item, int id) {
                            switch (id) {
                                case R.id.subject_title_main:
                                    Intent intent = new Intent(getApplicationContext(), VersesActivity.class);
                                    String subject_name, subject_id;
                                    if (item.getSubject_type().equals("مرادف موضوع")) {
                                        subject_name = item.getSubject();
                                        subject_id = item.getMaster();

                                    } else {
                                        subject_name = item.getSubject();
                                        subject_id = item.getID();
                                    }

                                    intent.putExtra("subject_id", subject_id);
                                    intent.putExtra("subject_name", subject_name);
                                    subjectSearch.setText(item.getSubject());
                                    saveInPreference(item);
                                    startActivity(intent);
                                    finish();
                                    break;
                                case R.id.arrow_image:
                                    subjectSearch.setText(item.getSubject());
                                    break;

                                default:
                                    break;
                            }

                        }

                        @Override
                        public void onLongClick(Subjects item, int clickedView) {
                        }
                    });

                    searchRecyclerView.setAdapter(searchAdapter);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressSearch.setVisibility(View.GONE);
                Toast.makeText(VersesActivity.this, getResources().getString(R.string.check_connection), Toast.LENGTH_SHORT).show();
            }
        });
        subjectsGsonRequest.setShouldCache(false);
        subjectsGsonRequest.setTag("main_request");
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(subjectsGsonRequest);

    }

    private void setUpDrawerTabIcons() {
//        TextView text1 = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
//        text1.setText(getResources().getString(R.string.titles));
//        text1.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
//        text1.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.child_, 0);
//        drawerTabLayout.getTabAt(0).setCustomView(text1);
//        TextView text2 = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
//        text2.setText(getResources().getString(R.string.subtitle));
//        text2.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
//        text2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.parent, 0, 0, 0);
//        drawerTabLayout.getTabAt(1).setCustomView(text2);

    }

    private void setUpVersesTabIcons() {
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


    }

    private void setupDrawerViewPager(ViewPager viewPager) {
//        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
//        DrawerTreeFragment fragment1 = new DrawerTreeFragment();
//        Bundle bundle1 = new Bundle();
//        bundle1.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) titlesList);
//        fragment1.setArguments(bundle1);
//        DrawerTreeFragment fragment2 = new DrawerTreeFragment();
//        Bundle bundle2 = new Bundle();
//        bundle2.putParcelableArrayList("list", (ArrayList<Subjects>) subtitlesList);
//        fragment2.setArguments(bundle2);
//        adapter.addFragment(fragment1, getResources().getString(R.string.titles));
//        adapter.addFragment(fragment2, getResources().getString(R.string.subtitle));
//        viewPager.setAdapter(adapter);
    }

    private void setupVersesViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        ExpandedVersesFragment expandedVersesFragment = new ExpandedVersesFragment();
        NormalVersesFragment normalVersesFragment = new NormalVersesFragment();
        Bundle expandedBundle = new Bundle();
        expandedBundle.putParcelableArrayList("vos", (ArrayList<? extends Parcelable>) vos);
        expandedVersesFragment.setArguments(expandedBundle);
        Bundle normalBundle = new Bundle();
        normalBundle.putParcelableArrayList("verses", (ArrayList<? extends Parcelable>) versesList);
        normalVersesFragment.setArguments(normalBundle);
        adapter.addFragment(expandedVersesFragment, getResources().getString(R.string.suras));
        adapter.addFragment(normalVersesFragment, getResources().getString(R.string.verses));
        viewPager.setAdapter(adapter);
    }

//    private void drawGraph() {
//        runOnUiThread(new Runnable() {
//
//            @Override
//            public void run() {
//                final Graph graph = new Graph();
//
//                Node parentNode = new Node(subject_name);
//                Node node;
//                for (Subjects subject : subtitlesList
//                        ) {
//                    node = new Node(subject.getSubject());
//                    graph.addEdge(parentNode, node);
//                }
//                final BaseGraphAdapter<ViewHolder> adapter = new BaseGraphAdapter<ViewHolder>(getApplicationContext(), R.layout.tree_node, graph) {
//
//                    @NonNull
//                    @Override
//                    public ViewHolder onCreateViewHolder(View view) {
//                        return new ViewHolder(view);
//                    }
//
//                    @Override
//                    public void onBindViewHolder(ViewHolder viewHolder, Object data, final int position) {
//
//                        viewHolder.mTextView.setText(data.toString());
//                        viewHolder.mTextView.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                Toast.makeText(VersesActivity.this, graph.getNode(position).getData() + "", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
//                };
//
//
//                // set the algorithm here
//                final BuchheimWalkerConfiguration configuration = new BuchheimWalkerConfiguration.Builder()
//                        .setSiblingSeparation(20)
//                        .setLevelSeparation(150)
//                        .setSubtreeSeparation(100)
//                        .setOrientation(BuchheimWalkerConfiguration.ORIENTATION_TOP_BOTTOM)
//                        .build();
//                adapter.setAlgorithm(new BuchheimWalkerAlgorithm(configuration));
//
//                graphView.setAdapter(adapter);
//                floatingTreeButton.setVisibility(View.VISIBLE);
//            }
//        });
//    }


    private void requestTreeData() {
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
                    subject_name = searchedSubject.getSubject();
                    setUpDrawerRecycler();
                    floatingTreeButton.setVisibility(View.VISIBLE);
                    floatingTreeButton.startAnimation(mShakeAnimation);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(VersesActivity.this, getResources().getString(R.string.check_connection), Toast.LENGTH_SHORT).show();
            }
        });
        subjectsTreeGsonRequest.setShouldCache(false);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(subjectsTreeGsonRequest);


    }

    private void setUpDrawerRecycler() {
        String fontPath = "fonts/Al-QuranAlKareem.ttf";
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        subjectName.setText(subject_name);
noRequest=true;
        subjectSearch.setText(subject_name);
        noRequest=false;
        subjectSearch.setTypeface(tf);
        subjectName.setTypeface(tf);
        drawerTitle.setTypeface(tf);
        if (!titlesList.isEmpty()) {
            RecyclerView.LayoutManager titlelayoutManager = new LinearLayoutManager(getApplicationContext());
            titleRecycler.setLayoutManager(titlelayoutManager);
            titleRecycler.setItemAnimator(new DefaultItemAnimator());
            titleTreeAdapter = new SubjectTreeAdapter(titlesList, getApplicationContext(), new SubjectTreeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Subjects item, int clickedView) {
                    Intent intent = new Intent(getApplicationContext(), VersesActivity.class);
                    intent.putExtra("subject_id", item.getID());
                    intent.putExtra("subject_name", item.getSubject());
                    startActivity(intent);
                    finish();
                }
            });
            titleRecycler.setAdapter(titleTreeAdapter);
            titleRecycler.setVisibility(View.VISIBLE);
            titleNum.setText("متفرع من " + getTextFromNum(titlesList.size()));
            titleNum.setTypeface(tf);
            titleLinearLayout.setVisibility(View.VISIBLE);
        }
        if (!subtitlesList.isEmpty()) {
            RecyclerView.LayoutManager subtitlelayoutManager = new LinearLayoutManager(getApplicationContext());
            subtitleRecycler.setLayoutManager(subtitlelayoutManager);
            subtitleRecycler.setItemAnimator(new DefaultItemAnimator());
            subtitleTreeAdapter = new SubjectTreeAdapter(subtitlesList, getApplicationContext(), new SubjectTreeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Subjects item, int clickedView) {
                    Intent intent = new Intent(getApplicationContext(), VersesActivity.class);
                    intent.putExtra("subject_id", item.getID());
                    intent.putExtra("subject_name", item.getSubject());
                    startActivity(intent);
                    finish();
                }
            });
            subtitleRecycler.setAdapter(subtitleTreeAdapter);
            subtitleRecycler.setVisibility(View.VISIBLE);
            subtitleNum.setText("يتفرع منه " + getTextFromNum(subtitlesList.size()));
            subtitleNum.setTypeface(tf);
            subtitleLinearLayout.setVisibility(View.VISIBLE);

        }
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
            default:
                message = size + " مواضيع";
                break;
        }
        return message;
    }

    private void requestVersesData() {
        params.put("subject_id", subject_id);
        GsonRequest<Verses[]> subjectsExpandableGsonRequest = new GsonRequest<Verses[]>(expandableRecyclerUrl, Request.Method.POST, Verses[].class, null, params, new Response.Listener<Verses[]>() {
            @Override
            public void onResponse(Verses[] response) {
                if (response != null) {
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
                    setupVersesViewPager(versesViewPager);
                    setUpVersesTabIcons();
                    versesViewPager.setCurrentItem(1);
                    versesPage.setVisibility(View.VISIBLE);
                    versesProgress.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(VersesActivity.this, getResources().getString(R.string.check_connection), Toast.LENGTH_SHORT).show();
            }
        });
        subjectsExpandableGsonRequest.setShouldCache(false);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(subjectsExpandableGsonRequest);


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

    public void hideActionBar() {
        getSupportActionBar().hide();
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


    public void toggleDrawer(View view) {

        if (drawer.isDrawerOpen(GravityCompat.START)) {

            drawer.closeDrawer(GravityCompat.START);
        } else {
            drawer.openDrawer(GravityCompat.START);
        }
    }


    public void serachWordMotabiq(View view) {
    }
}
