package com.ckchan.assignment1.ckchan_todolist;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import com.ckchan.assignment1.ckchan_notes.R;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

//Creates Archive tab 
//Displays all archived tasks from TODO tab
public class ArchiveFragment extends Fragment {
	
	private ListView listView;
	private ArrayList<String> archiveStrArray;
    private ArrayAdapter<String> arrayAdapter;
    private Context context;
    private SparseBooleanArray checkedArchiveArray;
    private JSONArray checkedArchiveJsonArray;
    private ArchiveDatabase archiveDatabase = new ArchiveDatabase();
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
    	context = getActivity();
        View rootView = inflater.inflate(R.layout.fragment_archive, container, false);
        
        archiveStrArray = new ArrayList<String>();
        arrayAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_multiple_choice,archiveStrArray);
        listView = (ListView) rootView.findViewById(R.id.listView1);

        listView.setAdapter(arrayAdapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setLongClickable(true);
        arrayAdapter.setNotifyOnChange(false);
        
//        loadTasks(archiveStrArray,checkedArchiveJsonArray, context);  	
        arrayAdapter.notifyDataSetChanged();     
        
        return rootView;
    }
//    public void onPause() {
//    	
//    	super.onPause();
//	    try {
//	    	
//	    	saveTasks();
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
//    	loadTasks(archiveStrArray,checkedArchiveJsonArray, context);
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
    
//    private void saveTasks() throws JSONException {
//    	
//    	int count = listView.getAdapter().getCount();
//	    archiveDatabase.saveTaskData(context, archiveStrArray);
//	    checkedArchiveArray = listView.getCheckedItemPositions();
//	    archiveDatabase.saveCheckedItems(context, checkedArchiveArray, count);
//	}
//    
//	private void loadTasks(ArrayList<String> archiveStrArray, JSONArray checkedArchiveJsonArray, final Context context) {
//		
//		try {
//			
//			archiveStrArray = archiveDatabase.loadTaskData(context);
//			checkedArchiveJsonArray = archiveDatabase.loadCheckedItems(context);
//			
//			if (checkedArchiveJsonArray != null) {
//				
//				for (int i = 0; i < checkedArchiveJsonArray.length(); i++) {
//					
//					boolean isChecked = checkedArchiveJsonArray.getBoolean(i);
//					listView.setItemChecked(i, isChecked);
//				}
//			}		
//		} catch (JSONException e) {
//			
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
} 