package com.example.hourglass;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
public class TwoPlayersNoPauseFragment extends Fragment {
    public TwoPlayersNoPauseFragment() {
        // Required empty public constructor
    }


    public static TwoPlayersNoPauseFragment newInstance(String param1, String param2) {
        TwoPlayersNoPauseFragment fragment = new TwoPlayersNoPauseFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);;
        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_two_players_no_pause, container, false);
    }

    @Override
    public void onResume(){
        super.onResume();
        if(getActivity() != null){
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        // if you want to enable the turn orientation then in onPause method you should write:
        // getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
    }

}