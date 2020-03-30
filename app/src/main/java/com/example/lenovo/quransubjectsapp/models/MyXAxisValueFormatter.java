package com.example.lenovo.quransubjectsapp.models;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

public class MyXAxisValueFormatter implements IAxisValueFormatter,IValueFormatter {


    public MyXAxisValueFormatter() {

        // format values to 1 decimal digit
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        // "value" represents the position of the label on the axis (x or y)
        if((int)value==value)
        return  (int) value +"";
        return "";
    }


    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        return Math.round(value)+"";
    }
}
