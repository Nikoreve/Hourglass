package com.example.hourglass;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

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

public class OnePlayerWithPauseFragment extends MyFragment implements NotificationImplementation {
    private final String TAG = "Hourglass 1pWithPause";
    TextView onePlayerWithPauseMinutesTV, onePlayerWithPauseSecondsTV;
    ImageButton oneplayerWithPausePlayIB, oneplayerWithPausePauseIB, oneplayerWithPauseRestartIB;
    boolean isPaused = false, isRunning = false, isStopped = false;
    long totalTimeInMillis, stopTimeInMillis = 999, leftTimeInSeconds,
            leftTimeInMillis, endTimeInMillis;
    long elapsedTime1 = 0, lastElapsedTime1 = 0, elapsedTime2 = 0;
   long totalTimeInMillisArchieve;
    Chronometer chronometer;
    Notification notification;
    NotificationManagerCompat notificationManagerCompat;
    NotificationManager notificationManager;
    private ProgressBar circular_progressBar;
    private int progressStatus = 0, upTime = 1;
    private long timeLeftInMillis;
    private ConstraintLayout buttonContainer;
    private BroadcastReceiver notificationUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            pauseNotification();
        }
    };

    public OnePlayerWithPauseFragment() {
        // Required empty public constructor
    }

    public static OnePlayerWithPauseFragment newInstance(String param1, String param2) {
        OnePlayerWithPauseFragment fragment = new OnePlayerWithPauseFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one_player_with_pause, container, false);

        MainActivity mainActivityObject = new MainActivity();

//        IntentFilter intentFilter = new IntentFilter("com.example.NOTIFICATION_UPDATE_ACTION");
//        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(notificationUpdateReceiver, intentFilter);


//        onePlayerWithPauseMinutesTV = (TextView) view.findViewById(R.id.textView_1playerWithPause_minutes);
//        onePlayerWithPauseSecondsTV = (TextView) view.findViewById(R.id.textView_1playerWithPause_seconds);
        oneplayerWithPausePlayIB = (ImageButton) view.findViewById(R.id.imageButton_1playerWithPause_play);
        oneplayerWithPausePauseIB = (ImageButton) view.findViewById(R.id.imageButton_1playerWithPause_pause);
        oneplayerWithPauseRestartIB = (ImageButton) view.findViewById(R.id.imageButton_1playerWithPause_restart);
        chronometer = (Chronometer) view.findViewById(R.id.chronometer);
        circular_progressBar = view.findViewById(R.id.progressBar_fopwp);

//        if (String.valueOf(mainActivityObject.getUserPrefMinutesNP(getContext())).length() == 1)
//            onePlayerWithPauseMinutesTV.setText("0" + String.valueOf(mainActivityObject.getUserPrefMinutesNP(getContext())));
//        else
//            onePlayerWithPauseMinutesTV.setText("" + String.valueOf(mainActivityObject.getUserPrefMinutesNP(getContext())));
//
//        if (String.valueOf(mainActivityObject.getUserPrefSecondsNP(getContext())).length() == 1)
//            onePlayerWithPauseSecondsTV.setText("0" + mainActivityObject.getUserPrefSecondsNP(getContext()));
//        else
//            onePlayerWithPauseSecondsTV.setText("" + mainActivityObject.getUserPrefSecondsNP(getContext()));

        int minutes = mainActivityObject.getUserPrefMinutesNP(getContext());
        int seconds = mainActivityObject.getUserPrefSecondsNP(getContext());
//        int minutes = Integer.parseInt(onePlayerWithPauseMinutesTV.getText().toString());
//        int seconds = Integer.parseInt(onePlayerWithPauseSecondsTV.getText().toString());

        int max = minutes * 60 + seconds;
        circular_progressBar.setMax(max);
        progressStatus = max;
        Log.d("progressStatus or max value is: ", "" + progressStatus);
        circular_progressBar.setProgress(max);

        long minutesInMillis = minutes * 60 * 1000;
        long secondsInMillis = seconds * 1000;
        totalTimeInMillis = minutesInMillis + secondsInMillis;
        totalTimeInMillisArchieve = totalTimeInMillis;
        leftTimeInMillis = totalTimeInMillis;
        endTimeInMillis = System.currentTimeMillis() + totalTimeInMillis;

        Log.d("minutes: ", "" + minutes);
        Log.d("seconds: ", "" + seconds);
        Log.d("minutesInMillis: ", "" + minutesInMillis);
        Log.d("secondsInMllis: ", "" + secondsInMillis);
        Log.d("totalTimeInMillis: ", "" + totalTimeInMillis);

