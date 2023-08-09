package com.example.hourglass;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimatedImageDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

//MAIN PRINCIPLE IS TO STOP THE TIMER WHEN THE UI OF THE ACTIVITY IS INVISIBLE
public class OnePlayerNoPauseFragment extends Fragment implements NotificationImplementation {

    private final String TAG = "Hourglass 1pNoPause";
    public ImageButton oneplayerNoPausePlayIB, oneplayerNoPauseRestartIB;
    Chronometer chronometer;
    boolean isRunning = false, isAfterOnStopMethod;
    long totalTimeInMillis, totalTimeInMillisArchieve, stopTimeInMillis = 999, leftTimeInSeconds, leftTimeInMillis;
    long elapsedTime1 = 0, lastElapsedTime1 = 0, elapsedTime2 = 0;
    Notification notification;
    NotificationManagerCompat notificationManagerCompat;
    NotificationManager notificationManager;
    private ProgressBar circular_progressBar;
    private int progressStatus = 0, upTime = 1, max, isCreatedFirstTime = 0;


    public OnePlayerNoPauseFragment() {
        // Required empty public constructor
    }

    public static OnePlayerNoPauseFragment newInstance(String param1, String param2) {
        OnePlayerNoPauseFragment fragment = new OnePlayerNoPauseFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one_player_no_pause, container, false);
        MainActivity mainActivityObject = new MainActivity();

//        IntentFilter intentFilter = new IntentFilter("com.example.NOTIFICATION_UPDATE_ACTION");
//        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(notificationUpdateReceiver, intentFilter);

        oneplayerNoPausePlayIB = (ImageButton) view.findViewById(R.id.imageButton_1playerNoPause_play);
//        oneplayerNoPausePauseIB = (ImageButton) view.findViewById(R.id.imageButton_1playerWithPause_pause);
        oneplayerNoPauseRestartIB = (ImageButton) view.findViewById(R.id.imageButton_1playerNoPause_restart);
        chronometer = (Chronometer) view.findViewById(R.id.chronometer);
        circular_progressBar = view.findViewById(R.id.progressBar_fopnp);


        int minutes = mainActivityObject.getUserPrefMinutesNP(getContext());
        int seconds = mainActivityObject.getUserPrefSecondsNP(getContext());

        max = minutes * 60 + seconds;
        circular_progressBar.setMax(max);
        progressStatus = max;
        Log.d("progressStatus or max value is: ", "" + progressStatus);
        circular_progressBar.setProgress(max);

        long minutesInMillis = minutes * 1000L;
        long secondsInMillis = seconds * 1000L;
        totalTimeInMillis = minutesInMillis * 60 + secondsInMillis;
        totalTimeInMillisArchieve = totalTimeInMillis;


        Log.d("minutes: ", "" + minutes);
        Log.d("seconds: ", "" + seconds);
        Log.d("minutesInMillis: ", "" + minutesInMillis);
        Log.d("secondsInMllis: ", "" + secondsInMillis);
        Log.d("totalTimeInMillis: ", "" + totalTimeInMillis);

//        Intent fullScreenIntent = new Intent(getActivity(), OnePlayerWithPauseFragment.class);
//        PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(getActivity(), 0, fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        chronometer.setBase(SystemClock.elapsedRealtime() - totalTimeInMillis);
        Log.d("chronometer getBase", "" + chronometer.getBase());

        oneplayerNoPausePlayIB.setOnClickListener(this::playIBonClick1PNP);
        oneplayerNoPauseRestartIB.setOnClickListener(this::restartIBonClick1PNP);

        return view;
    }

    public void playIBonClick1PNP(View view) {

        isRunning = true;
//        leftTimeInSeconds = totalTimeInMillis / 1000;
//                System.out.println("leftTimeInSeconds : "+leftTimeInSeconds);

        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer cArg) {
                lastElapsedTime1 = elapsedTime1;
                elapsedTime1 = chronometer.getBase() - SystemClock.elapsedRealtime();
                if (lastElapsedTime1 - elapsedTime1 > 999)
                    showProgress();
                else if (lastElapsedTime1 - elapsedTime1 == totalTimeInMillis || (lastElapsedTime1 - elapsedTime1 > 0 && lastElapsedTime1 - elapsedTime1 < 65)) {
                    upTime++;
                    if (upTime == 1 || upTime == 2)
                        showProgress();
                }
//                        elapsedTime1 = chronometer.getBase() + SystemClock.elapsedRealtime();
//                        if (elapsedTime1 - lastElapsedTime1 > 999)
//                            showProgress(leftTimeInSeconds);
//                        if (elapsedTime1 - lastElapsedTime1 == elapsedTime1 || (elapsedTime1 - lastElapsedTime1 > 8 && elapsedTime1 - lastElapsedTime1 < 200)) {
//                            upTime++;
//                            if (upTime == 1 || upTime == 2)
//                                showProgress(leftTimeInSeconds);
//                        }
                Log.d("lastElapsedTime1 is:", "" + lastElapsedTime1);
                Log.d("elapsedTime1 is:", "" + elapsedTime1);
                Log.d("SystemClock.elapsedRealtime():", "" + SystemClock.elapsedRealtime());
                Log.d("chronometer.getBase():", "" + chronometer.getBase());


                if (elapsedTime1 >= stopTimeInMillis) {
                    createNotification();
                } else {

                    chronometer.stop();
                    Toast.makeText(getActivity(), R.string.notificationTitleFinish, Toast.LENGTH_SHORT).show();
//                            oneplayerNoPausePauseIB.setClickable(false);
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), "myCh")
                            .setSmallIcon(R.drawable.hourglass_icon)
                            .setContentTitle(getString(R.string.notificationTitleFinish))
                            .setShowWhen(true)
                            .setSilent(true)
                            .setContentText("00:00")
                            .setDefaults(Notification.DEFAULT_VIBRATE)
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                                    .setFullScreenIntent(fullScreenPendingIntent,true)
                            .setCategory(NotificationCompat.CATEGORY_ALARM);

                    notification = builder.build();
