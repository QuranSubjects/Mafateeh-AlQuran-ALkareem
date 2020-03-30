package com.example.lenovo.quransubjectsapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class SubjectTreeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_tree);
        String subject_id = getIntent().getStringExtra("subject_id");
        WebView subjectTree = findViewById(R.id.subject_tree_webview);
        String url = "https://quransubjects.com/ng--QuranService/web/subjects/subject-tree";
        String postData = null;
        try {
            postData = "subject_id=" + URLEncoder.encode(subject_id, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        subjectTree.postUrl(url, postData.getBytes());
    }
}
