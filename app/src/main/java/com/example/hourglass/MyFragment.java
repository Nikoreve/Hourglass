package com.example.hourglass;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hourglass.settings.language.LanguageManager;

public class MyFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        LanguageManager languageManager = new LanguageManager(getContext());
        // Kanonika mesa sthn parametro ypirxe to "this" omws edw milame gia fragment epomenws
        // den mporei na apotelei to context toy.
        //ή mporei na doylepsei k etsi: LanguageManager languageManager = new LanguageManager(getActivity());
        languageManager.updateRecource(languageManager.getLang());
    }
}


