package com.example.hourglass.settings.language;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

public class LanguageManager {

    private Context context;
    private SharedPreferences sharedPreferences;

    public LanguageManager(Context ctx) {
        context = ctx;
        sharedPreferences = context.getSharedPreferences("lang", Context.MODE_PRIVATE);
    }

    public void updateRecource(String code) {
        Locale locale = new Locale(code);
        Locale.setDefault(locale);
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        setLang(code);
    }

    public String getLang() {
        return sharedPreferences.getString("lang", "en");
    }

    public void setLang(String code) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("lang", code);
        editor.apply();
    }


}
