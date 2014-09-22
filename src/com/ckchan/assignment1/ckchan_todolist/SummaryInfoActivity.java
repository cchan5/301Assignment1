package com.ckchan.assignment1.ckchan_todolist;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import com.ckchan.assignment1.ckchan_notes.R;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

//Activity displays total number of tasks,
//number of archived tasks,
//number of complete and incomplete tasks,
//and number of complete and incomplete archived tasks
public class SummaryInfoActivity extends Activity {
	
	private TaskDatabase taskDatabase = new TaskDatabase();
	private ArchiveDatabase archiveDatabase = new ArchiveDatabase();

	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		
		ArrayList<String> taskStrArray = new ArrayList<String>();
		ArrayList<String> archiveStrArray = new ArrayList<String>();
		Context context;
		JSONArray checkedItemsJsonArray = new JSONArray();
		JSONArray checkedArchiveJsonArray = new JSONArray();
		TextView textView;
		
		int taskCount = 0;
		int checkedItemsCount = 0;
		int archiveCount = 0;
		int checkedArchiveCount = 0;
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_info);
        context = getApplicationContext();
        
        // Get action bar  
        ActionBar actionBar = getActionBar();
 
        // Enabling Up / Back navigation
        actionBar.setDisplayHomeAsUpEnabled(true);
               
        textView = (TextView) findViewById(R.id.textView1);
        
        //Gets number of tasks and number of checked items from TODO list
        try {
        	
			taskStrArray = taskDatabase.loadTaskData(context, taskStrArray);
			checkedItemsJsonArray = taskDatabase.loadCheckedItems(context, checkedItemsJsonArray);
			taskCount = taskStrArray.size();
			
			archiveStrArray = archiveDatabase.loadTaskData(context, archiveStrArray);
			checkedArchiveJsonArray = archiveDatabase.loadCheckedItems(context, checkedArchiveJsonArray);
			archiveCount = archiveStrArray.size();
			
	        if (checkedItemsJsonArray != null) {
	        	
				for (int i = 0; i < checkedItemsJsonArray.length(); i++) {
					
					boolean isChecked = checkedItemsJsonArray.getBoolean(i);	
					if (isChecked == true) {
						
						checkedItemsCount++;
					}
				}
	        }	  	        
	        if (checkedArchiveJsonArray != null) {
	        	
				for (int i = 0; i < checkedArchiveJsonArray.length(); i++) {
					
					boolean isChecked = checkedArchiveJsonArray.getBoolean(i);	
					if (isChecked == true) {
						
						checkedArchiveCount++;
					}
				}
	        }
		} catch (JSONException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
        //Output to textView1
        textView.setText(taskCount + " total tasks.\n");
        textView.append(checkedItemsCount + " checked tasks.\n");
    }
}
