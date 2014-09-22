package com.ckchan.assignment1.ckchan_todolist;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.util.SparseBooleanArray;

public interface DatabaseInterface {
	
	public void saveTaskData(Context context, ArrayList<String> arrayList);
	public ArrayList<String> loadTaskData(Context context,ArrayList<String> arrayList) throws JSONException;
	
	public void saveCheckedItems(Context context, SparseBooleanArray checkedItemsArray, int itemCount) throws JSONException;
	public JSONArray loadCheckedItems(Context context, JSONArray checkedItemsJsonArray) throws JSONException;
}
