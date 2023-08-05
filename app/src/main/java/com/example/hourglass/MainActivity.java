package com.example.hourglass;

import static java.lang.String.format;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends MyAppCompatActivity {
    private static final int REQUEST_LANGUAGE = 1;
    public String player1Name, player2Name, getValueMinutesNP = "", getValueSecondsNP = "", getValueNoOfPlayersNP = "";
    public int defaultValueMinutesNP, defaultValueSecondsNP, defaultValueNoOfPlayersNP, minNPvalue, secNPvalue, nopNPvalue;
    public String defaultValuePlayer1Name = "", defaultValuePlayer2Name = "", getPlayer1NameValue, getPlayer2NameValue;
    CheckBox checkBoxWithPause;
    NumberPicker minutesNP, secondsNP, noOfPlayersNP;
    Button startBtn, setTimeBtn, playersBtn, settingsBtn, quitBtn, doneBtn;
    TextInputEditText player1NameTiet, player2NameTiet;
    Boolean chckBox, checkBoxValue, getValueWithPauseCheckBox = false, defaultValueWithPauseCheckBox = true;
    Boolean isMinutesNPfirstTime = true, isSecondsNPfirstTime = true, isNoOfPlayersNPfirstTime = true, isWithPauseCheckBox = true;
    TextView onePlayerWithPauseMinutesTV, getOnePlayerWithPauseSecondsTV;
    ImageButton oneplayerWithPauseResumePasueIB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUserPreferences();

        startBtn = findViewById(R.id.start_button);
        //startBtn.setOnClickListener((View.OnClickListener) this);
        setTimeBtn = findViewById(R.id.setTime_button);
        //setTimeBtn.setOnClickListener((View.OnClickListener) this);
        playersBtn = findViewById(R.id.players_button);
        //playersBtn.setOnClickListener((View.OnClickListener) this);
        settingsBtn = findViewById(R.id.settings_button);
        // settingsBtn.setOnClickListener((View.OnClickListener) this);
        quitBtn = findViewById(R.id.quit_button);
        //quitBtn.setOnClickListener((View.OnClickListener) this);


        setTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSetTimeDialog();
            }
        });

        playersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNumberOfPlayersDialog();
            }
        });

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                setActivityToStart();
                startActivity(new Intent(MainActivity.this, StartActivity.class));
            }
        });

    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_button:
                //Intent intent = new Intent(MainActivity.this,kati);
                break;
            case R.id.setTime_button:
                //showSetTimeDialog();
//                startActivity(new Intent(MainActivity.this, SetTimeActivity.class));
                break;
            case R.id.players_button:
                //Intent intent = new Intent(MainActivity.this,kati);
                break;
            case R.id.settings_button:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivityForResult(intent, REQUEST_LANGUAGE);

//                    startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                break;
            case R.id.quit_button:
//                    finish();
                break;
        }

    }

    public void setActivityToStart() {
//        setContentView(R.layout.one_player_with_pause);
//
//        onePlayerWithPauseMinutesTV = findViewById(R.id.textView_1playerWithPause_minutes);
//        getOnePlayerWithPauseSecondsTV = findViewById(R.id.textView_1playerWithPause_seconds);
//        oneplayerWithPausePlayIB = findViewById(R.id.imageButton_1playerWithPause_play);
//
//        onePlayerWithPauseMinutesTV.setText(getMinutesValue());
//        if (getSecondsValue().length() == 1)
//            getOnePlayerWithPauseSecondsTV.setText("0" + getSecondsValue());
//        else
//            getOnePlayerWithPauseSecondsTV.setText(getSecondsValue());

    }

    public String getMinutesValue() {
//        SharedPreferences preferences1 = getSharedPreferences("MinutesPref", MODE_PRIVATE);
//        defaultValueMinutesNP = preferences1.getInt("minutesNPvalue", 0);
//        return String.valueOf(defaultValueMinutesNP);
        return String.valueOf(getUserPrefMinutesNP(this));
    }

    public String getSecondsValue() {
//        return String.valueOf(secondsNP);
        return String.valueOf(getUserPrefSecondsNP(this));
    }

    public int getNumberOfPlayers() {
//        return String.valueOf(nopNPvalue);
        return getUserPrefNoOfPlayersNP(this);
    }

    public boolean getWithPauseCheckbox() {
        return getUserPrefWithPauseCB(this);
    }

    private void showSetTimeDialog() {
        ConstraintLayout setTimeConstraintLayout = findViewById(R.id.setTimeConstraintLayout);
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.set_time_custom_dialog, setTimeConstraintLayout);
        doneBtn = view.findViewById(R.id.button_setTime_doneDialog);
        minutesNP = view.findViewById(R.id.numberPicker_setTime_minutespicker);
        secondsNP = view.findViewById(R.id.numberPicker_setTime_secondspicker);
        checkBoxWithPause = view.findViewById(R.id.checkBox_setTime_withpause);

        minutesNP.setMinValue(00);
        minutesNP.setMaxValue(59);

        secondsNP.setMinValue(00);
        secondsNP.setMaxValue(59);

        //Kane na doylepsei to checkbox preference
        checkBoxValue = isWithPauseCheckBox ? defaultValueWithPauseCheckBox : getValueWithPauseCheckBox;
        checkBoxWithPause.setChecked(checkBoxValue);

        checkBoxWithPause.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isWithPauseCheckBox = false;
                getValueWithPauseCheckBox = isChecked;
                Log.d("onCheckedChanged getValueWithPauseCheckBox state is:", "" + getValueWithPauseCheckBox);
