package com.ckchan.assignment1.ckchan_todolist;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.SparseBooleanArray;

public class ArchiveDatabase implements DatabaseInterface {

    public static final String prefs = "ArchiveFile";
    public static final String prefs2 = "CheckedArchiveFile";

    public void saveTaskData(Context context, ArrayList<String> archiveStrArray) {
   
        SharedPreferences preferences = context.getSharedPreferences(prefs, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        
        JSONArray archiveStrJsonArray = new JSONArray(archiveStrArray);

        editor.putString("savedArchives", archiveStrJsonArray.toString());
        editor.commit();
    }

    public ArrayList<String> loadTaskData(Context context,ArrayList<String> archiveStrArray) throws JSONException {
        
        SharedPreferences preferences = context.getSharedPreferences(prefs, Context.MODE_PRIVATE);             
        
        if (preferences != null) {
	        String archiveStrJsonString = preferences.getString("savedArchives", null);
	        
	        if (archiveStrJsonString != null){
		        JSONArray archiveStrJsonArray = new JSONArray(archiveStrJsonString);
	        
		        for (int i = 0; i < archiveStrJsonArray.length(); i++) {
		        	archiveStrArray.add(archiveStrJsonArray.getString(i));
		        }
	        }
        }
        
        return archiveStrArray;
    }
    
    public void saveCheckedItems(Context context, SparseBooleanArray checkedArchiveArray, int itemCount) throws JSONException {
    	
        SharedPreferences preferences = context.getSharedPreferences(prefs2, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        
        JSONArray checkedArchiveJsonArray = new JSONArray();
        
        //Loops through the SparseBooleanArray
        //and adds each key-value pair into the JSON Array
        //Some code used from:
        //http://stackoverflow.com/questions/7999211/iterate-through-sparsearray
        boolean value;
        for (int i = 0; i < checkedArchiveArray.size(); i++) {
        	value = checkedArchiveArray.get(i);
        	checkedArchiveJsonArray.put(i, value);
        }
        
        editor.putString("savedCheckedArchives", checkedArchiveJsonArray.toString());
        editor.commit();
        
    }
    
    public JSONArray loadCheckedItems(Context context, JSONArray checkedArchiveJsonArray) throws JSONException {
    	
        SharedPreferences preferences = context.getSharedPreferences(prefs2, Context.MODE_PRIVATE);             
        
        if (preferences != null) {
	        String checkedArchiveJsonString = preferences.getString("savedCheckedArchives", null);
	        
	        if (checkedArchiveJsonString != null){
		        JSONArray checkedJsonArray = new JSONArray(checkedArchiveJsonString);
		        checkedArchiveJsonArray = checkedJsonArray;
		        
	        }
        }
    	return checkedArchiveJsonArray;
    }
}
