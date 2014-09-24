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
public class TaskDatabase implements DatabaseInterface {
    public static final String taskPrefs = "TaskFile";
    public static final String archivePrefs = "ArchiveFile";

    public void saveTaskData(Context context, List<TodoTask> taskArray) throws JSONException {
   
        saveData(context, taskArray, taskPrefs);
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

        editor.putString("savedTasks", taskJsonArray.toString());
        editor.commit();
	}

    public List<TodoTask> loadTaskData(Context context) throws JSONException {
        
        return loadData(context, taskPrefs);
    }

	private List<TodoTask> loadData(Context context, String prefName) throws JSONException {
		SharedPreferences preferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);             
        ArrayList<TodoTask> taskArray = new ArrayList<TodoTask>();
        
        if (preferences != null) {
        	
	        String taskStrJsonString = preferences.getString("savedTasks", null);
	        
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
