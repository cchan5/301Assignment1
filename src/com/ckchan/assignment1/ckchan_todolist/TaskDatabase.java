package com.ckchan.assignment1.ckchan_todolist;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.List;

import android.util.SparseBooleanArray;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Carly on 15/09/2014.
 */

//JSONArray methods from: http://developer.android.com/reference/org/json/JSONArray.html 2014-09-20
//JSONObject methods from: http://www.json.org/javadoc/org/json/JSONObject.html 2014-09-24
//Shared Preferences from: http://developer.android.com/guide/topics/data/data-storage.html 2014-09-20
public class TaskDatabase implements DatabaseInterface {
	
    public static final String taskPrefs = "TaskFile";
    public static final String archivePrefs = "ArchiveFile";
    public static final String emailPrefs = "EmailAddress";

    public void saveTaskData(Context context, List<TodoTask> taskArray) throws JSONException {
   
        saveData(context, taskArray, taskPrefs);
    }
    
    public void saveArchiveData(Context context, List<TodoTask> archiveArray) throws JSONException {
    	   
        saveData(context, archiveArray, archivePrefs);
    }

    public List<TodoTask> loadTaskData(Context context) throws JSONException {
        
        return loadData(context, taskPrefs);
    }
    
    public List<TodoTask> loadArchiveData(Context context) throws JSONException {
        
        return loadData(context, archivePrefs);
    }
	
	public void appendTask (Context context, TodoTask todoTask) throws JSONException {
		
		ArrayList<TodoTask> taskArray = (ArrayList<TodoTask>) loadData(context, taskPrefs);
		taskArray.add(todoTask);
		saveData(context, taskArray, taskPrefs);
	}
	
	public void appendArchiveTask (Context context, TodoTask todoTask) throws JSONException {
		
		ArrayList<TodoTask> archiveArray = (ArrayList<TodoTask>) loadData(context, archivePrefs);
		archiveArray.add(todoTask);
		saveData(context, archiveArray, archivePrefs);
	}
	
	public void saveEmailAddress(Context context, Email email) throws JSONException{
		
		saveEmail(context, email, emailPrefs);
	}
	
	public Email loadEmailAddress(Context context) throws JSONException {
		
		return loadEmail(context, emailPrefs);
	}
	
	private Email loadEmail(Context context, String prefName) throws JSONException {
		
		SharedPreferences preferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
		
		String address = preferences.getString("emailAddress", null);
		Email email = new Email();
		email.setAddress(address);
		
		return email;
	}
	
	private void saveEmail(Context context, Email email, String prefName) throws JSONException {
		
		SharedPreferences preferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        
        JSONObject jsonEmail = new JSONObject();
        jsonEmail.put("address", email.getAddress());
        
        editor.putString("emailAddress", email.getAddress());
        editor.commit();
	}
	
	private void saveData(Context context, List<TodoTask> taskArray, String prefName)
			throws JSONException {
		
		SharedPreferences preferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        
        JSONArray taskJsonArray = new JSONArray();
        
        for (TodoTask task : taskArray) {
        	JSONObject jsonTask = new JSONObject();
        	jsonTask.put("taskDescription", task.getTaskDescription());
        	jsonTask.put("checked", task.isChecked());
        	taskJsonArray.put(jsonTask);
        }

        editor.putString(prefName, taskJsonArray.toString());
        editor.commit();
	}
	
	private List<TodoTask> loadData(Context context, String prefName) throws JSONException {
		
		SharedPreferences preferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);             
        ArrayList<TodoTask> taskArray = new ArrayList<TodoTask>();
        
        if (preferences != null) {
        	
	        String taskStrJsonString = preferences.getString(prefName, null);
	        
	        if (taskStrJsonString != null){
	        	
		        JSONArray taskStrJsonArray = new JSONArray(taskStrJsonString);
	        
		        for (int i = 0; i < taskStrJsonArray.length(); i++) {
		        	
		        	JSONObject task = taskStrJsonArray.getJSONObject(i);
		        	TodoTask todoTask = new TodoTask(task.getString("taskDescription"), task.getBoolean("checked"));
		        	taskArray.add(todoTask);
		        }
	        }
        }    
        return taskArray;
	}
}

//Portions of this page are modifications based on work created and shared by the Android Open Source Project and used according to terms described in the Creative Commons 2.5 Attribution License. 