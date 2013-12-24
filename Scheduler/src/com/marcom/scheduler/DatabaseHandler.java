package com.marcom.scheduler;

import java.util.ArrayList;
//import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
	
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "schedule";
	
	// table names
	private static final String CLUBS = "clubs";
	private static final String EVENTS = "events";
	
	// keys
	// clubs
	// private static final String KEY_ID = "id";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE " + CLUBS + "(id INTEGER PRIMARY KEY, clubName TEXT)");
		db.execSQL("CREATE TABLE " + EVENTS + "(id INTEGER PRIMARY KEY, clubName TEXT, description TEXT, date TEXT)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + CLUBS);
		db.execSQL("DROP TABLE IF EXISTS " + EVENTS);
		onCreate(db);
	}
	
	// crud operations for clubs
	public void addClub(String clubid) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put("clubName", clubid);
		
		db.insert(CLUBS, null, values);
		db.close();
	}
	
	public ArrayList<String> getAllClubs() {
		ArrayList<String> clubs = new ArrayList<String>();
		
		String selection = "SELECT clubName FROM " + CLUBS;
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selection, null);
		
		if ( cursor.moveToFirst() ) {
			do {
				clubs.add(cursor.getString(0));
			} while (cursor.moveToNext());
		}
		
		db.close();
		
		return clubs;
	}
	
	public void removeClub(String id) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		// String selection = "DELETE FROM " + CLUBS + " WHERE clubName=" + id;
		
		db.delete(CLUBS, "clubName = ?", new String[] { id });
		db.close();
	}
	
	// crud operations for events
	public void addEvent(String clubid, String description, String time) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put("clubName", clubid);
		values.put("description", description);
		values.put("date", time);
		
		// db.execSQL("CREATE TABLE " + EVENTS + "(id INTEGER PRIMARY KEY, clubName TEXT, description TEXT, date TEXT)");
		db.insert(EVENTS, null, values);
		db.close();
	}
	
	public ArrayList<String> getEventsById(String clubid) {
		ArrayList<String> events = new ArrayList<String>();
		
		String selection = "SELECT description FROM " + EVENTS + " WHERE clubName='" + clubid + "' ORDER BY date";
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selection, null);
		
		if ( cursor.moveToFirst() ) {
			do {
				events.add(cursor.getString(0));
			} while (cursor.moveToNext());
		}
		
		db.close();
		
		return events;
	}
	
	public void deleteEvent(String clubid, String desc) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		//db.rawQuery("DELETE FROM " + EVENTS + " WHERE clubName='" + clubid + "' AND description='" + desc + "'", null);
		
		db.delete(EVENTS, "clubName='" + clubid + "' AND description='" + desc + "'", null);
		
		db.close();
	}
	
	public void deleteEventsByClubId(String clubid) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		//db.rawQuery("DELETE FROM " + EVENTS + " WHERE clubName='" + clubid + "'", null);
		
		db.delete(EVENTS, "clubName='" + clubid + "'", null);
		
		db.close();
	}

}
