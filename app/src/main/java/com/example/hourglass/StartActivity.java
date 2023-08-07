package com.example.hourglass;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class StartActivity extends MyAppCompatActivity {
    Fragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if()
        setContentView(R.layout.activity_start_activity);

        MainActivity mainActivityObject = new MainActivity();

        fragmentManager = getSupportFragmentManager();
        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }



            if (mainActivityObject.getUserPrefNoOfPlayersNP(this) == 1)
                if (mainActivityObject.getUserPrefWithPauseCB(this))
                    fragment = new OnePlayerWithPauseFragment();
                else
                    fragment = new OnePlayerNoPauseFragment();
            else if (mainActivityObject.getUserPrefWithPauseCB(this))
                fragment = new TwoPlayersWithPauseFragment();
            else
                fragment = new TwoPlayersNoPauseFragment();

            Bundle args = new Bundle();
            args.putBoolean("resetFirstTime", true);
            fragment.setArguments(args);

            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.commit();


        }
    }

}
