package com.example.hourglass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SettingsActivity extends MyAppCompatActivity {
    TextView languagetxt, themetxt, manualtxt, sfxtxt, musictxt, abouttxt, backtxt;
    Toolbar toolbar;
    AlertDialog.Builder builder;
    TextView toolbarTitle;
    private CustomAdapter adapter;
    private List<LanguageItem> itemList;
    AlertDialog.Builder builderAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        LanguageManager languageManager = new LanguageManager(this);
        toolbar = makeToolbar();
        builderAbout = new AlertDialog.Builder(SettingsActivity.this);

        languagetxt = findViewById(R.id.language_settings);
        themetxt = findViewById(R.id.theme_settings);
        manualtxt = findViewById(R.id.manual_settings);
        sfxtxt = findViewById(R.id.sfx_settings);
        musictxt = findViewById(R.id.music_settings);
        abouttxt = findViewById(R.id.about_settings);
        backtxt = findViewById(R.id.back_settings);

        itemList = new ArrayList<>();
        List<LanguageItem> itemList = new ArrayList<>();

        itemList.add(new LanguageItem(R.string.textEnglishLanguage,R.drawable.uk));
        itemList.add(new LanguageItem(R.string.textGreekLanguage,R.drawable.greece));

        adapter = new CustomAdapter(this, R.layout.language_item, itemList);
        
        languagetxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(SettingsActivity.this);
                builder.setTitle(R.string.languageDialog)
                        .setAdapter(adapter, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                LanguageItem selectedItem = itemList.get(which);

                                String selectedLanguage;
                                Intent resultIntent = new Intent();
                                switch (which){
                                    case 0:
                                        languageManager.updateRecource("en");
                                        recreate();
                                        selectedLanguage = "en"; // Example: "fr" for French
                                        resultIntent = new Intent();
                                        resultIntent.putExtra("selected_language", selectedLanguage);
                                        setResult(RESULT_OK, resultIntent);
                                        Toast.makeText(SettingsActivity.this, R.string.english_language_selected, Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                        break;
                                    case 1:
                                        languageManager.updateRecource("el");
                                        recreate();
                                        selectedLanguage = "el"; // Example: "fr" for French
                                        resultIntent.putExtra("selected_language", selectedLanguage);
                                        setResult(RESULT_OK, resultIntent);
                                        Toast.makeText(SettingsActivity.this, R.string.greek_language_selected, Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                        break;
                                    default:
//                                        dialog.dismiss();

                            }
                        }
//                        .setItems(R.array.language_dialog_items, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                String strcode = "";
//                                String selectedLanguage;
//                                Intent resultIntent = new Intent();
//                                switch (which) {
//                                    case 0:
//                                        //sharedPreferences
////                                        String code = "en";
////                                        Locale locale= new Locale(code);
////                                        Resources resources = context.getResources();
////                                        Configuration configuration = resources.getConfiguration();
////                                        configuration.setLocale(locale);
////                                        if(code == strcode)
//                                        languageManager.updateRecource("en");
//                                        recreate();
//                                        selectedLanguage = "en"; // Example: "fr" for French
//                                        resultIntent = new Intent();
//                                        resultIntent.putExtra("selected_language", selectedLanguage);
//                                        setResult(RESULT_OK, resultIntent);
//                                        Toast.makeText(SettingsActivity.this, R.string.english_language_selected, Toast.LENGTH_SHORT).show();
//                                        dialog.dismiss();
//                                        break;
//                                    case 1:
//                                        //sharedPreferences
//                                        languageManager.updateRecource("el");
//                                        recreate();
//                                        selectedLanguage = "el"; // Example: "fr" for French
//                                        resultIntent.putExtra("selected_language", selectedLanguage);
//                                        setResult(RESULT_OK, resultIntent);
//                                        Toast.makeText(SettingsActivity.this, R.string.greek_language_selected, Toast.LENGTH_SHORT).show();
//                                        dialog.dismiss();
//                                        break;
//                                    default:
//                                        dialog.dismiss();
//                                }
//                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

    abouttxt.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           showAboutDialog(builderAbout);
        }
    });



    backtxt.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(SettingsActivity.this, MainActivity.class));
        }
    });



    }

    public void showAboutDialog(AlertDialog.Builder b){
            b.setTitle(R.string.aboutDialogTitle)
                    .setMessage(R.string.aboutDialogMessage).show();
        }


//        languagetxt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                PopupMenu popup = new PopupMenu(SettingsActivity.this, languagetxt);
//                popup.getMenuInflater()
//                        .inflate(R.menu.menu_language, popup.getMenu());
//                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem item) {
//                        Toast.makeText(SettingsActivity.this, "Language is set up to " +item.getTitle(), Toast.LENGTH_LONG).show();
//                        return true;
//                    }
//                });
//                popup.show();
//            }
//        });


    public Toolbar makeToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).setDisplayHomeAsUpEnabled(true);
//        actionBar.setHomeAsUpIndicator(R.drawable.arrow_back_24);
        actionBar.setTitle("");
        toolbarTitle = toolbar.findViewById(R.id.toolbar_title);

        toolbarTitle.setText(R.string.settingsButton);
        return toolbar;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }
}