package com.example.hourglass;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
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


public class OnePlayerNoPauseFragment extends Fragment implements NotificationImplementation {

    private final String TAG = "Hourglass 1pNoPause";
    public ImageButton oneplayerNoPausePlayIB, oneplayerNoPauseRestartIB;
    Chronometer chronometer;
    boolean isPaused = false;
    long totalTimeInMillis, stopTimeInMillis = 999, leftTimeInSeconds;
    long elapsedTime1 = 0, lastElapsedTime1 = 0, elapsedTime2 = 0;
    Notification notification;
    NotificationManagerCompat notificationManagerCompat;
    NotificationManager notificationManager;
    private ProgressBar circular_progressBar;
    private int progressStatus = 0, upTime = 1;


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

        int max = minutes * 60 + seconds;
        circular_progressBar.setMax(max);
        progressStatus = max;
        Log.d("progressStatus or max value is: ", "" + progressStatus);
        circular_progressBar.setProgress(max);

        long minutesInMillis = minutes * 1000L;
        long secondsInMillis = seconds * 1000L;
        totalTimeInMillis = minutesInMillis * 60 + secondsInMillis;


        Log.d("minutes: ", "" + minutes);
        Log.d("seconds: ", "" + seconds);
        Log.d("minutesInMillis: ", "" + minutesInMillis);
        Log.d("secondsInMllis: ", "" + secondsInMillis);
        Log.d("totalTimeInMillis: ", "" + totalTimeInMillis);

//        Intent fullScreenIntent = new Intent(getActivity(), OnePlayerWithPauseFragment.class);
//        PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(getActivity(), 0, fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        chronometer.setBase(SystemClock.elapsedRealtime() - totalTimeInMillis);
        Log.d("chronometer getBase", "" + chronometer.getBase());

        oneplayerNoPausePlayIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isPaused = false;
                leftTimeInSeconds = totalTimeInMillis / 1000;
//                System.out.println("leftTimeInSeconds : "+leftTimeInSeconds);


                chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                    @Override
                    public void onChronometerTick(Chronometer cArg) {
                        lastElapsedTime1 = elapsedTime1;
                        elapsedTime1 = chronometer.getBase() - SystemClock.elapsedRealtime();
                        if (lastElapsedTime1 - elapsedTime1 > 999)
                            showProgress(leftTimeInSeconds);
                        else if (lastElapsedTime1 - elapsedTime1 == totalTimeInMillis || (lastElapsedTime1 - elapsedTime1 > 0 && lastElapsedTime1 - elapsedTime1 < 65)) {
                            upTime++;
                            if (upTime == 1 || upTime == 2)
                                showProgress(leftTimeInSeconds);
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

        });

        oneplayerNoPauseRestartIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                chronometer.stop();
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
        });

        return view;
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

    private void showProgress(long leftTimeInSeconds) {
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
//
//        long elapsedTime3 = SystemClock.elapsedRealtime() - chronometer.getBase();
//        long min = elapsedTime3 / 60000;
//        long sec = (elapsedTime3 - min * 60000) / 1000;
//        long minPos = min < 0 ? -min : min;
//        long secPos = sec < 0 ? -sec : sec;
//        String mmin = minPos < 10 ? "0" + minPos : minPos + "";
//        String ssec = secPos < 10 ? "0" + secPos : secPos + "";
//
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), "myCh")
//                .setSmallIcon(R.drawable.hourglass_icon)
//                .setContentTitle(getString(R.string.notificationTitleStop))
//                .setShowWhen(true)
//                .setSilent(true)
//                .setContentText(mmin + ":" + ssec + "");
//
//        notification = builder.build();
////                            notification.defaults |= Notification.VISIBILITY_PUBLIC;
//        notificationManagerCompat = NotificationManagerCompat.from(getContext());
//        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        notificationManagerCompat.notify(1, notification);
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
        chronometer.stop();
//        pauseNotification();
    }

    @Override
    public void onStop() {
        super.onStop();
        chronometer.stop();
//        pauseNotification();
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        chronometer.start();
//        createNotification();
//    }

//    @Override
//    public void onStart(){
//        super.onStart();
//        chronometer.start();
//        createNotification();
//    }

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
