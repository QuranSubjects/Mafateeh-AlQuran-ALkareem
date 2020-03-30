package com.example.lenovo.quransubjectsapp.fragments;

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
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.lenovo.quransubjectsapp.R;
import com.example.lenovo.quransubjectsapp.TafaseerActivity;
import com.example.lenovo.quransubjectsapp.VersesActivity;
import com.example.lenovo.quransubjectsapp.adapters.SubjectTreeAdapter;
import com.example.lenovo.quransubjectsapp.models.Subjects;
import com.example.lenovo.quransubjectsapp.models.Tafseer;
import com.example.lenovo.quransubjectsapp.volley.GsonRequest;
import com.example.lenovo.quransubjectsapp.volley.MySingleton;

import java.util.HashMap;
import java.util.Map;

public class TafaseerFragment extends Fragment {
   public static WebView tafseerText;

    static Context context;

    public TafaseerFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tafseer_fragment, container, false);
        context = getContext();
        StringBuilder data = new StringBuilder();
        data .append("<HTML style=\"direction:rtl;font-family: traditional arabic;line-height: 1.5em !important;color: rgb(0, 0, 0) !important;font-size: 18px !important;white-space: pre-wrap;\">");
        data .append(getArguments().getString("tafseer"));
        data .append("</HTML>");
        tafseerText = rootView.findViewById(R.id.tafseer_text);

        tafseerText .setWebViewClient(new WebViewClient());

//        tafseerText .getSettings().setUseWideViewPort(true);
//        tafseerText .getSettings().setLoadWithOverviewMode(true);

        tafseerText .getSettings().setSupportZoom(true);
//        tafseerText .getSettings().setBuiltInZoomControls(true);
//        tafseerText .getSettings().setDisplayZoomControls(false);
        tafseerText.loadData(data.toString(),"text/html; charset=utf-8", "UTF-8");
        return rootView;
    }

}
