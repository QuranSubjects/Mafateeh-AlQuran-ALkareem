package com.example.lenovo.quransubjectsapp.fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.SpannableStringBuilder;
import android.text.style.CharacterStyle;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.lenovo.quransubjectsapp.R;
import com.example.lenovo.quransubjectsapp.TafaseerActivity;
import com.example.lenovo.quransubjectsapp.WordMeaningDialog;
import com.example.lenovo.quransubjectsapp.models.SubjectsSearch;
import com.example.lenovo.quransubjectsapp.models.Sura;
import com.example.lenovo.quransubjectsapp.volley.GsonRequest;
import com.example.lenovo.quransubjectsapp.volley.MySingleton;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.HashMap;
import java.util.Map;


public class QuranPageFragment extends Fragment {
    String text, page;
    TextView pageNum;
    TextView quranPageText;
    TextView suraName, sectionNum;
    String wordMeaningUrl = "http://quransubjects.com/ng--QuranService/web/api/subjects/verse-id-from-text";
    Map<String, String> params = new HashMap<String, String>();
    String aya="";
    public QuranPageFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.quran_page_fragment, container, false);
        quranPageText = rootView.findViewById(R.id.quran_page_text);
        String fontPath = "fonts/Amiri-Regular.ttf";
        text = (String) getArguments().get("text");
        page = (String) getArguments().get("page");
        suraName = rootView.findViewById(R.id.sura_name_id);
        sectionNum = rootView.findViewById(R.id.section_name_id);
        if (page != null) {
            getSuraNameAndSection(Integer.parseInt(page));
        }
        getArguments().clear();
        pageNum = rootView.findViewById(R.id.page_num);
        pageNum.setText(page);
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), fontPath);
        quranPageText.setTypeface(tf);
