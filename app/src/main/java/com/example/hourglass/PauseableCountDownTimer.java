package com.example.hourglass;

import android.os.CountDownTimer;

public class PauseableCountDownTimer extends CountDownTimer {
    private long timeLeftInMillis;
    private boolean isPaused;

    public PauseableCountDownTimer(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.timeLeftInMillis = millisInFuture;
        this.isPaused = false;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        if (!isPaused) {
            timeLeftInMillis = millisUntilFinished;
            // Update UI with the remaining time
        }
    }

    @Override
    public void onFinish() {
        // Countdown finished
    }

    public void pause() {
        isPaused = true;
        cancel(); // Cancel the current countdown timer
    }

    public void resume() {
        isPaused = false;
        // Start a new countdown timer with the remaining time
        start();
    }

    public long getTimeLeftInMillis() {
        return timeLeftInMillis;
    }

    public void setTimeLeftInMillis(long tLIM){
        timeLeftInMillis = tLIM;
    }
}
