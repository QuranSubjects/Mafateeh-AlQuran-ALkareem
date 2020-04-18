package com.example.lenovo.quransubjectsapp;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.quransubjectsapp.models.MyXAxisValueFormatter;
import com.example.lenovo.quransubjectsapp.models.MyYAxisValueFormatter;
import com.example.lenovo.quransubjectsapp.models.VersesOfSuras;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class ChartActivity extends AppCompatActivity {

    private HorizontalBarChart chart;
    Typeface tfLight;
    ArrayList<VersesOfSuras> vos;
    TextView subjectText;
    int numVerses = 0;
    TextView suravsverse, makkivsmadani, suraandverse;
    PieChart pieChart;
    private int makki, madani;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        setDefaultLanguage(this, "ar");
        showActionBar();
        TextView toolbarTitle;
        String fontPath = "fonts/Al-QuranAlKareem.ttf";
        tfLight = Typeface.createFromAsset(getAssets(), fontPath);
        ImageView homeButton = findViewById(R.id.action_main);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbarTitle = findViewById(R.id.toolbar_title);
        suravsverse = findViewById(R.id.sura_vs_verse);
        makkivsmadani = findViewById(R.id.makki_vs_madani);
        suraandverse = findViewById(R.id.suraandverse);
        ImageView action_help = findViewById(R.id.action_help);
        action_help.setVisibility(View.GONE);
        toolbarTitle.setText("إحصاءات");
        vos = MainActivity.vos;
        Collections.reverse(vos);

        toolbarTitle.setTypeface(tfLight);
        subjectText = findViewById(R.id.subject_text);
        subjectText.setTypeface(tfLight);
        makkivsmadani.setTypeface(tfLight);
        suravsverse.setTypeface(tfLight);
        suraandverse.setTypeface(tfLight);
        makki = 0;
        madani = 0;
        for (int i = 0; i < vos.size(); i++) {
            if (vos.get(i).getSuraName().contains("مكية")) {
                makki++;
            } else {
                madani++;
            }
        }
        if (makki == 0 || madani == 0) {
            makkivsmadani.setVisibility(View.GONE);
        }

        getSuravsVersesChart();

    }

    public void getSuravsVersesChart() {
        chart = findViewById(R.id.chart1);
        // chart.setHighlightEnabled(false);

        chart.setDrawBarShadow(false);

        chart.setDrawValueAboveBar(true);

        chart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        chart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(false);

        // draw shadows for each bar that show the maximum value
        // chart.setDrawBarShadow(true);

        chart.setDrawGridBackground(false);

        XAxis xl = chart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setTypeface(tfLight);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
        xl.setGranularity(1f);
        xl.setValueFormatter(new MyYAxisValueFormatter());
        xl.setLabelCount(vos.size());
        YAxis yl = chart.getAxisLeft();
        yl.setTypeface(tfLight);
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(true);
        yl.setAxisMinimum(0f);
        yl.setValueFormatter(new MyXAxisValueFormatter());// this replaces setStartAtZero(true)
//        yl.setInverted(true);

        YAxis yr = chart.getAxisRight();
        yr.setTypeface(tfLight);
        yr.setDrawAxisLine(true);
        yr.setDrawGridLines(false);
        yr.setAxisMinimum(0f);
        yr.setValueFormatter(new MyXAxisValueFormatter());// this replaces setStartAtZero(true)
//        yr.setInverted(true);

        chart.setFitBars(true);
        chart.animateY(2500);
        chart.setScaleEnabled(false);
        setData(vos.size(), 300);
        chart.setFitBars(true);
        chart.invalidate();
        // setting data

    }

    private void setData(int count, int range) {

        float barWidth = 9f;
        float spaceForBar = 10f;
        ArrayList<BarEntry> values = new ArrayList<>();
        numVerses = 0;
        for (int i = 0; i < count; i++) {
            VersesOfSuras v = vos.get(i);

            int val = (int) (v.getVerses().size());
            numVerses += v.getVerses().size();
            values.add(new BarEntry(i * spaceForBar, (int) val));
        }
        subjectText.setText(getIntent().getStringExtra("searchType") + " " + getIntent().getStringExtra("subject"));
        suraandverse.setText("(" + "الآيات: " + numVerses + "- السور: " + vos.size() + ")");
        BarDataSet set1;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(values, "عدد الآيات");

            set1.setDrawIcons(false);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setValueTypeface(tfLight);
            data.setBarWidth(barWidth);
            data.setValueFormatter(new MyXAxisValueFormatter());
            chart.setData(data);
            chart.getLegend().setEnabled(false);
            chart.getAxisLeft().setDrawLabels(false);
            chart.getAxisRight().setDrawLabels(false);
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
        context.getResources().updateConfiguration(config,
                context.getResources().getDisplayMetrics());
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

    public void getMakkivsMadaniChart() {
        setTitle("PieChartActivity");


        pieChart = findViewById(R.id.idPieChart);
//        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setCenterTextTypeface(tfLight);
        pieChart.setCenterText("مكية / مدنية");
        pieChart.setCenterTextSize(20f);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);

        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);

        pieChart.setHoleRadius(58f);
        pieChart.setTransparentCircleRadius(61f);

        pieChart.setDrawCenterText(true);

        pieChart.setRotationAngle(0);
        // enable rotation of the pieChart by touch
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);

        // pieChart.setUnit(" €");
        // pieChart.setDrawUnitsInChart(true);

        // add a selection listener



        pieChart.animateY(1400, Easing.EaseInOutQuad);
        // pieChart.spin(2000, 0, 360);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
        addDataSet();
        // entry label styling
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setEntryLabelTypeface(tfLight);
        pieChart.setEntryLabelTextSize(18f);


    }

    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("MPAndroidChart\ndeveloped by Philipp Jahoda");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, 14, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
        s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }
    private void addDataSet() {
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();

        yEntrys.add(new PieEntry(makki, "مكية"));
        yEntrys.add(new PieEntry(madani, "مدنية"));


        xEntrys.add("مكية");
        xEntrys.add("مدنية");

        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(20);
        pieDataSet.setValueFormatter(new MyXAxisValueFormatter());

        //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();


        colors.add(Color.GREEN);
        colors.add(Color.CYAN);

        pieDataSet.setColors(colors);

        //add legend to chart

        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void suravsverse(View view) {
        view.setBackground(getDrawable(R.drawable.bordered_edit_text));
        makkivsmadani.setBackground(null);
        getSuravsVersesChart();
        if (pieChart != null) {
            pieChart.setVisibility(View.GONE);
            chart.setVisibility(View.VISIBLE);
            suraandverse.setVisibility(View.VISIBLE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void makkivsmadani(View view) {
        view.setBackground(getDrawable(R.drawable.bordered_edit_text));
        suravsverse.setBackground(null);
        getMakkivsMadaniChart();
        if (chart != null) {
            chart.setVisibility(View.GONE);
            pieChart.setVisibility(View.VISIBLE);
            suraandverse.setVisibility(View.GONE);
        }
    }
}


