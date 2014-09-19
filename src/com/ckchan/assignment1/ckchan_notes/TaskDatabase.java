package com.ckchan.assignment1.ckchan_notes;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import android.util.SparseBooleanArray;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Carly on 15/09/2014.
 */
public class TaskDatabase {
    public static final String prefs = "TaskFile";

    public void saveTaskData(ArrayList<String> taskStrArray, Context context) {
   
        SharedPreferences preferences = context.getSharedPreferences(prefs, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        
        JSONArray taskStrJsonArray = new JSONArray(taskStrArray);

        editor.putString("savedTasks", taskStrJsonArray.toString());
        editor.commit();
    }

    public ArrayList<String> loadTaskData(ArrayList<String> taskStrArray,Context context) throws JSONException {
        
        SharedPreferences preferences = context.getSharedPreferences(prefs, Context.MODE_PRIVATE);             
//        ArrayList<String> taskStrArray = new ArrayList<String>();
        
        if (preferences != null) {
	        String taskStrJsonString = preferences.getString("savedTasks", null);
	        
	        if (taskStrJsonString != null){
		        JSONArray taskStrJsonArray = new JSONArray(taskStrJsonString);
	        
		        for (int i = 0; i < taskStrJsonArray.length(); i++) {
		        	taskStrArray.add(taskStrJsonArray.getString(i));
		        }
	        }
        }
        
        return taskStrArray;
    }
    
//    public void saveCheckedItems(SparseBooleanArray checkedItemsArray, Context context) {
//    	
//    }
}
