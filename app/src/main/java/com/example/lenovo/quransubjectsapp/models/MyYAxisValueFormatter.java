package com.example.lenovo.quransubjectsapp.models;

import com.example.lenovo.quransubjectsapp.MainActivity;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DecimalFormat;

public class MyYAxisValueFormatter implements IAxisValueFormatter {

    private DecimalFormat mFormat;

    public MyYAxisValueFormatter() {

        // format values to 1 decimal digit
        mFormat = new DecimalFormat("###,###,##0.0");
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        String x = MainActivity.vos.get((int)value/10).getSuraName().replaceAll("سورة ","");
        // "value" represents the position of the label on the axis (x or y)
        return x;
    }


}
