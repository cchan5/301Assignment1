package com.ckchan.assignment1.ckchan_todolist;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.ckchan.assignment1.ckchan_notes.R;

public class TodoFragment extends Fragment implements AbsListView.MultiChoiceModeListener{
	
    private Button addButton;
    private ListView listView;
    private EditText editText;
    private ArrayList<String> taskStrArray;
    private ArrayAdapter<String> arrayAdapter;
    private Context context;
    private CheckedTextView checkedTextView;
    private SparseBooleanArray checkedItemsArray;
    private JSONArray checkedItemsJsonArray;
    private TaskDatabase taskDatabase = new TaskDatabase();


    //Creates the TODO tab
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
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
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
			
			@Override
			public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void onDestroyActionMode(ActionMode mode) {
				
				// TODO Auto-generated method stub			
			}
			
			@Override
			public boolean onCreateActionMode(ActionMode mode, Menu menu) {
				
				MenuInflater menuInflater = getActivity().getMenuInflater();
				menuInflater.inflate(R.menu.contextual_menu, menu);				
				return true;
			}
			
			@Override
			public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
				
				switch (item.getItemId()) {
				case R.id.delete_item:
					
				}
				return false;
			}
			
			@Override
			public void onItemCheckedStateChanged(ActionMode mode, int position,
					long id, boolean checked) {
				
				// TODO Auto-generated method stub	
			}
		});
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setLongClickable(true);
        arrayAdapter.setNotifyOnChange(false);
        
        //Load saved tasks into listView
        loadTasks(taskStrArray,checkedItemsJsonArray, context);  	
        arrayAdapter.notifyDataSetChanged();
        
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
        
        
        
//        checkedTextView = (CheckedTextView) taskView.findViewById(R.id.checkedTextView);
//        checkedTextView.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				if (checkedTextView.isChecked()) {
//					checkedTextView.setChecked(false);
//				}else {
//					checkedTextView.setChecked(true);
//				}			
//			}
//		});

        return rootView;
    }
    
  	@Override
	public boolean onCreateActionMode(ActionMode mode, Menu menu) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onDestroyActionMode(ActionMode mode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onItemCheckedStateChanged(ActionMode mode, int position,
			long id, boolean checked) {
		// TODO Auto-generated method stub
		
	}
	
    public void onPause() {
    	
    	super.onPause();
	    try {
	    	
			saveTasks();
		} catch (JSONException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void onResume() {
    	
    	super.onResume();
//    	loadTasks(taskStrArray,checkedItemsJsonArray, context);
    }
    
    public void onStop() {
    	
    	super.onStop();
    	try {
    		
			saveTasks();
		} catch (JSONException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void onDestroy() {
    	
    	super.onDestroy();
    	try {
    		
			saveTasks();
		} catch (JSONException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private void saveTasks() throws JSONException {
    	
    	int count = listView.getAdapter().getCount();
	    taskDatabase.saveTaskData(context, taskStrArray);
	    checkedItemsArray = listView.getCheckedItemPositions();
	    taskDatabase.saveCheckedItems(context, checkedItemsArray, count);
	}
    
	private void loadTasks(ArrayList<String> taskStrArray, JSONArray checkedItemsJsonArray, final Context context) {
		
		try {
			
			taskStrArray = taskDatabase.loadTaskData(context,taskStrArray);
			checkedItemsJsonArray = taskDatabase.loadCheckedItems(context, checkedItemsJsonArray);
			
			if (checkedItemsJsonArray != null) {
				
				for (int i = 0; i < checkedItemsJsonArray.length(); i++) {
					
					boolean isChecked = checkedItemsJsonArray.getBoolean(i);
					listView.setItemChecked(i, isChecked);
				}
			}			
		} catch (JSONException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
