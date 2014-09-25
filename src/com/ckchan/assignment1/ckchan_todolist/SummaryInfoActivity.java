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

	@Override
    protected void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_info);
        
		ArrayList<TodoTask> taskArray = new ArrayList<TodoTask>();
		ArrayList<TodoTask> archiveArray = new ArrayList<TodoTask>();
		Context context;
		TextView textView = (TextView) findViewById(R.id.textView1);

		int taskCount = 0;
		int checkedTaskCount = 0;
		int uncheckedTaskCount = 0;
		int archiveCount = 0;
		int checkedArchiveCount = 0;
		int uncheckedArchiveCount = 0;
		
        context = getApplicationContext();
        
        // Get action bar  
        ActionBar actionBar = getActionBar();
 
        // Enabling Up / Back navigation
        actionBar.setDisplayHomeAsUpEnabled(true);
               
        try {
        	
			taskArray = (ArrayList<TodoTask>) taskDatabase.loadTaskData(context);
			taskCount = taskArray.size();
			
			archiveArray = (ArrayList<TodoTask>) taskDatabase.loadArchiveData(context);
			archiveCount = archiveArray.size();
			
	        checkedTaskCount = getCheckCount(taskArray, checkedTaskCount);	  	        
	        checkedArchiveCount = getCheckCount(archiveArray, checkedArchiveCount);
	        
	        uncheckedTaskCount = getUncheckCount(taskArray, uncheckedTaskCount);	  	        
	        uncheckedArchiveCount = getUncheckCount(archiveArray, uncheckedArchiveCount);
	        
		} catch (JSONException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
        //Output to textView1
        textView.setText("Total number of tasks: " + taskCount + "\n");
        textView.append("Number of checked tasks: " + checkedTaskCount + "\n");
        textView.append("Number of unchecked tasks: " + uncheckedTaskCount + "\n");
        textView.append("Total number of archived tasks: " + archiveCount + "\n");
        textView.append("Number of checked archived tasks: " + checkedArchiveCount + "\n");
        textView.append("Number of unchecked archived tasks: " + uncheckedArchiveCount + "\n");
    }

	private int getCheckCount(ArrayList<TodoTask> taskArray, int checkedTaskCount) {
		if (taskArray != null) {
			
			for (TodoTask task : taskArray) {
				
				boolean checked = task.isChecked();	
				if (checked) {
					
					checkedTaskCount++;
				} 
			}
		}
		return checkedTaskCount;
	}
	
	private int getUncheckCount(ArrayList<TodoTask> taskArray, int uncheckedTaskCount) {
		if (taskArray != null) {
			
			for (TodoTask task : taskArray) {
				
				boolean checked = task.isChecked();	
				if (!checked) {
		
					uncheckedTaskCount++;
				}
			}
		}
		return uncheckedTaskCount;
	}
}