//        Intent fullScreenIntent = new Intent(getActivity(), OnePlayerWithPauseFragment.class);
//        PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(getActivity(), 0, fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        chronometer.setBase(SystemClock.elapsedRealtime() - totalTimeInMillis);
        Log.d("chronometer getBase", "" + chronometer.getBase());

        //        mChronometer.setBase(SystemClock.elapsedRealtime() - (nr_of_min * 60000 + nr_of_sec * 1000)))


//        oneplayerWithPausePauseIB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
////                Chronometer chronometer = new Chronometer(getContext());
////                chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener(){
////                    @Override
////                    public void onChronometerTick(Chronometer cArg) {
////                        long time = totalTimeInMillis ;//- cArg.getBase();
//////                        int h   = (int)(time /3600000);
////                        int m = (int) time/60000;
////                        int s= (int)(time - m*60000)/1000 ;
//////                        String hh = h < 10 ? "0"+h: h+"";
////                        String mm = m < 10 ? "0"+m: m+"";
////                        String ss = s < 10 ? "0"+s: s+"";
////                        cArg.setText(mm+":"+ss);
////                    }
////                });
////                chronometer.setBase(totalTimeInMillis);
////                chronometer.start();
//
//            }
//        });

        oneplayerWithPausePlayIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                progressStatus = minutes * 60 + seconds;
//                circular_progressBar.setProgress(progressStatus);

//                totalTimeInMillis = minutesInMillis + secondsInMillis;
                isRunning = true;
                isPaused = false;
                oneplayerWithPausePauseIB.setImageResource(R.drawable.round_pause_65);
                leftTimeInSeconds = totalTimeInMillis / 1000;
