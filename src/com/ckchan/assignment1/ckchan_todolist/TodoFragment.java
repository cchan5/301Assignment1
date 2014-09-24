package com.ckchan.assignment1.ckchan_todolist;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.AbsListView;
import android.widget.AdapterView;
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

import com.ckchan.assignment1.adapter.TaskArrayAdapter;
import com.ckchan.assignment1.adapter.TaskArrayAdapter.ViewHolder;
import com.ckchan.assignment1.ckchan_notes.R;

public class TodoFragment extends Fragment {
	
    private Button addButton;
    private ListView listView;
    private EditText editText;
//    private ArrayList<String> taskStrArray;
    private List<TodoTask> taskArray;
    private TaskArrayAdapter arrayAdapter;
    private Context context;
    private SparseBooleanArray checkedItemsArray;
    private JSONArray checkedItemsJsonArray;
    private TaskDatabase taskDatabase = new TaskDatabase();
    private List<Integer> selectedPositions = new ArrayList<Integer>();


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
        taskArray = new ArrayList<TodoTask>();
        arrayAdapter = new TaskArrayAdapter(context, taskArray);
        listView = (ListView) rootView.findViewById(R.id.listView);

        listView.setAdapter(arrayAdapter);
        
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	@Override
        	public void onItemClick(AdapterView<?> parent, View item, int position, long id) {
        		TodoTask todoTask = arrayAdapter.getItem(position);
        		todoTask.toggleChecked();
        		ViewHolder viewHolder = (ViewHolder) item.getTag();
        		viewHolder.getCheckBox().setChecked(todoTask.isChecked());
        	}
		});
        
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
					for (Integer position : selectedPositions) {
						TodoTask removedTask = taskArray.remove(position.intValue());
						arrayAdapter.remove(removedTask);
						arrayAdapter.notifyDataSetChanged();
					}
					try {
						saveTasks();
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				return false;
			}
			
			@Override
			public void onItemCheckedStateChanged(ActionMode mode, int position,
					long id, boolean checked) {
				
				if (checked) {
					selectedPositions.add(new Integer(position));
				}else {
					selectedPositions.remove(new Integer(position));
				}
				
				
			}
		});
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setLongClickable(true);
        arrayAdapter.setNotifyOnChange(false);
        
        //Load saved tasks into listView
        loadTasks(context);  	
        arrayAdapter.notifyDataSetChanged();
        
        //Takes text from editText and puts it in a new row
        addButton.setOnClickListener(new View.OnClickListener() {
        	
            @Override
            public void onClick(View v) {
            	
                TodoTask todoTask = new TodoTask(editText.getText().toString(),false);
                taskArray.add(todoTask);
                arrayAdapter.notifyDataSetChanged();
                editText.setText(null); //Reset the text field
                try {
					saveTasks();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });

        return rootView;
    }
    
	
//    public void onPause() {
//    	
//    	super.onPause();
//	    try {
//	    	
//			saveTasks();
//		} catch (JSONException e) {
//			
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    }
//    
//    public void onResume() {
//    	
//    	super.onResume();
////    	loadTasks(taskStrArray,checkedItemsJsonArray, context);
//    }
//    
//    public void onStop() {
//    	
//    	super.onStop();
//    	try {
//    		
//			saveTasks();
//		} catch (JSONException e) {
//			
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    }
//    
//    public void onDestroy() {
//    	
//    	super.onDestroy();
//    	try {
//    		
//			saveTasks();
//		} catch (JSONException e) {
//			
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    }
//    
    private void saveTasks() throws JSONException {
    	
	    taskDatabase.saveTaskData(context, taskArray);
	}
    
	private void loadTasks(final Context context) {
		
		try {
			
			taskArray = taskDatabase.loadTaskData(context);	
			
			if (taskArray != null) {
				// TODO: modify to use clear and add all TodoTasks from taskArray
				arrayAdapter = new TaskArrayAdapter(context, taskArray);
				listView.setAdapter(arrayAdapter);
			}			
		} catch (JSONException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
