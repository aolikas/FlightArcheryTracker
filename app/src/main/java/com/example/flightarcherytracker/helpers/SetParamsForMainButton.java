package com.example.flightarcherytracker.helpers;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class SetParamsForMainButton {

    public static void setParamsForTrainingButton(Button startTraining) {

        //make button StartTraining full screen
        startTraining.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
    }
}