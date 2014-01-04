package com.marcom.scheduler;

import java.util.ArrayList;
//import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
// import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ClubActivity extends Activity {
	
	ListView clubListView;
	DatabaseHandler db;
	ArrayList<String> clubs;
	ArrayAdapter<String> adapter;
	
	public final static String CLUB_NAME = "com.marcom.scheduler.CLUBNAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club);
        
        updateList();
        
        clubListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos,
					long id) {
				// TODO Auto-generated method stub
				/*
				String clubToRemove = (String) parent.getItemAtPosition(pos);
				db.removeClub(clubToRemove);
				
				// update
				onResume();
				*/
				String clb = (String) parent.getItemAtPosition(pos);
				gotoEventActivity(clb);
			}
        	
		});
        
        clubListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(final AdapterView<?> parent, View view,
					final int pos, long id) {
				// TODO Auto-generated method stub
				// Log.v("long clicked","pos: " + pos);
				
				AlertDialog.Builder alertDialogBuild = new AlertDialog.Builder(parent.getContext());
				alertDialogBuild.setTitle("Are you sure?");
				alertDialogBuild.setMessage("Are you sure you want to delete this class/club?");
				alertDialogBuild.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						String clubToRemove = (String) parent.getItemAtPosition(pos);
						db.removeClub(clubToRemove);
						
						db.deleteEventsByClubId(clubToRemove);
						
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
				String clubToRemove = (String) parent.getItemAtPosition(pos);
				db.removeClub(clubToRemove);
				
				db.deleteEventsByClubId(clubToRemove);*/
				
				// onResume();
                return true;
			}
		});
        
        db.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.club, menu);
        return true;
    }
    
    
    
    @Override
    protected void onResume() {
    	super.onResume();
    	updateList();
    }
    
    public void updateList() {
    	clubListView = (ListView) findViewById(R.id.clubListView);
        db = new DatabaseHandler(this);
        clubs = db.getAllClubs();
        db.close();
        
        adapter = new ArrayAdapter<String>(this,
        		android.R.layout.simple_list_item_1,
        		clubs);
        clubListView.setAdapter(adapter);
    }
    
    public void addClass(View view) {
    	Intent intent = new Intent(this, AddClubActivity.class);
    	startActivity(intent);
    }
    
    public void gotoEventActivity(String club) {
    	Intent intent = new Intent(this, EventActivity.class);
    	intent.putExtra(CLUB_NAME, club);
    	startActivity(intent);
    }
}
