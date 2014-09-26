package com.ckchan.assignment1.ckchan_todolist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;
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
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.ckchan.assignment1.adapter.TaskArrayAdapter;
import com.ckchan.assignment1.adapter.TaskArrayAdapter.ViewHolder;
import com.ckchan.assignment1.ckchan_notes.R;

public class TodoFragment extends Fragment implements TaskFragmentInterface{
	
    private Button addButton;
    private ListView listView;
    private EditText editText;
    private List<TodoTask> taskArray;
    private TaskArrayAdapter arrayAdapter;
    private Context context;
    private TaskDatabase taskDatabase = new TaskDatabase();
    private List<Integer> selectedPositions = new ArrayList<Integer>();


    //Creates the TODO tab
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    	
    	context = getActivity();
        View rootView = inflater.inflate(R.layout.fragment_todo, container, false);

        //Initialization
        addButton = (Button) rootView.findViewById(R.id.add_button);
        editText = (EditText) rootView.findViewById(R.id.editText);
        taskArray = new ArrayList<TodoTask>();
        arrayAdapter = new TaskArrayAdapter(context, taskArray);        
        listView = (ListView) rootView.findViewById(R.id.listView);       
        listView.setAdapter(arrayAdapter);
        
        //Toggle check boxes when clicked
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	@Override
        	public void onItemClick(AdapterView<?> parent, View item, int position, long id) {
        		TodoTask todoTask = arrayAdapter.getItem(position);
        		todoTask.toggleChecked();
        		ViewHolder viewHolder = (ViewHolder) item.getTag();
        		viewHolder.getCheckBox().setChecked(todoTask.isChecked());
        	}
		});
        
        //Use contextual action bar(CAB) to select multiple items
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
				menuInflater.inflate(R.menu.todo_contextual_menu, menu);				
				return true;
			}
			
			@Override
			public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
				
				switch (item.getItemId()) {
				case R.id.delete_items:
					
					int deletedCount = 0; //Used to make sure indices of the arraylist match the values in selectedPostions
					Collections.sort(selectedPositions);
					for (Integer position : selectedPositions) {
						
						TodoTask removedTask = taskArray.remove(position.intValue() - deletedCount);
						arrayAdapter.remove(removedTask);
						arrayAdapter.notifyDataSetChanged();
						deletedCount++;
					}
					try {
						
						saveTasks();		
						selectedPositions.clear();
					} catch (JSONException e) {
						
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
					break;
				case R.id.archive_items:
					
					int archiveCount = 0;
					Collections.sort(selectedPositions);
					for (Integer position : selectedPositions) {
						
						TodoTask removedTask = taskArray.remove(position.intValue() - archiveCount);
						try {
							
							taskDatabase.appendArchiveTask(context, removedTask); //appends task to archiveArray stored in memory
							arrayAdapter.remove(removedTask);
							arrayAdapter.notifyDataSetChanged();
							saveTasks();
							archiveCount++;
						} catch (JSONException e) {
							
							// TODO Auto-generated catch block
							e.printStackTrace();
						}						
					}
					selectedPositions.clear();
					break;
				case R.id.email_items:
					
					Collections.sort(selectedPositions);
					
					//This code was from:
					//http://stackoverflow.com/questions/2197741/how-can-i-send-emails-from-my-android-application 2014-09-24
					try {
						
						Email email = taskDatabase.loadEmailAddress(context);
						
						//StringBuilder code from:
						//http://stackoverflow.com/questions/12899953/in-java-how-to-append-a-string-more-efficiently 2014-09-24
						StringBuilder stringBuilder = new StringBuilder();
						
						if (email != null) {
							
							Intent i = new Intent(Intent.ACTION_SEND);
							i.setType("message/rfc822");
							i.putExtra(Intent.EXTRA_EMAIL  , new String[]{email.getAddress()});
							i.putExtra(Intent.EXTRA_SUBJECT, "Todo Tasks");
							
							for (Integer position : selectedPositions) {
								
								TodoTask task = taskArray.get(position);
								stringBuilder.append(task.getTaskDescription() + "\n");
							}	
							
							String emailContent = stringBuilder.toString();
							i.putExtra(Intent.EXTRA_TEXT   , emailContent);
							
							try {
								
							    startActivity(Intent.createChooser(i, "Send mail..."));
							} catch (android.content.ActivityNotFoundException ex) {
								
							    Toast.makeText(context, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
							}
						}
						
					} catch (JSONException e) {
						
						// TODO Auto-generated catch block
						e.printStackTrace();
					}					
					selectedPositions.clear();
					break;
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
        
        //Takes text from editText and puts it in a new row when button is pressed
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
    
    //Loads saved tasks when tab is visible
	@Override
	public void onArticleSelected() {
		
		loadTasks(context);
		arrayAdapter.notifyDataSetChanged();
	}
	
    public void saveTasks() throws JSONException {
    	
	    taskDatabase.saveTaskData(context, taskArray);
	}
    
	public void loadTasks(final Context context) {
		
		try {
			
			taskArray = taskDatabase.loadTaskData(context);	
			
			if (taskArray != null) {
				
				arrayAdapter = new TaskArrayAdapter(context, taskArray);
				arrayAdapter.setArrayType("task");
				listView.setAdapter(arrayAdapter);
			}			
		} catch (JSONException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