//                checkBoxWithPause.setChecked(isChecked);
                SharedPreferences sharedPreferences_withPause_CheckBox = getSharedPreferences("withPauseChkBox", MODE_PRIVATE);
                SharedPreferences.Editor editorCB = sharedPreferences_withPause_CheckBox.edit();
                editorCB.putBoolean("withPauseCB", getValueWithPauseCheckBox);
//              Log.d("SharedPReferences editorCB state is:",""+ getValueWithPauseCheckBox);
                editorCB.apply();
            }
        });


        minNPvalue = isMinutesNPfirstTime ? defaultValueMinutesNP : Integer.parseInt(getValueMinutesNP);
        minutesNP.setValue(minNPvalue);

        minutesNP.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                isMinutesNPfirstTime = false;
                getValueMinutesNP = (String) (format("%s", minutesNP.getValue()));
//                Log.d("MainActivity gia ta Mintes toy NumberPIcker: ", getValueMinutesNP);
                SharedPreferences sharedPreferences = getSharedPreferences("MinutesPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putInt("minutesNPvalue", newVal);
                editor.apply();
            }
        });

        secNPvalue = isSecondsNPfirstTime ? defaultValueSecondsNP : Integer.parseInt(getValueSecondsNP);
        secondsNP.setValue(secNPvalue);
        secondsNP.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                isSecondsNPfirstTime = false;
                getValueSecondsNP = (String) (format("%s", secondsNP.getValue()));
//                Log.d("MainActivity gia ta seconds toy NumberPicker: ", getValueSecondsNP);
                SharedPreferences sharedPreferences = getSharedPreferences("SecondsPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("secondsNPvalue", newVal);
                editor.apply();
            }
        });


        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();

        doneBtn.findViewById(R.id.button_setTime_doneDialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }


    private void showNumberOfPlayersDialog() {
        ConstraintLayout setnofplayersConstraintLayout = findViewById(R.id.setnofplayersConstraintLayout);
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.setnofplayers_customdialog, setnofplayersConstraintLayout);
        noOfPlayersNP = view.findViewById(R.id.numberPicker_setnofplayers_playersPicker);
        player1NameTiet = view.findViewById(R.id.tiet_setnofplayers_player1Name);
        player2NameTiet = view.findViewById(R.id.tiet_setnofplayers_player2Name);
        doneBtn = view.findViewById(R.id.button_setnofplayers_doneDialog);

        if(!getUserPrefPlayer1Name(this).isEmpty())
            player1NameTiet.setText(getUserPrefPlayer1Name(this));
        if(!getUserPrefPlayer2Name(this).isEmpty())
            player2NameTiet.setText(getUserPrefPlayer2Name(this));

        noOfPlayersNP.setMinValue(1);
        noOfPlayersNP.setMaxValue(2);

        nopNPvalue = isNoOfPlayersNPfirstTime ? defaultValueNoOfPlayersNP : Integer.parseInt(getValueNoOfPlayersNP);
        noOfPlayersNP.setValue(nopNPvalue);
        noOfPlayersNP.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                isNoOfPlayersNPfirstTime = false;
                getValueNoOfPlayersNP = (String) (format("%s", noOfPlayersNP.getValue()));
//                Log.d("MainActivity gia ta seconds toy NumberPicker: ", getValueSecondsNP);
                SharedPreferences sharedPreferences = getSharedPreferences("NoOfPlayersPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("noOfPlayersNPvalue", newVal);
                editor.apply();

            }
        });

        //getValueTxt.setText(String.format("User's desired second pick is: %s", secondsNP.getValue()));

