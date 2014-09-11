package com.ckchan.assignment1.ckchan_notes;

import com.ckchan.assignment1.adapter.TabsPagerAdapter;
import com.ckchan.assignment1.ckchan_notes.R;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends FragmentActivity implements ActionBar.TabListener{
    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;
    // Tab titles
    private String[] tabs = { "TODO", "Archive" };
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //Initialization of viewpager
        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
 
        viewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);  
        
        // Adding Tabs
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));     
        }
    
        
        //On swiping the viewpager make respective tab selected   
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
         
	        @Override
	        public void onPageSelected(int position) {
	            // on changing the page
	            // make respected tab selected
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    //Selecting action bar items
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        
        //Handle presses on the action bar items
        switch (item.getItemId()) {
	        case R.id.summary_info:
	            SummaryInfo();
	            return true;
	        case R.id.action_settings:
	            //TODO: May or may not need a settings button
	            return true;
	        default:
	        	return super.onOptionsItemSelected(item);
        }
    }


	@Override
	//When tabs are selected 
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		viewPager.setCurrentItem(tab.getPosition());
	}


	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
	
	//Starts new SummaryInfo activity
    private void SummaryInfo() {
        Intent i = new Intent(MainActivity.this, SummaryInfo.class);
        startActivity(i);
    }
    
    
   
}