//        quranPageText.setText(text);
        fontPath = "fonts/Kufi-LT-Regular.ttf";
        tf = Typeface.createFromAsset(getActivity().getAssets(), fontPath);
        pageNum.setTypeface(tf);
        suraName.setTypeface(tf);
        sectionNum.setTypeface(tf);
        quranPageText.setText(text);
        quranPageText.setCustomSelectionActionModeCallback((ActionMode.Callback) new StyleCallback());
        return rootView;
    }

    private void getSuraNameAndSection(int pageNum) {
        int i = 0;
        while (pageNum >= Sura.SURAPAGES[i]) {
            if (pageNum == Sura.SURAPAGES[i]) {
                i++;
                break;
            }
            i++;
            if (i == 115) {
                break;
            }
        }
        i--;

        suraName.setText("سورة " + Sura.SURANAMES[i]);
        int sect = 0;
        if (pageNum == 1) {
            sect = 1;
        } else {
            if (pageNum > 600) {
                sect = 30;
            } else {
                if (pageNum % 20 == 0 || pageNum % 20 == 1) {
                    sect = pageNum / 20;
                }
                else {
                    if (pageNum % 20 != 0) {
                        sect = (pageNum / 20) + 1;

                    }
                }
            }
        }
sectionNum.setText("الجزء "+ Sura.SECTIONNAMES[sect]);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.clear();
        onDestroy();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public class StyleCallback implements ActionMode.Callback {

        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
//        MenuInflater inflater = mode.getMenuInflater();
//        inflater.inflate(R.menu.style, menu);
            menu.clear();
            mode.getMenuInflater().inflate(R.menu.style, menu);
//        menu.removeItem(android.R.id.selectAll);
            return true;
        }

        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {

            menu.clear();
            mode.getMenuInflater().inflate(R.menu.style, menu);
            return true;
        }

        @Override


        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            CharacterStyle cs;
            int start = quranPageText.getSelectionStart();
            int end = quranPageText.getSelectionEnd();
            SpannableStringBuilder ssb = new SpannableStringBuilder(quranPageText.getText());

            CharSequence selectedText = quranPageText.getText().subSequence(start, end);

            String startingFromSelection = quranPageText.getText().toString().substring(end);
            String ayaNum = "";
            for (int i = 0; i < startingFromSelection.length(); i++) {
                if (startingFromSelection.charAt(i) == '\n') {
                    ayaNum = "";
                    Toast.makeText(getContext(), ayaNum+"", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (Character.isDigit(startingFromSelection.charAt(i))) {
                    for(int j=i;Character.isDigit(startingFromSelection.charAt(j));j++) {
                        ayaNum+= startingFromSelection.charAt(j);
                    }
                    Toast.makeText(getContext(), ayaNum+"", Toast.LENGTH_SHORT).show();
                    break;
                }
            }


            switch (item.getItemId()) {

                case R.id.aya_tafseer:

                    int startingAya = 0;
                    int endAya = 0;

                    for (int i = start - 1; i >= 0; i--) {
                        if (quranPageText.getText().toString().charAt(i) == ')' ||quranPageText.getText().toString().charAt(i) == '\n') {
                            startingAya = i + 1;
                            break;
                        }
                    }
                    for (int i = end; i <= quranPageText.getText().length(); i++) {
                        if (quranPageText.getText().toString().charAt(i) == '(' || quranPageText.getText().toString().charAt(i) == '\n') {
                            endAya = i;
                            break;
                        }
                    }
                    String selectionAya = quranPageText.getText().toString().substring(startingAya,endAya);
                    if(!selectionAya.contains("\n")&& !selectionAya.contains("(")&& !selectionAya.contains(")")) {
                        aya = "";
                        String[] words = selectionAya.split("\\s+");
                        for (String w : words) {
                            if (!aya.equals("")) {
                                aya += " " + w;
                            } else {
                                aya += w;
                            }
                        }
                        aya = aya.trim();

                        Log.d("sss",aya);
//                        Toast.makeText(getContext(), "تفسير الآية " + aya, Toast.LENGTH_SHORT).show();
                        getSuraAndVerseId();
//                    cs = new StyleSpan(Typeface.BOLD);
//                    ssb.setSpan(cs, start, end, 1);
//                    quranPageText.setText(ssb);
//
//      return true;
                    }
                    break;
                case R.id.word_tafseer:
                    CharSequence mainText = quranPageText.getText();
                    selectedText = selectedText.toString().trim();
                    if (!selectedText.toString().contains(" ") && ! (selectedText.toString().contains("\n")) && !(selectedText.toString().contains("(")) && !(selectedText.toString().contains(")"))) {
//                        for (int i = start; i > 0; i--) {
//                            if (mainText.subSequence(i, end).toString().contains(" ") ||
//                                    mainText.subSequence(i, end).toString().contains(")") ||
//                                    mainText.subSequence(i, end).toString().contains("\n")) {
//                                start = i + 1;
//                                break;
//                            }
//                        }
//                        for (int i = end; i < mainText.length(); i++) {
//                            if (mainText.subSequence(start, i).toString().contains(" ") ||
//                                    mainText.subSequence(start, i).toString().contains("(") ||
//                                    mainText.subSequence(start, i).toString().contains("\n")) {
//                                end = i - 1;
//                                break;
//                            }
//                        }
                        selectedText = mainText.subSequence(start, end);
                        Intent intent = new Intent(getContext(), WordMeaningDialog.class);
                        intent.putExtra("selectedtext", selectedText.toString());
                        intent.putExtra("verseId", ayaNum);
                        intent.putExtra("versePage", page);

                        startActivity(intent);
                    }else{
                        Toast.makeText(getContext(), "الرجاء إختيار الكلمة بشكل دقيق. لا يمكن إظهار معنى لأكثر من كلمة", Toast.LENGTH_SHORT).show();
                    }


//                    Toast.makeText(getContext(), "معنى الكلمة " + selectedText + " الموجودة في الآية " + ayaNum + " صفحة " + page, Toast.LENGTH_SHORT).show();
//                    cs = new StyleSpan(Typeface.ITALIC);
//                    ssb.setSpan(cs, start, end, 1);
//                    quranPageText.setText(ssb);
//                    return true;
                    break;
//                case R.id.underline:
//                    cs = new UnderlineSpan();
//                    ssb.setSpan(cs, start, end, 1);
//                    quranPageText.setText(ssb);
//                    return true;
            }
            return false;
        }

        public void onDestroyActionMode(ActionMode mode) {
        }

    }

    private void getSuraAndVerseId() {
        Gson gson = new Gson();
        params.clear();
        params.put("verse_text",aya);
        params.put("page_num",page);
//        params.put("sura_id", sura_id);
//        params.put("verse_id", verse_id);
        GsonRequest<SubjectsSearch> subjectsGsonRequest = new GsonRequest<SubjectsSearch>(wordMeaningUrl, Request.Method.POST, SubjectsSearch.class, null, params, new Response.Listener<SubjectsSearch>() {
            @Override
            public void onResponse(SubjectsSearch response) {
                if (response != null) {
                    JsonElement data = response.getData();
                    String suraandverse = gson.fromJson(data, String.class);
                    String[] surathenverse = suraandverse.split(",");
                    if (surathenverse.length == 2) {
                        Intent intent = new Intent(getContext(), TafaseerActivity.class);
                        intent.putExtra("book_id", 0);
                        intent.putExtra("sura_id", surathenverse[0]);
                        intent.putExtra("verse_id", surathenverse[1]);
                        getContext().startActivity(intent);
                        getActivity().finish();
                    }
                }else {
                    Toast.makeText(getContext(), "response null", Toast.LENGTH_SHORT).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "لا يوجد إتصال بالشبكة", Toast.LENGTH_SHORT).show();
            }
        });
        subjectsGsonRequest.setShouldCache(false);
        subjectsGsonRequest.setTag("main_request");
        MySingleton.getInstance(getContext()).addToRequestQueue(subjectsGsonRequest);

    }


}
