package com.ckchan.assignment1.ckchan_todolist;

import java.util.ArrayList;

import org.json.JSONException;

import com.ckchan.assignment1.adapter.TabsPagerAdapter;
import com.ckchan.assignment1.ckchan_notes.R;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends FragmentActivity implements ActionBar.TabListener{
	
    private ViewPager viewPager;
    private TabsPagerAdapter tabsPagerAdapter;
    private ActionBar actionBar;
    
    // Tab titles
    //In string array in case more tabs need to be added
    private String[] tabs = { "TODO", "Archive" };
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialization of viewPager
        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();
        tabsPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(tabsPagerAdapter);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Adding Tabs
        for (String tab_name : tabs) {
        	
            actionBar.addTab(actionBar.newTab().setText(tab_name).setTabListener(this));
        }
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
         
	        @Override
	        public void onPageSelected(int position) {
	        	
	        	//Execute code in onArticleSelected when tab is selected
	            switch (position) {
	            case 0:
	            	//This code was from:
	            	//http://stackoverflow.com/questions/20412379/viewpager-update-fragment-on-swipe 2014-09-23
	            	TodoFragment todoFragment = (TodoFragment) tabsPagerAdapter.instantiateItem(viewPager, position);
	            	if (todoFragment != null) {
	            		todoFragment.onArticleSelected();
	            	}
	            	break;
	            case 1:
	            	ArchiveFragment archiveFragment = (ArchiveFragment) tabsPagerAdapter.instantiateItem(viewPager, position);
	            	if (archiveFragment != null) {
	            		archiveFragment.onArticleSelected();
	            	}
	            	break;
	            }    	
	            actionBar.setSelectedNavigationItem(position); //Switch to selected tab
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
        	Settings();
            return true;
        }
        
        //Handle presses on the action bar items
        switch (item.getItemId()) {
	        case R.id.summary_info:
	            SummaryInfo();
	            return true;
	        case R.id.email_all:
	        	try {
					
	        		Context context = getApplicationContext();
					TaskDatabase taskDatabase = new TaskDatabase();;
					Email email = taskDatabase.loadEmailAddress(context);
					ArrayList<TodoTask> taskArray= (ArrayList<TodoTask>) taskDatabase.loadTaskData(context);
					ArrayList<TodoTask> archiveArray= (ArrayList<TodoTask>) taskDatabase.loadArchiveData(context);
					//StringBuilder code from:
					//http://stackoverflow.com/questions/12899953/in-java-how-to-append-a-string-more-efficiently
					StringBuilder stringBuilder = new StringBuilder();
					
					if (email != null) {
						
						Intent i = new Intent(Intent.ACTION_SEND);
						i.setType("message/rfc822");
						i.putExtra(Intent.EXTRA_EMAIL  , new String[]{email.getAddress()});
						i.putExtra(Intent.EXTRA_SUBJECT, "Todo and Archive Tasks");
						
						stringBuilder.append("Todo Tasks\n");
						for (TodoTask task : taskArray) {
							
							stringBuilder.append(task.getTaskDescription() + "\n");
						}	
						stringBuilder.append("Archive Tasks\n");
						for (TodoTask task : archiveArray) {
							
							stringBuilder.append(task.getTaskDescription() + "\n");
						}
						
						String emailContent = stringBuilder.toString();
						i.putExtra(Intent.EXTRA_TEXT   , emailContent);
						
						try {
							
						    startActivity(Intent.createChooser(i, "Send mail..."));
						} catch (android.content.ActivityNotFoundException ex) {
							
						    Toast.makeText(context, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
						}
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
	
	public class CheckedTasks {
		
		private boolean checked = false;
		
		public void setChecked(boolean checked) {
			this.checked = checked;
		}
		
		public boolean isChecked() {
			return checked;
		}
	}
	
	//Starts new SummaryInfo activity
    private void SummaryInfo() {
        Intent i = new Intent(MainActivity.this, SummaryInfoActivity.class);
        startActivity(i);
    }
    
    private void Settings() {
    	Intent i = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(i);
    }
   
}
