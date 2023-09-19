package com.example.hourglass;

import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class TwoPlayersWithPauseFragment extends Fragment {
    private final static String TAG = "Hourglass 2pWithPause";
    Chronometer chronometer1, chronometer2;
    boolean isPaused_chr1 = true, isPaused_chr2 = true, changeTurn = false;
    boolean isChr1Running = false, isChr2Running = false, isChr1Inaugurate = false, isChr2Inaugurate = false;
    // inaugurate = εγκαινιαζω
    long totalTimeInMillis, stopTimeInMillis = 999, leftTimeInSeconds1, leftTimeInSeconds2;
    long elapsedTime1_chr1 = 0, lastElapsedTime1_chr1 = 0, elapsedTime2_chr1 = 0;
    long elapsedTime1_chr2 = 0, lastElapsedTime1_chr2 = 0, elapsedTime2_chr2 = 0;
    ImageButton pauseIB1, playIB1, restartIB1, pauseIB2, playIB2, restartIB2;
    TextView player1NameTxt, player2NameTxt;
    AnimatedVectorDrawable avd;
    AnimatedVectorDrawableCompat avdc;
    int valueTime = 0, max = 0;
    int upTime_Chr1 = 1, upTime_Chr2 = 1;
    private int minutes, seconds, checkedRadiobutton;
    private ProgressBar circular_progressBar1, circular_progressBar2;
    private int progressStatus1 = 0, progressStatus2 = 0;

    // changeTurn : used to check if the playIB is pressed for first time. Hence the countDownTimer starts.

    public TwoPlayersWithPauseFragment() {
        // Required empty public constructor
    }


    public static TwoPlayersWithPauseFragment newInstance(String param1, String param2) {
        TwoPlayersWithPauseFragment fragment = new TwoPlayersWithPauseFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_two_players_with_pause, container, false);

        pauseIB1 = view.findViewById(R.id.imageButton_2playersWithPause_pause1);
        playIB1 = view.findViewById(R.id.imageButton_2playersWithPause_play1);
        restartIB1 = view.findViewById(R.id.imageButton_2playersWithPause_restart1);
        pauseIB2 = view.findViewById(R.id.imageButton_2playersWithPause_pause2);
        playIB2 = view.findViewById(R.id.imageButton_2playersWithPause_play2);
        restartIB2 = view.findViewById(R.id.imageButton_2playersWithPause_restart2);
        player1NameTxt = view.findViewById(R.id.textview_ftpwp_player1Name);
        player2NameTxt = view.findViewById(R.id.textview_ftpwp_player2Name);

        playIB1.setOnClickListener(this::playIB1onClick);
        restartIB1.setOnClickListener(this::restartIB12onClick);
        pauseIB1.setOnClickListener(this::pauseIB1onClick);

        playIB2.setOnClickListener(this::playIB2onClick);
        restartIB2.setOnClickListener(this::restartIB12onClick); // it calls the method for the restartIB1 because it does the same work
//        pauseIB2.setOnClickListener(this::pauseIB2onClick);

        chronometer1 = view.findViewById(R.id.chronometer1);
        circular_progressBar1 = view.findViewById(R.id.progressBar_ftpwp1);
        chronometer2 = view.findViewById(R.id.chronometer2);
        circular_progressBar2 = view.findViewById(R.id.progressBar_ftpwp2);

        MainActivity mainActivityObject = new MainActivity();
        String player1Name = mainActivityObject.getUserPrefPlayer1Name(getContext());
        String player2Name = mainActivityObject.getUserPrefPlayer2Name(getContext());

        if (player1Name.length() == 0)
            player1NameTxt.setText(R.string.player1NameTxt);
        else
            player1NameTxt.setText(player1Name);

        if (player2Name.isEmpty())
            player2NameTxt.setText(R.string.player2NameTxt);
        else
            player2NameTxt.setText(player2Name);

        checkedRadiobutton = mainActivityObject.getUserPrefCurrentRadiobuttonChecked(getContext());
        if (checkedRadiobutton == 2131296651) {
            minutes = mainActivityObject.getUserPref1MinutesNP(getContext());
            seconds = mainActivityObject.getUserPref1SecondsNP(getContext());
        } else if (checkedRadiobutton == 2131296652) {
            minutes = mainActivityObject.getUserPref2MinutesNP(getContext());
            seconds = mainActivityObject.getUserPref2SecondsNP(getContext());
        } else if (checkedRadiobutton == 2131296653) {
            minutes = mainActivityObject.getUserPref3MinutesNP(getContext());
            seconds = mainActivityObject.getUserPref3SecondsNP(getContext());
        } else {
            // checkedRadiobutton == -1
            System.out.print("The checkedRadiobutton probably is: -1");
            minutes = mainActivityObject.getUserPref1MinutesNP(getContext());
            seconds = mainActivityObject.getUserPref1SecondsNP(getContext());
        }
//        int minutes = mainActivityObject.getUserPref1MinutesNP(getContext());
//        int seconds = mainActivityObject.getUserPref1SecondsNP(getContext());

        max = minutes * 60 + seconds;
//        System.out.print("max value: "+max);
        Log.d(TAG, "max value: " + max);
        circular_progressBar1.setMax(max);
        circular_progressBar2.setMax(max);
        progressStatus1 = max;
        circular_progressBar1.setProgressDrawable(getResources().getDrawable(R.drawable.circular_progressbar_green, getActivity().getTheme()));
        circular_progressBar1.setProgress(progressStatus1);
        progressStatus2 = max;
        circular_progressBar2.setProgressDrawable(getResources().getDrawable(R.drawable.circular_progressbar_green, getActivity().getTheme()));
        circular_progressBar2.setProgress(progressStatus2);
        long minutesInMillis = minutes * 1000L;
        long secondsInMillis = seconds * 1000L;
        totalTimeInMillis = minutesInMillis * 60 + secondsInMillis;

        chronometer1.setBase(SystemClock.elapsedRealtime() - totalTimeInMillis);
        chronometer2.setBase(SystemClock.elapsedRealtime() - totalTimeInMillis);

        chronometer1.setOnChronometerTickListener(this::onChronometerTick1);
        chronometer2.setOnChronometerTickListener(this::onChronometerTick2);
        return view;
    }

    public void onChronometerTick1(View view) {
        chronometer1.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                //Log.d(TAG,"CHR1 \t isChr1running: "+isChr1Running+"\nisChr2running: "+isChr2Running);
                lastElapsedTime1_chr1 = elapsedTime1_chr1;
                elapsedTime1_chr1 = chronometer.getBase() - SystemClock.elapsedRealtime();
                if (lastElapsedTime1_chr1 - elapsedTime1_chr1 > 999)
                    showProgress();
//                circular_progressBar1 = showProgress(progressStatus1, circular_progressBar1);
                else if (lastElapsedTime1_chr1 - elapsedTime1_chr1 == totalTimeInMillis || (lastElapsedTime1_chr1 - elapsedTime1_chr1 > 0 && lastElapsedTime1_chr1 - elapsedTime1_chr1 < 65)) {
                    upTime_Chr1++;
                    if (upTime_Chr1 == 1 || upTime_Chr1 == 2)
                        showProgress();
//                        circular_progressBar1 = showProgress(progressStatus1, circular_progressBar1);
                }
                if (elapsedTime1_chr1 >= stopTimeInMillis) {

                } else {

                    chronometer1.stop();
                    isChr1Running = false;
                    Toast.makeText(getActivity(), getString(R.string.player1TimeFinished) + " " + player1NameTxt.getText().toString(), Toast.LENGTH_LONG).show();
                    pauseIB1.setEnabled(false);
                    playIB1.setEnabled(false);
                    pauseIB2.setEnabled(false);
                    playIB2.setEnabled(false);

                }
            }
        });
        chronometer1.setBase(SystemClock.elapsedRealtime() + totalTimeInMillis);
        chronometer1.setCountDown(true);
        chronometer1.start();
        isChr1Inaugurate = true; // is used to check whether the chronometer1 has started or not
    }

    public void onChronometerTick2(View view) {
        chronometer2.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                //Log.d(TAG,"CHR2 \t isChr1running: "+isChr1Running+"\nisChr2running: "+isChr2Running);
                lastElapsedTime1_chr2 = elapsedTime1_chr2;
                elapsedTime1_chr2 = chronometer.getBase() - SystemClock.elapsedRealtime();
                if (lastElapsedTime1_chr2 - elapsedTime1_chr2 > 999) {
                    showProgress();
                } else if (lastElapsedTime1_chr2 - elapsedTime1_chr2 == totalTimeInMillis || (lastElapsedTime1_chr2 - elapsedTime1_chr2 > 0 && lastElapsedTime1_chr2 - elapsedTime1_chr2 < 65)) {
                    upTime_Chr2++;
                    if (upTime_Chr2 == 1 || upTime_Chr2 == 2) {
                        showProgress();
                    }
                }
                if (elapsedTime1_chr2 >= stopTimeInMillis) {
                } else {
                    chronometer2.stop();
                    isChr2Running = false;
                    Toast.makeText(getActivity(), getString(R.string.player2TimeFinished) + " " + player2NameTxt.getText().toString(), Toast.LENGTH_SHORT).show();
                    pauseIB1.setEnabled(false);
                    playIB1.setEnabled(false);
                    pauseIB2.setEnabled(false);
                    playIB2.setEnabled(false);
                }
            }
        });
        chronometer2.setBase(SystemClock.elapsedRealtime() + totalTimeInMillis);
        chronometer2.setCountDown(true);
        chronometer2.start();
        isChr2Inaugurate = true; // is used to check whether the chronometer2 has started or not
    }

    public void playIB1onClick(View view) {
        restartIB1.setClickable(true);
        restartIB2.setClickable(true);


        if (isChr2Inaugurate) {
            chronometer2.stop();
        }

        isPaused_chr1 = false;
        // tha prepei molis patietai to playIB2 na dinei: isChr1Running = true
//        isChr1Running = true;

        // valueTime is used to check if the chronometer starts from this player
        if (valueTime == 0) {
            valueTime = 1;
            isChr1Running = true;
//                if (playIB1.getDrawable() == getResources().getDrawable(R.drawable.round_play_arrow_65, getActivity().getTheme())) {
            playIB1.setImageDrawable(getResources().getDrawable(R.drawable.avd_anim, getActivity().getTheme()));
            Drawable drawable = playIB1.getDrawable();

            if (drawable instanceof AnimatedVectorDrawable) {
                avd = (AnimatedVectorDrawable) drawable;
                avd.isStateful();
                avd.start();
//                while(avd.isRunning()) {
//                }
            } else if (drawable instanceof AnimatedVectorDrawableCompat) {
                avdc = (AnimatedVectorDrawableCompat) drawable;
                avdc.start();
//                while(avdc.isRunning()) {
//                }
            }

            pauseIB1.setVisibility(View.VISIBLE);
            restartIB1.setVisibility(View.VISIBLE);
            pauseIB2.setVisibility(View.VISIBLE);
            pauseIB2.setEnabled(false);
            restartIB2.setVisibility(View.VISIBLE);
            playIB2.setImageDrawable(getResources().getDrawable(R.drawable.avd_anim, getActivity().getTheme()));
            playIB2.setEnabled(false);
        }

        if (!changeTurn) {
            changeTurn = true;
            onChronometerTick1(view);
//            chronometer1.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
//                @Override
//                public void onChronometerTick(Chronometer chronometer) {
//                    Log.d(TAG,"CHR1 \t isChr1running: "+isChr1Running+"\nisChr2running: "+isChr2Running);
//                    lastElapsedTime1_chr1 = elapsedTime1_chr1;
//                    elapsedTime1_chr1 = chronometer.getBase() - SystemClock.elapsedRealtime();
//                    if (lastElapsedTime1_chr1 - elapsedTime1_chr1 > 999)
//                        showProgress();
////                circular_progressBar1 = showProgress(progressStatus1, circular_progressBar1);
//                    else if (lastElapsedTime1_chr1 - elapsedTime1_chr1 == totalTimeInMillis || (lastElapsedTime1_chr1 - elapsedTime1_chr1 > 0 && lastElapsedTime1_chr1 - elapsedTime1_chr1 < 65)) {
//                        upTime_Chr1++;
//                        if (upTime_Chr1 == 1 || upTime_Chr1 == 2)
//                            showProgress();
////                        circular_progressBar1 = showProgress(progressStatus1, circular_progressBar1);
//                    }
//                    if (elapsedTime1_chr1 >= stopTimeInMillis) {
//
//                    } else {
//
//                        chronometer1.stop();
//                        isChr1Running = false;
//                        // player1NameTxt take it by userPrefName
//                        String pl = "player1NameTxt";
//                        Toast.makeText(getActivity(), getString(R.string.player1TimeFinished) + " " + pl, Toast.LENGTH_SHORT).show();
//
//                    }
//                }
//            });
//            chronometer1.setBase(SystemClock.elapsedRealtime() + totalTimeInMillis);
//            chronometer1.setCountDown(true);
//            chronometer1.start();

//            Log.d("2pwpf", "" + playIB1.getDrawable());
//            if(playIB1.getDrawable() == getResources().getDrawable(R.drawable.avd_anim,getActivity().getTheme()))
//                playIB1.setImageDrawable(getResources().getDrawable(R.drawable.next_arrow_65,getActivity().getTheme()));
//            Log.d("2pwpf2", "" + playIB1.getDrawable());


        } else {
            elapsedTime2_chr1 = chronometer1.getBase() - SystemClock.elapsedRealtime();
            chronometer1.stop();
            isChr1Running = false;
            playIB1.setEnabled(false);
            pauseIB1.setEnabled(false);
            playIB2.setEnabled(true);
            pauseIB2.setEnabled(true);
            isChr2Running = true;

            if (!isChr2Inaugurate) {
                onChronometerTick2(view);
            } else {
                chronometer2.setBase(SystemClock.elapsedRealtime() + elapsedTime2_chr2);
                chronometer2.start();
            }
        }

    }

    public void pauseIB1onClick(View view) {
        if (!isPaused_chr1) {
            isPaused_chr1 = true;
            isChr1Running = false;
            elapsedTime2_chr1 = chronometer1.getBase() - SystemClock.elapsedRealtime();
            chronometer1.stop();
            pauseIB1.setImageResource(R.drawable.round_play_arrow_65);
            playIB1.setEnabled(false);
//            playIB2.setEnabled(false);
//            pauseIB2.setEnabled(false);

        } else {
            isPaused_chr1 = false;
            isChr1Running = true;
//                    elapsedTime2 = chronometer.getBase() - SystemClock.elapsedRealtime();
            chronometer1.setBase(SystemClock.elapsedRealtime() + elapsedTime2_chr1);
            chronometer1.start();
            playIB1.setEnabled(true);
//            playIB2.setEnabled(true);
            pauseIB1.setImageResource(R.drawable.round_pause_65);
        }
//                isPaused = !isPaused;
    }

    public void restartIB12onClick(View view) {
        restartIB1.setClickable(false);
        restartIB2.setClickable(false);
        isPaused_chr1 = false;
        isPaused_chr2 = false;
        valueTime = 0;
        changeTurn = false;

        if (isChr1Inaugurate) {
            chronometer1.stop();
            chronometer1.setBase(SystemClock.elapsedRealtime() + totalTimeInMillis);  // + 1000
            isChr1Inaugurate = false;
        }
        progressStatus1 = max;
        circular_progressBar1.setProgressDrawable(getResources().getDrawable(R.drawable.circular_progressbar_green, getActivity().getTheme()));
        circular_progressBar1.setProgress(progressStatus1, true);
        elapsedTime1_chr1 = 0;
//        elapsedTime2_chr1 = 0;
        upTime_Chr1 = 0;
//                lastElapsedTime1 = 0;

        if (isChr2Inaugurate) {
            chronometer2.stop();
            chronometer2.setBase(SystemClock.elapsedRealtime() + totalTimeInMillis); // + 1000
            isChr2Inaugurate = false;
        }
        progressStatus2 = max;
        circular_progressBar2.setProgressDrawable(getResources().getDrawable(R.drawable.circular_progressbar_green, getActivity().getTheme()));
        circular_progressBar2.setProgress(progressStatus2, true);
        elapsedTime1_chr2 = 0;
//        elapsedTime2_chr2 = 0;
        upTime_Chr2 = 0;

        playIB1.setImageDrawable(getResources().getDrawable(R.drawable.round_play_arrow_65, getActivity().getTheme()));
        playIB2.setImageDrawable(getResources().getDrawable(R.drawable.round_play_arrow_65, getActivity().getTheme()));
        pauseIB1.setImageDrawable(getResources().getDrawable(R.drawable.round_pause_65));
        pauseIB2.setImageDrawable(getResources().getDrawable(R.drawable.round_pause_65));

        playIB1.setEnabled(true);
        pauseIB1.setEnabled(false);
        playIB2.setEnabled(true);
        pauseIB2.setEnabled(false);
    }

    public void playIB2onClick(View view) {
        restartIB1.setClickable(true);
        restartIB2.setClickable(true);

        if (isChr1Inaugurate)
            chronometer1.stop();

        isPaused_chr2 = false;
//        isChr2Running = true;

        // valueTime is used to check if the chronometer starts from this player
        if (valueTime == 0) {
            valueTime = 1;
            isChr2Running = true;

            playIB2.setImageDrawable(getResources().getDrawable(R.drawable.avd_anim, getActivity().getTheme()));
            Drawable drawable = playIB2.getDrawable();

            if (drawable instanceof AnimatedVectorDrawable) {
                avd = (AnimatedVectorDrawable) drawable;
                avd.start();

            } else if (drawable instanceof AnimatedVectorDrawableCompat) {
                avdc = (AnimatedVectorDrawableCompat) drawable;
                avdc.start();
            }


            Log.d("2pwpf", "" + playIB2.getDrawable());

            pauseIB2.setVisibility(View.VISIBLE);
            restartIB2.setVisibility(View.VISIBLE);
            pauseIB1.setVisibility(View.VISIBLE);
            pauseIB1.setEnabled(false);
            restartIB1.setVisibility(View.VISIBLE);
            playIB1.setImageDrawable(getResources().getDrawable(R.drawable.avd_anim, getActivity().getTheme()));
            playIB1.setEnabled(false);
        }

        if (!changeTurn) {
            changeTurn = true;
            onChronometerTick2(view);

//            chronometer2.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
//                @Override
//                public void onChronometerTick(Chronometer chronometer) {
//                    Log.d(TAG,"CHR2 \t isChr1running: "+isChr1Running+"\nisChr2running: "+isChr2Running);
//                    lastElapsedTime1_chr2 = elapsedTime1_chr2;
//                    elapsedTime1_chr2 = chronometer.getBase() - SystemClock.elapsedRealtime();
//                    if (lastElapsedTime1_chr2 - elapsedTime1_chr2 > 999) {
//                        showProgress();
//                    } else if (lastElapsedTime1_chr2 - elapsedTime1_chr2 == totalTimeInMillis || (lastElapsedTime1_chr2 - elapsedTime1_chr2 > 0 && lastElapsedTime1_chr2 - elapsedTime1_chr2 < 65)) {
//                        upTime_Chr2++;
//                        if (upTime_Chr2 == 1 || upTime_Chr2 == 2) {
//                            showProgress();
//                        }
//                    }
//                    if (elapsedTime1_chr2 >= stopTimeInMillis) {
//                    } else {
//                        chronometer2.stop();
//                        isChr2Running = false;
//                        String pl = "player2NameTxt";
//                        Toast.makeText(getActivity(), getString(R.string.player1TimeFinished) + " " + pl, Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//            chronometer2.setBase(SystemClock.elapsedRealtime() + totalTimeInMillis);
//            chronometer2.setCountDown(true);
//            chronometer2.start();
        } else {
            elapsedTime2_chr2 = chronometer2.getBase() - SystemClock.elapsedRealtime();
            chronometer2.stop();
            isChr2Running = false;
            playIB2.setEnabled(false);
            pauseIB2.setEnabled(false);
            playIB1.setEnabled(true);
            pauseIB1.setEnabled(true);
            isChr1Running = true;

            if (!isChr1Inaugurate) {
                onChronometerTick1(view);
            } else {
                chronometer1.setBase(SystemClock.elapsedRealtime() + elapsedTime2_chr1);
                chronometer1.start();
            }
        }
    }

    private void showProgress() {
        if (isChr1Running) {
            progressStatus1--;
            if (getElapsedTime1_chr1() <= 2000)
                circular_progressBar1.setProgressDrawable(getResources().getDrawable(R.drawable.circular_progressbar_red, getActivity().getTheme()));
            else if (getElapsedTime1_chr1() <= 3000)
                circular_progressBar1.setProgressDrawable(getResources().getDrawable(R.drawable.circular_progressbar_green, getActivity().getTheme()));
            else if (getElapsedTime1_chr1() <= 4000)
                circular_progressBar1.setProgressDrawable(getResources().getDrawable(R.drawable.circular_progressbar_red, getActivity().getTheme()));
            else if (getElapsedTime1_chr1() <= 5000)
                circular_progressBar1.setProgressDrawable(getResources().getDrawable(R.drawable.circular_progressbar_green, getActivity().getTheme()));

            circular_progressBar1.setProgress(progressStatus1);
        } else if (isChr2Running) {
            progressStatus2--;
            if (getElapsedTime1_chr2() <= 2000)
                circular_progressBar2.setProgressDrawable(getResources().getDrawable(R.drawable.circular_progressbar_red, getActivity().getTheme()));
            else if (getElapsedTime1_chr2() <= 3000)
                circular_progressBar2.setProgressDrawable(getResources().getDrawable(R.drawable.circular_progressbar_green, getActivity().getTheme()));
            else if (getElapsedTime1_chr2() <= 4000)
                circular_progressBar2.setProgressDrawable(getResources().getDrawable(R.drawable.circular_progressbar_red, getActivity().getTheme()));
            else if (getElapsedTime1_chr2() <= 5000)
                circular_progressBar2.setProgressDrawable(getResources().getDrawable(R.drawable.circular_progressbar_green, getActivity().getTheme()));

            circular_progressBar2.setProgress(progressStatus2);
        }
    }

    public long getElapsedTime1_chr1() {
        return elapsedTime1_chr1;
    }

    public long getElapsedTime1_chr2() {
        return elapsedTime1_chr2;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        // if you want to enable the turn orientation then in onPause method you should write:
        // getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
    }

}