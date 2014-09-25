package com.ckchan.assignment1.ckchan_todolist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.ckchan.assignment1.adapter.TaskArrayAdapter;
import com.ckchan.assignment1.adapter.TaskArrayAdapter.ViewHolder;
import com.ckchan.assignment1.ckchan_notes.R;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.ListView;

//Creates Archive tab 
//Displays all archived tasks from TODO tab
public class ArchiveFragment extends Fragment implements TaskFragmentInterface{
	
    private ListView listView;
    private List<TodoTask> archiveArray;
    private TaskArrayAdapter arrayAdapter;
    private Context context;
    private TaskDatabase taskDatabase = new TaskDatabase();
    private List<Integer> selectedPositions = new ArrayList<Integer>();


    //Creates the Archive tab
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    	
    	context = getActivity();
        View rootView = inflater.inflate(R.layout.fragment_archive, container, false);

        //Initialization
        archiveArray = new ArrayList<TodoTask>();
        arrayAdapter = new TaskArrayAdapter(context, archiveArray);
        listView = (ListView) rootView.findViewById(R.id.listView1);

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
				menuInflater.inflate(R.menu.archive_contextual_menu, menu);				
				return true;
			}
			
			@Override
			public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
				
				switch (item.getItemId()) {
				case R.id.delete_items:
					
					int deletedCount = 0;
					Collections.sort(selectedPositions);
					for (Integer position : selectedPositions) {
						
						TodoTask removedTask = archiveArray.remove(position.intValue() - deletedCount);
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
				case R.id.unarchive_items:	
					int archiveCount = 0;
					Collections.sort(selectedPositions);
					for (Integer position : selectedPositions) {
						
						TodoTask removedTask = archiveArray.remove(position.intValue() - archiveCount);
						try {
							
							taskDatabase.appendTask(context, removedTask);
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
        

        return rootView;
    }
    

	@Override
	public void onArticleSelected() {
		
		loadTasks(context);
		arrayAdapter.notifyDataSetChanged();
	}
    
    private void saveTasks() throws JSONException {
    	
	    taskDatabase.saveArchiveData(context, archiveArray);
	}
    
	private void loadTasks(final Context context) {
		
		try {
			
			archiveArray = taskDatabase.loadArchiveData(context);	
			
			if (archiveArray != null) {
				
				arrayAdapter = new TaskArrayAdapter(context, archiveArray);
				listView.setAdapter(arrayAdapter);
			}			
		} catch (JSONException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
} 