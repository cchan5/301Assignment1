package com.ckchan.assignment1.ckchan_todolist;

import org.json.JSONException;

import android.content.Context;

//Some code was from:
//http://developer.android.com/training/basics/fragments/communicating.html 2014-09-23
public interface TaskFragmentInterface {
	
	public void onArticleSelected();
	public void saveTasks() throws JSONException;
	public void loadTasks(final Context context);
}
//Portions of this page are modifications based on work created and shared by the Android Open Source Project and used according to terms described in the Creative Commons 2.5 Attribution License. 