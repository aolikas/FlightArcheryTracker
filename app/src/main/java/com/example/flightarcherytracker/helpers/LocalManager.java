package com.example.flightarcherytracker.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Locale;

public class LocalManager {

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({ENGLISH, RUSSIAN})
    public @interface LocalDef {
        String[] SUPPORTED_LOCALS = {ENGLISH, RUSSIAN };
    }

    static final String ENGLISH = "en";
    static final String RUSSIAN = "rus";

    //shared preferences key
    private static final String LANGUAGE_KEY = "language_key";

    //set current pref local
    public static Context setLocal(Context context) {
        return updateResources(context, getLanguagePref(context));
    }

    //set new Local with context
    public static Context setNewLocal(Context context, @LocalDef String language) {
        setLanguagePref(context, language);
        return updateResources(context, language);
    }

    //get saved Local from Share Preferences
    public static String getLanguagePref(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(LANGUAGE_KEY, ENGLISH);
    }

    //set pref key
    private static void setLanguagePref(Context context, String localKey){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putString(LANGUAGE_KEY, localKey).apply();
    }

    //update resource
    private static Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Resources res = context.getResources();
        Configuration configuration = new Configuration(res.getConfiguration());
        if(Build.VERSION.SDK_INT >= 24) {
            configuration.setLocale(locale);
            context = context.createConfigurationContext(configuration);
        } else {
            configuration.setLocale(locale);
            res.updateConfiguration(configuration, res.getDisplayMetrics());
        }

        return context;
    }

    //get current locale
    public static Locale getLocale(Resources res) {
        Configuration configuration = res.getConfiguration();
        return Build.VERSION.SDK_INT >= 24 ? configuration.getLocales().get(0) : configuration.locale;
    }

}
