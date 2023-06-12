package com.bbostt.flyfagerstromtest.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class CustomViewPager extends FragmentPagerAdapter {
    private ArrayList<Fragment> mFragmentList;
    private ArrayList<String> mNameList; // tab isimlerini gösterecek

    public CustomViewPager(@NonNull FragmentManager fm){ // constructor oluşturduk
        super(fm);
        mFragmentList = new ArrayList<>();
        mNameList = new ArrayList<>();

    }

    @NonNull
    @Override
    public Fragment getItem(int position){
        return mFragmentList.get(position);
    }

    @Override
    public int getCount(){
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String name){
        mFragmentList.add(fragment);
        mNameList.add(name);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mNameList.get(position);
    }
}
