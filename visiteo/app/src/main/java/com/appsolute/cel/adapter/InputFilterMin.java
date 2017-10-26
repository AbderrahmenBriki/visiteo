package com.appsolute.cel.adapter;

import com.appsolute.cel.activity.EtatDesLieux;

import android.text.InputFilter;
import android.text.Spanned;
import android.widget.Toast;

public class InputFilterMin implements InputFilter {

    private int min;
    private String msg;

    public InputFilterMin(int min, String msg) {
        this.min = min;
        this.msg = msg;
    }

    public InputFilterMin(String min, String msg) {
        this.min = Integer.parseInt(min);
        this.msg = msg;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        try {
            int input = Integer.parseInt(dest.toString() + source.toString());
            if (isCorrect(min, input)) {
            	return null;
            }
            else {
            	Toast.makeText(EtatDesLieux.mContext, msg, Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException nfe) { }     
        return "";
    }

    private boolean isCorrect(int a, int b) {
        return b >= a;
    }
}
