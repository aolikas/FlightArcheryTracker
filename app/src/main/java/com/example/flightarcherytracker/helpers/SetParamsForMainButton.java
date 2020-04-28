package com.example.flightarcherytracker.helpers;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class SetParamsForMainButton {

    public static void setParamsForTrainingButton(Button startTraining) {

        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.RECTANGLE);
        gd.setCornerRadius(60F);
        gd.setStroke(3, Color.GRAY);
        gd.setColor(Color.rgb(224, 247, 250));


        //make button StartTraining full screen
        startTraining.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        startTraining.setBackground(gd);

        int[] colors = new int[]{Color.LTGRAY};
        int[][] states = new int[][]{new int[]{}};
        ColorStateList stateList = new ColorStateList(states, colors);

        RippleDrawable ripple = new RippleDrawable(stateList, startTraining.getBackground(), null);
        startTraining.setBackground(ripple);
    }
}