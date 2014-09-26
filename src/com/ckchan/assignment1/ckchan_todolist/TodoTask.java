package com.ckchan.assignment1.ckchan_todolist;

//Implemented similar to Planet object from: http://stackoverflow.com/questions/9990676/how-can-i-get-values-from-checkbox-in-android 2014-09-23
public class TodoTask {
	
	private String taskDescription = "";
	private boolean checked = false;
	
	public TodoTask(String taskDescription, boolean taskCompleted) {
		
		this.taskDescription = taskDescription;
		this.checked = taskCompleted;
	}
	
	public String getTaskDescription() {
		
		return taskDescription;
	}
	
	public boolean isChecked() {
		
		return checked;
	}
	
	public void setChecked(boolean checked) {
		
		this.checked = checked;
	}
	
	public void setTaskDescription (String taskDescription) {
		
		this.taskDescription = taskDescription;
	}
	
	public void toggleChecked() {
		
		checked = !checked;
	}
}