//                            notification.defaults |= Notification.VISIBILITY_PUBLIC;
                    notificationManagerCompat = NotificationManagerCompat.from(getContext());
                    if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    notificationManagerCompat.notify(1, notification);
                }

            }
        });
        chronometer.setBase(SystemClock.elapsedRealtime() + totalTimeInMillis);
        chronometer.setCountDown(true);
        chronometer.start();

//                AnimatedImageDrawable drawable = (AnimatedImageDrawable) oneplayerNoPausePlayIB.setAnimation(R.drawable.all_in_one_animate);
//                drawable.start();
//                oneplayerNoPausePlayIB.
        oneplayerNoPausePlayIB.setVisibility(View.GONE);
//                oneplayerNoPausePauseIB.setVisibility(View.VISIBLE);
//                oneplayerNoPauseRestartIB.setAnimation(R.drawable.all_in_one_animate);
        oneplayerNoPauseRestartIB.setVisibility(View.VISIBLE);
    }


    public void restartIBonClick1PNP(View view) {

        isRunning = false;
        chronometer.stop();

        totalTimeInMillis = totalTimeInMillisArchieve;
        chronometer.setBase(SystemClock.elapsedRealtime() + totalTimeInMillis + 1000);

        progressStatus = max;
        circular_progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.circular_progressbar_green, getActivity().getTheme()));
        circular_progressBar.setProgress(progressStatus, true);

        elapsedTime1 = 0;
//                lastElapsedTime1 = 0;
        upTime = 0;

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), "myCh");
        notification = builder.build();
        notificationManagerCompat = NotificationManagerCompat.from(getContext());
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        notificationManagerCompat.cancel(1);

        oneplayerNoPausePlayIB.setVisibility(View.VISIBLE);
        oneplayerNoPauseRestartIB.setVisibility(View.GONE);
    }

    public void createNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("myCh", "My Channel", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getContext().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        Intent intent = new Intent(getContext(), StartActivity.class);
//        intent.putExtra("time",)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        long min = elapsedTime1 / 60000;
        long sec = (elapsedTime1 - min * 60000) / 1000;
        long minPos = min < 0 ? -min : min;
        long secPos = sec < 0 ? -sec : sec;
        String mmin = minPos < 10 ? "0" + minPos : minPos + "";
        String ssec = secPos < 10 ? "0" + secPos : secPos + "";

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), "myCh")
                .setSmallIcon(R.drawable.hourglass_icon)
                .setContentTitle(getString(R.string.notificationTitleRunning))
                .setShowWhen(true)
                .setContentText(mmin + ":" + ssec + "")
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setOngoing(true)
                .setSilent(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent);

        notification = builder.build();
        notificationManager = (NotificationManager) getActivity().getSystemService(getActivity().NOTIFICATION_SERVICE);
