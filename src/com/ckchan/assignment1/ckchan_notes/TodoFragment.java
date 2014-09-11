package com.ckchan.assignment1.ckchan_notes;

import com.ckchan.assignment1.ckchan_notes.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
 
public class TodoFragment extends Fragment {
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_todo, container, false);
         
        return rootView;
    }
    
    //Adds a new task to the TODO list
    public void AddNewTask(View view) {
    	Intent intent = new Intent(this, AddNewTask.class);
    	EditText editText = (EditText) findViewById(R.id.add_message);
    	String message = editText.getText().toString();
    	
    }
}