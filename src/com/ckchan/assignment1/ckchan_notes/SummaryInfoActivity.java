package com.ckchan.assignment1.ckchan_notes;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

//Activity displays total number of tasks,
//number of archived tasks,
//number of complete and incomplete tasks,
//and number of complete and incomplete archived tasks
public class SummaryInfoActivity extends Activity {
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_info);
 
        // Get action bar  
        ActionBar actionBar = getActionBar();
 
        // Enabling Up / Back navigation
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}