//    notificationManagerCompat =NotificationManagerCompat.from(getContext());

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
//        notificationManagerCompat.notify(1, notification);
        notificationManager.notify(1, notification);
    }

    private void showProgress() {
        progressStatus--;
        if (getElapsedTime1() <= 2000)
            circular_progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.circular_progressbar_red, getActivity().getTheme()));
        else if (getElapsedTime1() <= 3000)
            circular_progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.circular_progressbar_green, getActivity().getTheme()));
        else if (getElapsedTime1() <= 4000)
            circular_progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.circular_progressbar_red, getActivity().getTheme()));
        else if (getElapsedTime1() <= 5000)
            circular_progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.circular_progressbar_green, getActivity().getTheme()));

        circular_progressBar.setProgress(progressStatus);
    }

    public void pauseNotification() {

    }

    public void stopNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), "myCh");
        notification = builder.build();
        notificationManagerCompat = NotificationManagerCompat.from(getContext());
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        notificationManagerCompat.cancel(1);
    }

    @Override
    public void onPause() {
        super.onPause();
//        chronometer.stop();
//        pauseNotification();
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        chronometer.start();
//        createNotification();
//    }

    @Override
    public void onStop() {
        super.onStop();
//        Toast.makeText(getActivity(), "is onStop called", Toast.LENGTH_SHORT).show();
//        elapsedTime2 = SystemClock.elapsedRealtime() - chronometer.getBase();
//        leftTimeInMillis = totalTimeInMillis - elapsedTime2;
        isAfterOnStopMethod = true;
        chronometer.stop();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("timerPref1PNP", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("isCreatedFirst1PNP", isCreatedFirstTime);
//        editor.putLong("mtimeLeft1PNP", leftTimeInMillis);
        editor.putLong("elapsedTime1", elapsedTime1);
//        editor.putLong("elapsedTime2_1PNP", elapsedTime2);

        editor.putBoolean("timerRunning1PNP", isRunning);
        editor.putBoolean("timerStopped1PNP", isAfterOnStopMethod);
        editor.apply();
//        }
//        isCreatedFirstTime = false;
//        pauseNotification();
    }

    @Override
    public void onStart() {
        super.onStart();
//        Toast.makeText(getActivity(), "After change orientation", Toast.LENGTH_SHORT).show();
        SharedPreferences sharedPreferences2 = getActivity().getSharedPreferences("timerPref1PNP", Context.MODE_PRIVATE);
        stopTimeInMillis = 999;
//        leftTimeInMillis = sharedPreferences2.getLong("mtimeLeft1PNP", totalTimeInMillis);
        progressStatus = sharedPreferences2.getInt("progressStatus", progressStatus);
        isCreatedFirstTime = sharedPreferences2.getInt("isCreatedFirst1PNP", 0);
        elapsedTime1 = sharedPreferences2.getLong("elapsedTime1", 0);
//        elapsedTime2 = sharedPreferences2.getLong("elapsedTime2_1PNP", 0);
        isRunning = sharedPreferences2.getBoolean("timerRunning1PNP", false);
        isAfterOnStopMethod = sharedPreferences2.getBoolean("timerStopped1PNP", false);
//        Log.d(TAG, "elapsedTime2 is: " + elapsedTime2);

//        if(getActivity() != null && getActivity().getCallingActivity() != null && getActivity().getCallingActivity().getClassName().equals(MainActivity.class.getName())){
//        if (getActivity() != null) {
//            if (getActivity().getCallingActivity() != null) {
//                if (getActivity().getCallingActivity().getClassName().equals(MainActivity.class.getName())) {
//                    isCreatedFirstTime = 0;
//                }
//            }
//        }
        Bundle args = getArguments();
        if (args != null && args.getBoolean("resetFirstTime")) {
            isCreatedFirstTime = 0;
            progressStatus = max;
            args.putBoolean("resetFirstTime", false);
        }

        if (isCreatedFirstTime != 0) {
            if (isAfterOnStopMethod) {
                isAfterOnStopMethod = false;
                if (isRunning) {
                    if (elapsedTime1 <= stopTimeInMillis) {
                        Toast.makeText(getActivity(), R.string.notificationTitleFinish, Toast.LENGTH_SHORT).show();
//                        Log.d(TAG, "leftTimeInMillis0: " + leftTimeInMillis);
                        isRunning = false;
                        restartIBonClick1PNP(getView());
                    } else {
//                        Log.d(TAG, "leftTimeInMillis: " + leftTimeInMillis);
                        totalTimeInMillis = elapsedTime1;
                        playIBonClick1PNP(getView());
                    }
                }
//                else{
////                    Log.d(TAG, "leftTimeInMillis: " + leftTimeInMillis);
//                    totalTimeInMillis = elapsedTime3;
////                    playIBonClick1PWP(getView());
////                    isPaused = false;
////                    chronometer.setBase(SystemClock.elapsedRealtime() + totalTimeInMillis);
//
//                    isPaused = true;
//                    oneplayerWithPausePauseIB.setImageResource(R.drawable.round_play_arrow_65);
//
//                    chronometer.setBase(SystemClock.elapsedRealtime() + elapsedTime3);
//                    chronometer.setCountDown(true);
//                    chronometer.start();
//                    chronometer.stop();
//                    pauseIBonClick1PWP(getView());
//                }
            }
        }
        isCreatedFirstTime++;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // Unregister the broadcast receiver
//        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(notificationUpdateReceiver);
    }

    public void onDestroy() {
        super.onDestroy();

        stopNotification();
    }

    private int getProgressStatus() {
        Log.d(TAG, "progressStatus " + progressStatus);
        return progressStatus;
    }

    private long getElapsedTime1() {
        return elapsedTime1;
    }
}
