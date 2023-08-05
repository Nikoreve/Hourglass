//package com.example.hourglass;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//import android.widget.CheckBox;
//import android.widget.NumberPicker;
//import android.widget.TextView;
//
//public class SetTimeActivity extends MyAppCompatActivity {
//    TextView getValueTxt;
//    NumberPicker minutesNP, secondsNP;
//CheckBox chkbox;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_set_time);
//
//        //getValueTxt = findViewById(R.id.textview_setTime_getValuesOfNumberpickers);
//        minutesNP = findViewById(R.id.numberPicker_setTime_minutespicker);
//        secondsNP = findViewById(R.id.numberPicker_setTime_secondspicker);
//
//        minutesNP.setMinValue(00);
//        minutesNP.setMaxValue(59);
//
//
//        secondsNP.setMinValue(00);
//        secondsNP.setMaxValue(59);
//        //secondsNP.set
//
//        //getValueTxt.setText(String.format("User's desired second pick is: %s", secondsNP.getValue()));
//
//        secondsNP.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
//            @Override
//            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//          //      getValueTxt.setText(String.format("User's desired seconds pick is : %s", newVal));
//            }
//        });
//
//
//    }
//}