package com.ckchan.assignment1.ckchan_todolist;

import org.json.JSONException;

import com.ckchan.assignment1.ckchan_notes.R;
import com.ckchan.assignment1.ckchan_todolist.Email;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SettingsActivity extends Activity {
	
	Context context;
	EditText editText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		context = getApplicationContext();
		
		editText = (EditText) findViewById(R.id.editText1);
		Button	button = (Button) findViewById(R.id.button1);
		
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				TaskDatabase taskDatabase = new TaskDatabase();	
				Email email = new Email();
				
				email.setAddress(editText.getText().toString());
				try {
					
					taskDatabase.saveEmailAddress(context, email);
				} catch (JSONException e) {
					
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});	
	}
}