//        noOfPlayersNP.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
//            @Override
//            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//                //      getValueTxt.setText(String.format("User's desired seconds pick is : %s", newVal));
//            }
//        });

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
//        builder.setPositiveButton(doneBtn.getText(), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                alertDialog.dismiss();
//            }
//        })
        doneBtn.findViewById(R.id.button_setnofplayers_doneDialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

                player1Name = player1NameTiet.getText().toString();
                player2Name = player2NameTiet.getText().toString();

                SharedPreferences sharedPreferences1 = getSharedPreferences("Player1Name", MODE_PRIVATE);
                SharedPreferences.Editor editor1 = sharedPreferences1.edit();
                editor1.putString("Player1NameValue", player1Name);
                editor1.apply();


                SharedPreferences sharedPreferences2 = getSharedPreferences("Player2Name",MODE_PRIVATE);
                SharedPreferences.Editor editor2 = sharedPreferences2.edit();
                editor2.putString("Player2NameValue", player2Name);
                editor2.apply();

            }
        });
        if (alertDialog.getWindow() != null) {
//            alertDialog.getWindow().getExitTransition();
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }

    public void setUserPreferences() {
//        SharedPreferences preferences1 = getSharedPreferences("MinutesPref", MODE_PRIVATE);
//        defaultValueMinutesNP = preferences1.getInt("minutesNPvalue", 0);
//        SharedPreferences preferences2 = getSharedPreferences("SecondsPref", MODE_PRIVATE);
//        defaultValueSecondsNP = preferences2.getInt("secondsNPvalue", 0);
//        SharedPreferences preferences3 = getSharedPreferences("NoOfPlayersPref", MODE_PRIVATE);
//        defaultValueNoOfPlayersNP = preferences3.getInt("noOfPlayersNPvalue", 1);
//        SharedPreferences preferences_withPause_checkbox = getSharedPreferences("withPauseChkBox", MODE_PRIVATE);
//        defaultValueWithPauseCheckBox = preferences_withPause_checkbox.getBoolean("withPauseCB", true);
        getUserPrefMinutesNP(this);
        getUserPrefSecondsNP(this);
        getUserPrefNoOfPlayersNP(this);
        getUserPrefWithPauseCB(this);
        getUserPrefPlayer1Name(this);
        getUserPrefPlayer2Name(this);
    }

    public int getUserPrefMinutesNP(Context context) {
        SharedPreferences preferences1 = context.getSharedPreferences("MinutesPref", MODE_PRIVATE);
        return defaultValueMinutesNP = preferences1.getInt("minutesNPvalue", 0);
    }

    public int getUserPrefSecondsNP(Context context) {
        SharedPreferences preferences2 = context.getSharedPreferences("SecondsPref", MODE_PRIVATE);
        return defaultValueSecondsNP = preferences2.getInt("secondsNPvalue", 0);
    }

    public int getUserPrefNoOfPlayersNP(Context context) {
        SharedPreferences preferences3 = context.getSharedPreferences("NoOfPlayersPref", MODE_PRIVATE);
        return defaultValueNoOfPlayersNP = preferences3.getInt("noOfPlayersNPvalue", 1);
    }

    public boolean getUserPrefWithPauseCB(Context context) {
        SharedPreferences preferences_withPause_checkbox = context.getSharedPreferences("withPauseChkBox", MODE_PRIVATE);
        return defaultValueWithPauseCheckBox = preferences_withPause_checkbox.getBoolean("withPauseCB", true);
    }

    public String getUserPrefPlayer1Name(Context context){
        SharedPreferences preferences_playerName1 = context.getSharedPreferences("Player1Name", MODE_PRIVATE);
        return defaultValuePlayer1Name = preferences_playerName1.getString("Player1NameValue", " ");
    }

    public String getUserPrefPlayer2Name(Context context){
        SharedPreferences preferences_playerName2 = context.getSharedPreferences("Player2Name",MODE_PRIVATE);
        return defaultValuePlayer2Name = preferences_playerName2.getString("Player2NameValue", " ");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_LANGUAGE && resultCode == RESULT_OK) {
            // Retrieve the selected language from the result
            String selectedLanguage = data.getStringExtra("selected_language");

            // Apply the selected language to the app's configuration
            LanguageManager languageManager = new LanguageManager(this);
            languageManager.updateRecource(selectedLanguage);
        }
    }
}






