package com.example.hourglass;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MyAppCompatActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        LanguageManager languageManager = new LanguageManager(this);
        languageManager.updateRecource(languageManager.getLang());
//        recreate();
    }

    public void onResume(){
        super.onResume();
        LanguageManager languageManager = new LanguageManager(this);
        languageManager.updateRecource(languageManager.getLang());
    }
}
