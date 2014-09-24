package com.ckchan.assignment1.adapter;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.ckchan.assignment1.ckchan_notes.R;
import com.ckchan.assignment1.ckchan_todolist.TaskDatabase;
import com.ckchan.assignment1.ckchan_todolist.TodoTask;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
//This code is from:
//http://stackoverflow.com/questions/9990676/how-can-i-get-values-from-checkbox-in-android 2014-09-23
public class TaskArrayAdapter extends ArrayAdapter<TodoTask>{
	
	private LayoutInflater inflater;
	private List<TodoTask> taskArray;
	private TaskDatabase taskDatabase;
	private Context context;
	
	public TaskArrayAdapter(Context context, List<TodoTask> taskArray) {
		
		super(context, R.layout.task_textview, taskArray);
		inflater = LayoutInflater.from(context);
		this.taskArray = taskArray;
		this.taskDatabase = new TaskDatabase();
		this.context = context;
	}
	
	public static class ViewHolder {
		
	    private CheckBox checkBox ;
	    private TextView textView ;
	    
	    public ViewHolder( TextView textView, CheckBox checkBox ) {
	      this.checkBox = checkBox ;
	      this.textView = textView ;
	    }
	    public CheckBox getCheckBox() {
	      return checkBox;
	    }
	    public void setCheckBox(CheckBox checkBox) {
	      this.checkBox = checkBox;
	    }
	    public TextView getTextView() {
	      return textView;
	    }
	    public void setTextView(TextView textView) {
	      this.textView = textView;
	    }   
	  }
	
	public View getView(int position, View convertView, ViewGroup parent) {	
		
		TodoTask todoTask = (TodoTask) this.getItem(position);
		TextView textView;
		CheckBox checkBox;
		
		if (convertView == null) {
			
//			 = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.task_textview, null);
		
			textView = (TextView) convertView.findViewById(R.id.textView);
			checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);
			convertView.setTag(new ViewHolder(textView, checkBox));
	
			checkBox.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					CheckBox checkBox2 = (CheckBox) v;
					TodoTask task = (TodoTask) checkBox2.getTag();
					task.setChecked(checkBox2.isChecked());
					try {
						if (taskArray.contains(task)) {
							taskArray.set(taskArray.indexOf(task), task);
						}
						taskDatabase.saveTaskData(context, taskArray);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		} else {
			
			ViewHolder viewHolder = (ViewHolder) convertView.getTag();
			checkBox = viewHolder.getCheckBox() ;
	        textView = viewHolder.getTextView() ;
		}
		
		textView.setText(todoTask.getTaskDescription());
		checkBox.setTag(todoTask);
		checkBox.setChecked(todoTask.isChecked());
		checkBox.setText("");
	
		return convertView;
	}
	
	public void setTaskArray(ArrayList<TodoTask> taskArray) {
		this.taskArray = taskArray;
	}

}
