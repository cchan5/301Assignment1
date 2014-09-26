package com.ckchan.assignment1.ckchan_todolist;

import java.util.List;
import org.json.JSONException;
import android.content.Context;

public interface DatabaseInterface {
	
	public void saveTaskData(Context context, List<TodoTask> taskArray) throws JSONException;
	public List<TodoTask> loadTaskData(Context context) throws JSONException;
	
}