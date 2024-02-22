package com.example.hourglass;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends MyAppCompatActivity {

    LottieAnimationView lottieAnimationView;
    TextView titleSub1, titleSub2;
    View leftHorizontalLine, rightHorizontalLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        lottieAnimationView = findViewById(R.id.lottieAnimationView_hourglassTitle);
        titleSub1 = findViewById(R.id.textView_titleSub1);
        titleSub2 = findViewById(R.id.textView_titleSub2);
        leftHorizontalLine = findViewById(R.id.horizontal_line_left);
        rightHorizontalLine = findViewById(R.id.horizontal_line_right);

        lottieAnimationView.animate().scaleX((float) 0.35).setDuration(1500).setStartDelay(500);
        lottieAnimationView.animate().scaleY((float) 0.35).setDuration(1500).setStartDelay(500);
        titleSub1.animate().translationX(-150).setDuration(1500).setStartDelay(500);
        titleSub2.animate().translationX(150).setDuration(1500).setStartDelay(500);
        leftHorizontalLine.animate().translationX(-150).setDuration(1500).setStartDelay(500);
        rightHorizontalLine.animate().translationX(150).setDuration(1500).setStartDelay(500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 5000);
    }

}