package com.sid.locationtracker;

import com.sid.tabsswipe.adapter.TabsPagerAdapter;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;


//FragmentActivity and ActionBar.TabListener are used to implement Swipeable Tabs
public class MainActivity extends FragmentActivity implements
ActionBar.TabListener {
	
	private ViewPager viewPager;
    private TabsPagerAdapter tabsAdapter;
    private ActionBar actionBar;
    
    //Tab titles are stored in a string array
    private String[] tabs = { "LocationListener", "BroadcastReceiver" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// ViewPager of the XML file
        viewPager = (ViewPager) findViewById(R.id.main_swipeable_tabs);
        actionBar = getActionBar();
        
        //Creating instance for TabsPagerAdapter
        tabsAdapter = new TabsPagerAdapter(getSupportFragmentManager(), this);
 
        //Linking the View pages and tabs
        viewPager.setAdapter(tabsAdapter);
        
        //Disable Home button on the ActionBar
        actionBar.setHomeButtonEnabled(false);
        
        //Enable Tabs in the action bar for display
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);        
 
        // Adding Tabs to the action bar
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }
        
        // On swiping the ViewPager the respective tabs are selected
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
         
            @Override
            public void onPageSelected(int position) {
                // On changing the page, make respected tab as selected
                actionBar.setSelectedNavigationItem(position);
            }
         
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }
         
            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
	}
	
	//Overriding the methods of ActionBar.TabListener interface
	@Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {
    }
 
	//Overriding the methods of ActionBar.TabListener interface
    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        // on tab selected
        // show respected fragment view
        viewPager.setCurrentItem(tab.getPosition());
    }
 
    //Overriding the methods of ActionBar.TabListener interface
    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
    }
}
