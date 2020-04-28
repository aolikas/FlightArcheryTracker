package com.example.flightarcherytracker.helpers;


import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class SetParamsForButtons {

    public static void setParamsForButtons(Button stopTraining, Button saveShoots) {

        GradientDrawable gdStop = new GradientDrawable();
        gdStop.setShape(GradientDrawable.RECTANGLE);
        gdStop.setCornerRadius(60F);
        gdStop.setStroke(3, Color.GRAY);
        gdStop.setColor(Color.rgb(247, 209, 15));

        GradientDrawable gdSave = new GradientDrawable();
        gdSave.setShape(GradientDrawable.RECTANGLE);
        gdSave.setCornerRadius(60F);
        gdSave.setStroke(3, Color.GRAY);
        gdSave.setColor(Color.rgb(224, 247, 250));


        stopTraining.setVisibility(View.VISIBLE);
        saveShoots.setVisibility(View.VISIBLE);

        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 50f);
        params1.setMarginEnd(5);
        stopTraining.setLayoutParams(params1);
        //  startTraining.setBackgroundResource(R.color.colorNewButton);
        stopTraining.setBackground(gdStop);
        //  startTraining.setBackgroundColor(0xFADA5E);
        //  startTraining.setText(R.string.button_stop_current_training);

        int[] colorsTraining = new int[]{Color.LTGRAY};
        int[][] statesTraining = new int[][]{new int[]{}};
        ColorStateList stateListTraining = new ColorStateList(statesTraining, colorsTraining);

        RippleDrawable rippleTraining = new RippleDrawable(stateListTraining, stopTraining.getBackground(), null);
        stopTraining.setBackground(rippleTraining);


        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 50f);
        params2.setMarginStart(5);
        saveShoots.setLayoutParams(params2);
        saveShoots.setBackground(gdSave);

        int[] colorsSave = new int[]{Color.LTGRAY};
        int[][] statesSave = new int[][]{new int[]{}};
        ColorStateList stateListSave = new ColorStateList(statesSave, colorsSave);

        RippleDrawable rippleSave = new RippleDrawable(stateListSave, saveShoots.getBackground(), null);
        saveShoots.setBackground(rippleSave);
    }
}
