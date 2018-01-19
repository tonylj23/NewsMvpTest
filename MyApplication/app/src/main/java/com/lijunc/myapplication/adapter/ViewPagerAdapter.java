package com.lijunc.myapplication.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.lang.annotation.Retention;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by lijunc on 2018/1/6.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    List<String> mTitles;
    List<Fragment> fragments;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        fragments=new ArrayList<>();
        mTitles=new ArrayList<>();
    }


    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        //每次调用 notifyDataSetChanged() 方法时，都会激活 getItemPosition方法
        //POSITION_NONE表示该 Item 会被 destroyItem方法remove 掉，然后重新加载，
        //POSITION_UNCHANGED表示不会重新加载，默认是 POSITION_UNCHANGED
        return PagerAdapter.POSITION_NONE;
    }

    public void setItems(List<Fragment> fragments,List<String> mTitles){
        this.fragments=fragments;
        this.mTitles=mTitles;
        notifyDataSetChanged();
    }

    public void setItems(List<Fragment> fragments,String[] mTitles){
        this.fragments=fragments;
        this.mTitles= Arrays.asList(mTitles);
        notifyDataSetChanged();
    }

    public void addItem(Fragment fragment,String title){
        fragments.add(fragment);
        mTitles.add(title);
        notifyDataSetChanged();
    }

    public void delItem(int position){
        mTitles.remove(position);
        fragments.remove(position);
        notifyDataSetChanged();
    }

    public int delItem(String title){
        int index=mTitles.indexOf(title);
        if(index!=-1){
            delItem(index);
        }
        return index;
    }
    public void swipItems(int fromPos,int toPos){
        Collections.swap(mTitles,fromPos,toPos);
        Collections.swap(fragments,fromPos,toPos);
        notifyDataSetChanged();
    }

    public void modifyTitle(int position,String title){
        mTitles.set(position,title);
        notifyDataSetChanged();
    }
}
