package com.hyeongpil.hpchating;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hyeongpil.hpchating.fragment.PeopleFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getFragmentManager().beginTransaction().replace(R.id.fl_main_frame, new PeopleFragment()).commit();
    }

}
