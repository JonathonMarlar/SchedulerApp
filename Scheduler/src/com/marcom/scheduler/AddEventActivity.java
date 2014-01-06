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
	public final static String CLUB_NAME = "com.marcom.scheduler.CLUBNAME";

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
		Intent upIntent = NavUtils.getParentActivityIntent(this);
		upIntent.putExtra(CLUB_NAME, cid);
		
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpTo(this, upIntent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void addEvent(View view) {
		// info from widgets
		DatePicker dt = (DatePicker) findViewById(R.id.datePicker);
		EditText descField = (EditText) findViewById(R.id.eventDescriptionField);
		
		String desc = descField.getText().toString();
		
		// date
		//String date = Integer.toString(dt.getMonth());
		// date = date + "-" + Integer.toString(dt.getDayOfMonth());
		int mon = dt.getMonth() + 1;
		int day = dt.getDayOfMonth();
		int year = dt.getYear();
		
		String date = "";
		
		date += Integer.toString(year) + "/";
		if ( mon < 10 )
			date += "0" + Integer.toString(mon) + "/";
		else date += Integer.toString(mon) + "/";
		if ( day < 10 )
			date += "0" + Integer.toString(day);
		else date += Integer.toString(day);
		
		/*
		if ( mon < 10 )
			date += "0" + Integer.toString(mon) + "/";
		else date += Integer.toString(mon) + "/";
		if ( day < 10 )
			date += "0" + Integer.toString(day) + "/";
		else date += Integer.toString(day) + "/";
		date += Integer.toString(year);
		*/
		
		
		/*************************************
		switch (mon) {
		case 0:
			date = "Jan " + Integer.toString(day); break;
		case 1:
			date = "Feb " + Integer.toString(day); break;
		case 2:
			date = "Mar " + Integer.toString(day); break;
		case 3:
			date = "Apr " + Integer.toString(day); break;
		case 4:
			date = "May " + Integer.toString(day); break;
		case 5:
			date = "Jun " + Integer.toString(day); break;
		case 6:
			date = "Jul " + Integer.toString(day); break;
		case 7:
			date = "Aug " + Integer.toString(day); break;
		case 8:
			date = "Sep " + Integer.toString(day); break;
		case 9:
			date = "Oct " + Integer.toString(day); break;
		case 10:
			date = "Nov " + Integer.toString(day); break;
		case 11:
			date = "Dec " + Integer.toString(day); break;
		default:
				date = "Feb 33"; break;
		}
		*/
		
		DatabaseHandler eventHandle = new DatabaseHandler(this);
		
		eventHandle.addEvent(cid, desc, date);
		finish();
	}

}
