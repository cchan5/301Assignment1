package com.ckchan.assignment1.adapter;

import com.ckchan.assignment1.ckchan_todolist.ArchiveFragment;
import com.ckchan.assignment1.ckchan_todolist.TodoFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
//This code was taken from:
//http://stackoverflow.com/questions/16149778/how-do-i-use-fragmentpageradapter-to-have-tabs-with-different-content 2014-09-06
public class TabsPagerAdapter extends FragmentPagerAdapter {
 
    public TabsPagerAdapter(FragmentManager fm) {
    	
        super(fm);
    }
 
    @Override
    public Fragment getItem(int i) {
 
        switch (i) {
        
        case 0:
            // TODO List fragment activity
            return new TodoFragment();
            
        case 1:
            // Archive fragment activity
            return new ArchiveFragment();
        };      
        return null;   
    }
 
    @Override
    public int getCount() {
    	
        // 2 Tabs
        return 2;
    }
}