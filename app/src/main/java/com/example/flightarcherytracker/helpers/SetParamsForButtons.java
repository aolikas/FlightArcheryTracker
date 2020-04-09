package com.example.flightarcherytracker.helpers;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.flightarcherytracker.R;

public class SetParamsForButtons {

    public static void setParamsForButtons(Button startTraining, Button saveShoots) {

        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.RECTANGLE);
        gd.setCornerRadius(60F);
        gd.setStroke(1, Color.GRAY);
        gd.setColor(Color.rgb(250,218,94));




        saveShoots.setVisibility(View.VISIBLE);
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 50f);
        params1.setMarginEnd(5);
        startTraining.setLayoutParams(params1);
      //  startTraining.setBackgroundResource(R.color.colorNewButton);
        startTraining.setBackground(gd);
      //  startTraining.setBackgroundColor(0xFADA5E);
        startTraining.setText(R.string.button_start_new_training);


        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 50f);
        params2.setMarginStart(5);
        saveShoots.setLayoutParams(params2);
    }
}
