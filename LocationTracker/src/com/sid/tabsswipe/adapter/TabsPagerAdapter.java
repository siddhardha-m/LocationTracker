package com.sid.tabsswipe.adapter;

import com.sid.tabsswipe.LocationListenerFragment;
import com.sid.tabsswipe.BroadcastReceiverFragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

//FragmentPagerAdapter provides the corresponding views for each tab
public class TabsPagerAdapter extends FragmentPagerAdapter {
	
	private Context ctx;
	
	//Constructor to create the tab views
	public TabsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        ctx = context;
    }
 
	//Method to get data of each tab
    @Override
    public Fragment getItem(int index) {
 
        switch (index) {
        case 0:
            // LocationListener fragment activity
            return new LocationListenerFragment(ctx);
        case 1:
            // BroadcastReceiver fragment activity
            return new BroadcastReceiverFragment(ctx);
        }
 
        return null;
    }
 
    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 2;
    }
}
