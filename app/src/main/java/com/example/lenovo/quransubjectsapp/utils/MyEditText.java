package com.example.lenovo.quransubjectsapp.utils;

import android.content.Context;
import android.view.KeyEvent;
import android.widget.EditText;

public class MyEditText extends EditText {
    public MyEditText(Context context) {
        super(context);
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK &&
                event.getAction() == KeyEvent.ACTION_UP) {
            this.setFocusable(false);
            // do your stuff
            return false;
        }
        return super.dispatchKeyEvent(event);
    }
}
