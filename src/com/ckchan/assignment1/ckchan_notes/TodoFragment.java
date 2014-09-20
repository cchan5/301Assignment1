package com.ckchan.assignment1.ckchan_notes;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

public class TodoFragment extends Fragment {
    private String message;
    private Button addButton;
    private ListView listView;
    private EditText editText;
    private ArrayList<String> taskStrArray;
    private ArrayAdapter<String> arrayAdapter;
    private Context context;
    private CheckedTextView checkedTextView;
    private SparseBooleanArray checkedItemsArray;
    private TaskDatabase taskDatabase = new TaskDatabase();


    //Creates the TODO tab
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    	context = getActivity();
        View rootView = inflater.inflate(R.layout.fragment_todo, container, false);
        View taskView = inflater.inflate(R.layout.task_textview, container,false);

        //Initialization
        addButton = (Button) rootView.findViewById(R.id.add_button);
        editText = (EditText) rootView.findViewById(R.id.editText);
        taskStrArray = new ArrayList<String>();
        arrayAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_multiple_choice,taskStrArray);
        listView = (ListView) rootView.findViewById(R.id.listView);

        listView.setAdapter(arrayAdapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        arrayAdapter.setNotifyOnChange(false);
        
        //Load saved tasks into listView
        loadTasks(taskStrArray,context);  	
        arrayAdapter.notifyDataSetChanged();
        
        //Check items that were previously checked
//        listView.setItemChecked(position, value)
        
        //Takes text from editText and puts it in a new row
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editText.getText().toString();
                taskStrArray.add(message);
                arrayAdapter.notifyDataSetChanged();
                editText.setText(null); //Reset the text field

            }
        });
        
        checkedTextView = (CheckedTextView) taskView.findViewById(R.id.checkedTextView);
        checkedTextView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (checkedTextView.isChecked()) {
					checkedTextView.setChecked(false);
				}else {
					checkedTextView.setChecked(true);
				}			
			}
		});
        //TODO: http://stackoverflow.com/questions/8846707/how-to-implement-a-long-click-listener-on-a-listview
       
        return rootView;
    }
  
    public void onPause() {
    	super.onPause();
	    saveTasks();
    }
    
//    public void onResume() {
//    	super.onResume();
//    	loadTasks(taskStrArray,context);
//    }
    
    public void onStop() {
    	super.onStop();
    	saveTasks();
    }
    
    public void onDestroy() {
    	super.onDestroy();
    	saveTasks();
    }
    
    private void saveTasks() {
	    taskDatabase.saveTaskData(taskStrArray, context);
	    checkedItemsArray = listView.getCheckedItemPositions();
	}
    
	private void loadTasks(ArrayList<String> taskStrArray,final Context context) {
		try {
			taskStrArray = taskDatabase.loadTaskData(taskStrArray,context);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
