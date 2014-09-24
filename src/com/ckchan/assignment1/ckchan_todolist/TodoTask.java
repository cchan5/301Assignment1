package com.ckchan.assignment1.ckchan_todolist;

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
	
	public String toString() {
		return taskDescription;
	}
}
