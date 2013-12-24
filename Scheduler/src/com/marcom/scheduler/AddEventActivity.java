package com.marcom.scheduler;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class AddEventActivity extends Activity {
	
	String cid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_event);
		// Show the Up button in the action bar.
		setupActionBar();
		
		Intent intent = getIntent();
		cid = intent.getStringExtra(EventActivity.CLUB_ID_EVENT);
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_event, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void addEvent(View view) {
		// info from widgets
		DatePicker dt = (DatePicker) findViewById(R.id.datePicker);
		EditText descField = (EditText) findViewById(R.id.eventDescriptionField);
		
		String desc = descField.getText().toString();
		String date = Integer.toString(dt.getMonth());
		date = date + "-" + Integer.toString(dt.getDayOfMonth());
		
		DatabaseHandler eventHandle = new DatabaseHandler(this);
		
		eventHandle.addEvent(cid, desc, date);
		finish();
	}

}