//                System.out.println("leftTimeInSeconds : "+leftTimeInSeconds);

                chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                    @Override
                    public void onChronometerTick(Chronometer cArg) {
                        lastElapsedTime1 = elapsedTime1;
                        elapsedTime1 = chronometer.getBase() - SystemClock.elapsedRealtime();
                        leftTimeInMillis = totalTimeInMillis - elapsedTime1;
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
//                        progressStatus = (int) elapsedTime1 / 1000;
//                        progressStatus--;
//                        if(elapsedTime1 <= 2000)
//                            circular_progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.circular_progressbar_red));
//                        else if(elapsedTime1 <= 3000)
//                            circular_progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.circular_progressbar_green));
//                        else if(elapsedTime1 <= 4000)
//                            circular_progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.circular_progressbar_red));
//                        else if (elapsedTime1 <= 5000)
//                            circular_progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.circular_progressbar_green));
//
//                        circular_progressBar.setProgress(progressStatus);


                        if (elapsedTime1 >= stopTimeInMillis) {
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                                NotificationChannel channel = new NotificationChannel("myCh", "My Channel", NotificationManager.IMPORTANCE_DEFAULT);
//                                NotificationManager manager = getContext().getSystemService(NotificationManager.class);
//                                manager.createNotificationChannel(channel);
//                            }
//
//                            long min = elapsedTime1 / 60000;
//                            long sec = (elapsedTime1 - min * 60000) / 1000;
//                            long minPos = min < 0 ? -min : min;
//                            long secPos = sec < 0 ? -sec : sec;
//                            String mmin = minPos < 10 ? "0" + minPos : minPos + "";
//                            String ssec = secPos < 10 ? "0" + secPos : secPos + "";
//
//                            NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), "myCh")
//                                    .setSmallIcon(R.drawable.hourglass_icon)
//                                    .setContentTitle(getString(R.string.notificationTitleRunning))
//                                    .setShowWhen(true)
//                                    .setContentText(mmin + ":" + ssec + "")
//                                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
//                                    .setSilent(true);
//
//                            notification = builder.build();
////                            notification.defaults |= Notification.VISIBILITY_PUBLIC;
//                            notificationManagerCompat = NotificationManagerCompat.from(getContext());
//
//                            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
//                                // TODO: Consider calling
//                                //    ActivityCompat#requestPermissions
//                                // here to request the missing permissions, and then overriding
//                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                                //                                          int[] grantResults)
//                                // to handle the case where the user grants the permission. See the documentation
//                                // for ActivityCompat#requestPermissions for more details.
//                                return;
//                            }
//                            notificationManagerCompat.notify(1, notification);


                            createNotification();

//                             notificationServiceHourglass.onStartCommand();


                        } else {
                            isRunning = false;
                            chronometer.stop();
                            Toast.makeText(getActivity(), R.string.notificationTitleFinish, Toast.LENGTH_SHORT).show();
                            oneplayerWithPausePauseIB.setClickable(false);
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
//                        long time = totalTimeInMillis;
//                        int h   = (int)(time /3600000);
//                        int m = (int) time / 60000;
//                        int s = (int) (time - m * 60000) / 1000;
//                        int h   = (int)(time /3600000);
//                        int m = (int) time / 60000;
//                        int s = (int) (time - m * 60000) / 1000;
//                        String hh = h < 10 ? "0"+h: h+"";
//                        String mm = m < 10 ? "0" + m : m + "";
//                        String ss = s < 10 ? "0" + s : s + "";
//                        cArg.setText(mm + ":" + ss);
                    }
                });
                chronometer.setBase(SystemClock.elapsedRealtime() + totalTimeInMillis);
                chronometer.setCountDown(true);
                chronometer.start();

                oneplayerWithPausePlayIB.setVisibility(View.GONE);
                oneplayerWithPausePauseIB.setVisibility(View.VISIBLE);
                oneplayerWithPauseRestartIB.setVisibility(View.VISIBLE);
            }
        });

        oneplayerWithPausePauseIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isPaused) {
                    isPaused = true;
                    isRunning = false;
                    elapsedTime2 = chronometer.getBase() - SystemClock.elapsedRealtime();
                    chronometer.stop();
                    oneplayerWithPausePauseIB.setImageResource(R.drawable.round_play_arrow_65);
//                    progressStatus = getProgressStatus();
//                    circular_progressBar.setProgress(progressStatus);
                    pauseNotification();
                } else {
                    isPaused = false;
                    isRunning = true;
//                    elapsedTime2 = chronometer.getBase() - SystemClock.elapsedRealtime();
                    chronometer.setBase(SystemClock.elapsedRealtime() + elapsedTime2);
                    chronometer.start();
//                    progressStatus = getProgressStatus();
//                    circular_progressBar.setProgress(progressStatus);
                    oneplayerWithPausePauseIB.setImageResource(R.drawable.round_pause_65);
                }
//                isPaused = !isPaused;
            }
        });


        oneplayerWithPauseRestartIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRunning = false;
                chronometer.stop();
                totalTimeInMillis = totalTimeInMillisArchieve;
                chronometer.setBase(SystemClock.elapsedRealtime() + totalTimeInMillis + 1000);
                leftTimeInMillis = totalTimeInMillis;

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

                oneplayerWithPausePauseIB.setClickable(true);
                oneplayerWithPausePlayIB.setVisibility(View.VISIBLE);
                oneplayerWithPausePauseIB.setVisibility(View.GONE);
                oneplayerWithPauseRestartIB.setVisibility(View.GONE);
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
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0,
                intent, 0);

//        Intent broadcastIntent = new Intent(getContext(), NotificationReceiver.class);
//        broadcastIntent.putExtra("MessageAddedHere", "It's comming from the notification");
//        PendingIntent actionIntent = PendingIntent.getBroadcast(getContext(),
//                0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);


//        Intent intent = new Intent(getContext(), StartActivity.class); // Here pass your activity where you want to redirect.
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//        PendingIntent contentIntent = PendingIntent.getActivity(getContext(), (int) (Math.random() * 100), intent, 0);

