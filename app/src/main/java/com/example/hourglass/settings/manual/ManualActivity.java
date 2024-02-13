package com.example.hourglass.settings.manual;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hourglass.MyAppCompatActivity;
import com.example.hourglass.R;
import com.example.hourglass.onClickInterface;

import java.util.ArrayList;
import java.util.Objects;

public class ManualActivity extends MyAppCompatActivity {
    Fragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Toolbar toolbar;
    TextView toolbarTitle;
    RecyclerView recyclerView;
    SubjectAdapter subjectAdapter;
//    CardView cardView;// = findViewById(R.id.cardview_manualActivity);
//    View view;// = findViewById(R.id.view_manual_arrowDown);
    String tag = "0";
    private onClickInterface onclickInterface;

    public ManualActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);
        toolbar = makeToolbar();

        fragmentManager = getSupportFragmentManager();
        if (findViewById(R.id.fragment_container_activityManual) != null) {
            if (savedInstanceState != null) {
                return;
            }
        }

        fragment = new GeneralManualFragment();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_activityManual, fragment, ""+0);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();

        ArrayList<Subject> subjectArrayList = new ArrayList<>();
        subjectArrayList.add(new Subject("General", R.drawable.hourglass_icon, true));
        subjectArrayList.add(new Subject("Settings",R.drawable.round_settings_24,false));
        subjectArrayList.add(new Subject("1PWP", R.drawable.person_65, R.drawable.round_replay_65, false));
        subjectArrayList.add(new Subject("1PNP", R.drawable.person_65, R.drawable.round_replay_40, false));
        subjectArrayList.add(new Subject("2PWP", R.drawable.two_people_65, R.drawable.round_pause_24, false));
        subjectArrayList.add(new Subject("2PNP", R.drawable.two_people_65, R.drawable.pause_button, false));

        onclickInterface = new onClickInterface() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void setClick(int i) {
                Toast.makeText(ManualActivity.this, "Position is " + i, Toast.LENGTH_LONG).show();

                switch (i) {
                    case 0:
                        fragment = new GeneralManualFragment();
                        tag = "0";
                        break;
                    case 1:
                        fragment = new SettingsManualFragment();
                        tag = "1";
                        break;
                    case 2:
                        fragment = new OnePWPmanualFragment();
                        tag = "2";
                        break;
                    case 3:
                        fragment = new OnePNPmanualFragment();
                        tag = "3";
                        break;
                    case 4:
                        fragment = new TwoPWPmanualFragment();
                        tag = "4";
                        break;
                    case 5:
                        fragment = new TwoPNPmanualFragment();
                        tag = "5";
                        break;
//                    default:
                }
//                Log.d(" ","tag is "+fragment.getTag());
//                if(!Objects.equals(getSupportFragmentManager().findFragmentByTag("" + i), fragment.getTag())) {

                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container_activityManual, fragment, ""+i);
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    fragmentTransaction.commit();
//                }
            }
        };

        recyclerView = findViewById(R.id.recyclerView_activityManual);
        subjectAdapter = new SubjectAdapter(subjectArrayList, onclickInterface);
        recyclerView.setAdapter(subjectAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));


    }

    public Toolbar makeToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");
        toolbarTitle = toolbar.findViewById(R.id.toolbar_title);

        toolbarTitle.setText(R.string.manualHomeButton);
        return toolbar;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }


}