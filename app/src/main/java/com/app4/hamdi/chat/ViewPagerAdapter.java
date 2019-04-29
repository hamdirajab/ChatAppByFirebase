package com.app4.hamdi.chat;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    ArrayList<Fragment> fragmentArrayList;
    ArrayList<String> title;
    public ViewPagerAdapter(FragmentManager fm , ArrayList<String> title , ArrayList<Fragment> fragmentArrayList) {
        super(fm);
        this.fragmentArrayList = fragmentArrayList;
        this.title = title;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title.get(position);
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentArrayList.get(i);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }
}