//        Intent resultIntent = new Intent(getActivity(), StartActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getContext());
//        stackBuilder.addNextIntentWithParentStack(resultIntent);
//        PendingIntent resultPendingIntent
//                = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_IMMUTABLE
//                | PendingIntent.FLAG_IMMUTABLE);


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
                .setAutoCancel(true)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setOnlyAlertOnce(true)
//                .setContentIntent(pendingIntent)
                .setSilent(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH);
//                .addAction(R.drawable.hourglass_icon, "Pause", actionIntent);
        //builder.setTicker()
        notification = builder.build();
//                            notification.defaults |= Notification.VISIBILITY_PUBLIC;
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

    //    @SuppressLint("UseCompatLoadingForDrawables")
    private void showProgress(long leftTimeInSeconds) {

//        for (long i = leftTimeInSeconds; i > 0; i--) {

        if (!isPaused) {
//            progressStatus = (int) elapsedTime1;
//            Log.d(TAG,"elapsedTime3 : "+elapsedTime3);
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
    }


    public void pauseNotification() {

        long elapsedTime3 = SystemClock.elapsedRealtime() - chronometer.getBase();
        long min = elapsedTime3 / 60000;
        long sec = (elapsedTime3 - min * 60000) / 1000;
        long minPos = min < 0 ? -min : min;
        long secPos = sec < 0 ? -sec : sec;
        String mmin = minPos < 10 ? "0" + minPos : minPos + "";
        String ssec = secPos < 10 ? "0" + secPos : secPos + "";


        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), "myCh")
                .setSmallIcon(R.drawable.hourglass_icon)
                .setContentTitle(getString(R.string.notificationTitleStop))
                .setShowWhen(true)
                .setSilent(true)
                .setContentText(mmin + ":" + ssec + "");

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
        elapsedTime2 = chronometer.getBase() - SystemClock.elapsedRealtime();
        isStopped = true;
        chronometer.stop();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("timerPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("mtimeLeft", leftTimeInMillis);
        editor.putLong("mtimeEnd", endTimeInMillis);
        editor.putLong("elapsedTime2", elapsedTime2);
        editor.putBoolean("timerRunning", isRunning);
        editor.putBoolean("timerStopped", isStopped);
        editor.apply();
//        pauseNotification();
    }

    @Override
    public void onStart() {
        super.onStart();
//        Toast.makeText(getActivity(), "After change orientation", Toast.LENGTH_SHORT).show();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("timerPref", Context.MODE_PRIVATE);
        leftTimeInMillis = sharedPreferences.getLong("mtimeLeft", totalTimeInMillis);
        stopTimeInMillis = 999;
//        elapsedTime2 = sharedPreferences.getLong("elapsedTime2", 0);
        isRunning = sharedPreferences.getBoolean("timerRunning", false);
        isStopped = sharedPreferences.getBoolean("timerStopped", false);

        if (isStopped) {
            isStopped = false;
            if (isRunning) {
//            endTimeInMillis = sharedPreferences.getLong("mtimeEnd", 0);
//            timeLeftInMillis = endTimeInMillis - System.currentTimeMillis();
                if (leftTimeInMillis <= stopTimeInMillis) {
                    Log.d(TAG,"leftTimeInMillis0: "+leftTimeInMillis);
                    isRunning = false;
//                    timeLeftInMillis = 0;
                    oneplayerWithPauseRestartIB.callOnClick();
                } else {
                    Log.d(TAG,"leftTimeInMillis: "+leftTimeInMillis);
                    chronometer.setBase(SystemClock.elapsedRealtime() - leftTimeInMillis);
                    totalTimeInMillis = leftTimeInMillis;
                    oneplayerWithPausePlayIB.callOnClick();
//                    chronometer.setCountDown(true);
//                    chronometer.start();
//                    chronometer.setBase(SystemClock.elapsedRealtime() - elapsedTime1);
                }
//        createNotification();
            }
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // Unregister the broadcast receiver
//        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(notificationUpdateReceiver);
    }

    public void onDestroy() {
        super.onDestroy();

//        stopNotification();
    }

    private int getProgressStatus() {
        Log.d(TAG, "progressStatus " + progressStatus);
        return progressStatus;
    }

    private long getElapsedTime1() {
        return elapsedTime1;
    }
}