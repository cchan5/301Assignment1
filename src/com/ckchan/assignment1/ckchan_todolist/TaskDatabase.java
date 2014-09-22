package com.ckchan.assignment1.ckchan_todolist;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;
import android.util.SparseBooleanArray;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Carly on 15/09/2014.
 */
public class TaskDatabase implements DatabaseInterface {
    public static final String prefs = "TaskFile";
    public static final String prefs2 = "CheckedItemsFile";

    public void saveTaskData(Context context, ArrayList<String> taskStrArray) {
   
        SharedPreferences preferences = context.getSharedPreferences(prefs, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        
        JSONArray taskStrJsonArray = new JSONArray(taskStrArray);

        editor.putString("savedTasks", taskStrJsonArray.toString());
        editor.commit();
    }

    public ArrayList<String> loadTaskData(Context context,ArrayList<String> taskStrArray) throws JSONException {
        
        SharedPreferences preferences = context.getSharedPreferences(prefs, Context.MODE_PRIVATE);             
        
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
    
    public void saveCheckedItems(Context context, SparseBooleanArray checkedItemsArray, int itemCount ) throws JSONException {
    	
        SharedPreferences preferences = context.getSharedPreferences(prefs2, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        
        JSONArray checkedItemsJsonArray = new JSONArray();
        
        //Loops through the SparseBooleanArray
        //and adds each key-value pair into the JSON Array
        //Some code used from:
        //http://stackoverflow.com/questions/7999211/iterate-through-sparsearray
        boolean value;
        for (int i = 0; i < itemCount; i++) {
        	value = checkedItemsArray.get(i);
        	checkedItemsJsonArray.put(i, value);
        }
        
        editor.putString("savedCheckedItems", checkedItemsJsonArray.toString());
        editor.commit();
        
    }
    
    public JSONArray loadCheckedItems(Context context, JSONArray checkedItemsJsonArray) throws JSONException {
    	
        SharedPreferences preferences = context.getSharedPreferences(prefs2, Context.MODE_PRIVATE);             
        
        if (preferences != null) {
	        String checkedItemsJsonString = preferences.getString("savedCheckedItems", null);
	        
	        if (checkedItemsJsonString != null){
		        JSONArray checkedJsonArray = new JSONArray(checkedItemsJsonString);
		        checkedItemsJsonArray = checkedJsonArray;
		        
	        }
        }
    	return checkedItemsJsonArray;
    }
}
