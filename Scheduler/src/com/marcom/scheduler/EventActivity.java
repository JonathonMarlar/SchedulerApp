package com.marcom.scheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
// import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;

public class EventActivity extends Activity {
	
	public final static String CLUB_ID_EVENT = "com.marcom.scheduler.CLUBNAME";
	ListView eventListView;
	DatabaseHandler db;
	ArrayList<String> events;
	ArrayAdapter<String> adapter;
	String clubid;
	
	// stuff for listview date and event
	// List<Map<String, String>> dateEvent;
	ArrayList<String> dates;
	SimpleAdapter simpadp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event);
		// Show the Up button in the action bar.
		setupActionBar();
		
		// change title text view
		Intent intent = getIntent();
		String title = intent.getStringExtra(ClubActivity.CLUB_NAME);
		TextView tv = (TextView) findViewById(R.id.clubNameField);
		tv.setText(title);
		clubid = title;
		
		// refresh the list
		updateList();
		
		// -------------------------------------------------------------------------------
		
		eventListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(final AdapterView<?> parent, View view,
					final int pos, long id) {
				// TODO Auto-generated method stub
				// String eventToRemove = (String) parent.getItemAtPosition(pos);
				//Log.v("event", eventToRemove);
				
				// db.deleteEvent(clubid, eventToRemove);
				
				AlertDialog.Builder alertDialogBuild = new AlertDialog.Builder(parent.getContext());
				alertDialogBuild.setTitle("Are you sure?");
				alertDialogBuild.setMessage("Are you sure you want to delete this event?");
				alertDialogBuild.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						@SuppressWarnings("unchecked")
						Map<String, String> m = (HashMap<String, String>) parent.getItemAtPosition(pos);
						
						String eventToRemove = m.get("Event");
						db.deleteEvent(clubid, eventToRemove);
						
						updateList();
					}
				});
				alertDialogBuild.setNegativeButton("No", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
				});
				AlertDialog alertDialog = alertDialogBuild.create();
				alertDialog.show();
				
				/*
				// Log.v("event", t);
				@SuppressWarnings("unchecked")
				Map<String, String> m = (HashMap<String, String>) parent.getItemAtPosition(pos);
				
				String eventToRemove = m.get("Event");
				db.deleteEvent(clubid, eventToRemove);
				
				onResume(); */
				return true;
			}
		});
		
		db.close();
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
		getMenuInflater().inflate(R.menu.event, menu);
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
	
	@Override
	protected void onResume() {
		super.onResume();
		
		// refresh the list
		updateList();
	}
	
	public void updateList() {
		eventListView = (ListView) findViewById(R.id.eventList);
		db = new DatabaseHandler(this);
		
		List<Map<String, String>> dateEvent = new ArrayList<Map<String, String>>();
		events = db.getEventsById(clubid);
		dates = db.getDateEventsById(clubid);
		
		for ( int i = 0; i < events.size(); i++ ) {
			Map<String, String> datum = new HashMap<String, String>(2);
			datum.put("Event", events.get(i));
			datum.put("Date", dates.get(i));
			dateEvent.add(datum);
		}
		
		simpadp = new SimpleAdapter(this, dateEvent,
				android.R.layout.simple_list_item_2,
				new String[] {"Event", "Date"},
				new int[] {android.R.id.text1, android.R.id.text2});
		
		eventListView.setAdapter(simpadp);
		
		db.close();
	}
	
	public void addEvent(View view) {
		Intent intent = new Intent(this, AddEventActivity.class);
		intent.putExtra(CLUB_ID_EVENT, clubid);
		startActivity(intent);
	}

}
