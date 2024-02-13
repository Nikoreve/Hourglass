package com.example.hourglass.settings;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import com.example.hourglass.MainActivity;
import com.example.hourglass.settings.language.LanguageItem;
import com.example.hourglass.settings.language.LanguageManager;
import com.example.hourglass.settings.manual.ManualActivity;
import com.example.hourglass.MyAppCompatActivity;
import com.example.hourglass.R;
import com.example.hourglass.settings.theme.ThemeActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SettingsActivity extends MyAppCompatActivity implements View.OnClickListener {
    TextView languagetxt, themetxt, manualtxt, sfxtxt, musictxt, abouttxt;
    Button backButton;
    Toolbar toolbar;
    AlertDialog.Builder builder;
    TextView toolbarTitle;
    LanguageManager languageManager;
    AlertDialog.Builder builderAbout;
    SwitchCompat switchCompat;
    SharedPreferences sharedPreferences;
    boolean nightMode = false;
    SharedPreferences.Editor editor;
    private CustomAdapter adapter;
    private List<LanguageItem> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        languageManager = new LanguageManager(this);
        toolbar = makeToolbar();
        builderAbout = new AlertDialog.Builder(SettingsActivity.this);

        languagetxt = findViewById(R.id.language_settings);
        themetxt = findViewById(R.id.theme_settings);
//        switchCompat = findViewById(R.id.switchCompat_activitySettings_switchTheme);
        manualtxt = findViewById(R.id.manual_settings);
        sfxtxt = findViewById(R.id.sfx_settings);
        musictxt = findViewById(R.id.music_settings);
        abouttxt = findViewById(R.id.about_settings);
//        backButton = findViewById(R.id.back_settings);

        sharedPreferences = getSharedPreferences("MODE", MODE_PRIVATE);
        nightMode = sharedPreferences.getBoolean("nightMode", false);
        if (nightMode) {
            switchCompat.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
//        }else{
//            switchCompat.setChecked(false);
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//        }
//        switchCompat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (nightMode) {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//                    editor = sharedPreferences.edit();
//                    editor.putBoolean("nightMode", false);
//                } else {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//                    editor = sharedPreferences.edit();
//                    editor.putBoolean("nightMode", true);
//                }
//                editor.apply();
//            }
//        });

//        manualtxt.setOnClickListener(this::toDoManual);

        itemList = new ArrayList<>();
//        List<LanguageItem> itemList = new ArrayList<>();

        itemList.add(new LanguageItem(R.string.textEnglishLanguage, R.drawable.uk));
        itemList.add(new LanguageItem(R.string.textGreekLanguage, R.drawable.greece));

        adapter = new CustomAdapter(this, R.layout.language_item, itemList);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.language_settings:
                languagetxtClicked();
                break;
            case R.id.theme_settings:
                //method for theme
                break;
            case R.id.manual_settings:
                toDoManual();
                break;
            case R.id.sfx_settings:
                //method for sfx
                break;
            case R.id.music_settings:
                //method for music
                break;
            case R.id.about_settings:
                showAboutDialog(builderAbout);
                break;
//            case R.id.back_settings:
//                backButtonClicked();
//                break;
        }
    }

    public void languagetxtClicked() {
        builder = new AlertDialog.Builder(SettingsActivity.this);
        builder.setTitle(R.string.languageDialog)
                .setAdapter(adapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        LanguageItem selectedItem = itemList.get(which);

//                        String selectedLanguage;
//                        Intent resultIntent = new Intent();
                        switch (which) {
                            case 0:
                                languageManager.updateRecource("en");
                                recreate();
//                                selectedLanguage = "en";
//                                resultIntent.putExtra("selected_language", selectedLanguage);
//                                setResult(RESULT_OK, resultIntent);
//                                finish();
                                Toast.makeText(SettingsActivity.this, R.string.english_language_selected, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                break;
                            case 1:
                                languageManager.updateRecource("el");
                                recreate();
//                                selectedLanguage = "el";
//                                resultIntent.putExtra("selected_language", selectedLanguage);
//                                setResult(RESULT_OK, resultIntent);
//                                finish();
                                Toast.makeText(SettingsActivity.this, R.string.greek_language_selected, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                break;
                            default:
//                                        dialog.dismiss();

                        }
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

//    public void backButtonClicked() {
//        startActivity(new Intent(SettingsActivity.this, MainActivity.class));
//    }

    public void toDoManual() {
        startActivity(new Intent(SettingsActivity.this, ManualActivity.class));
    }


    public void showAboutDialog(AlertDialog.Builder b) {
        b.setTitle(R.string.aboutDialogTitle)
                .setMessage(R.string.aboutDialogMessage).show();
    }


    public Toolbar makeToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).setDisplayHomeAsUpEnabled(true);
//        actionBar.setHomeAsUpIndicator(R.drawable.arrow_back_24);
        actionBar.setTitle("");
        toolbarTitle = toolbar.findViewById(R.id.toolbar_title);

        toolbarTitle.setText(R.string.settingsHomeButton);
        return toolbar;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(SettingsActivity.this, MainActivity.class));
//                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }
}