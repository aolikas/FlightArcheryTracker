package com.example.flightarcherytracker.helpers;


import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class SetParamsForButtons {

    public static void setParamsForButtons(Button stopTraining, Button saveShoots) {

        stopTraining.setVisibility(View.VISIBLE);
        saveShoots.setVisibility(View.VISIBLE);

        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 50f);
        params1.setMarginEnd(5);
        stopTraining.setLayoutParams(params1);

        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 50f);
        params2.setMarginStart(5);
        saveShoots.setLayoutParams(params2);
    }
}
