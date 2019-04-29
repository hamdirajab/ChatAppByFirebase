package com.app4.hamdi.chat;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class DisplayAndSelectTypeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_and_select_type);

        ViewPager viewPager = findViewById(R.id.viewPager);

        displayFragment displayFragment = new displayFragment();
        SelectedGroupFragment selectedGroupFragment = new SelectedGroupFragment();
        SelectUserFragment selectUserFragment = new SelectUserFragment();
        ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
        fragmentArrayList.add(displayFragment);
        fragmentArrayList.add(selectUserFragment);
        fragmentArrayList.add(selectedGroupFragment);

        ArrayList<String> titleArrayList = new ArrayList<>();
        titleArrayList.add("All Massge");
        titleArrayList.add("Find One");
        titleArrayList.add("Find Group");


        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager() , titleArrayList , fragmentArrayList);
        viewPager.setAdapter(viewPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tab);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

    }
}
