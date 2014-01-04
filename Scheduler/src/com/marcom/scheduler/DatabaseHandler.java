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
	private static final String KEY_ID = "id";
	private static final String KEY_CLUBNAME = "clubName";
	private static final String KEY_DESC = "description";
	private static final String KEY_DATE = "date";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		//db.execSQL("CREATE TABLE " + CLUBS + "(id INTEGER PRIMARY KEY, clubName TEXT)");
		db.execSQL("CREATE TABLE " + CLUBS + "(" + KEY_ID + " INTEGER PRIMARY KEY, " +
				KEY_CLUBNAME + " TEXT)");
		db.execSQL("CREATE TABLE " + EVENTS + "(" + KEY_ID + " INTEGER PRIMARY KEY, " +
				KEY_CLUBNAME + " TEXT, " +
				KEY_DESC + " TEXT, " +
				KEY_DATE + " TEXT)");
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
		values.put(KEY_CLUBNAME, clubid);
		
		db.insert(CLUBS, null, values);
		db.close();
	}
	
	public ArrayList<String> getAllClubs() {
		ArrayList<String> clubs = new ArrayList<String>();
		
		// String selection = "SELECT clubName FROM " + CLUBS;
		String selection = "SELECT " + KEY_CLUBNAME + " FROM " + CLUBS;
		
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
		
		db.delete(CLUBS, KEY_CLUBNAME + " = ?", new String[] { id });
		db.close();
	}
	
	// crud operations for events
	public void addEvent(String clubid, String description, String time) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(KEY_CLUBNAME, clubid);
		values.put(KEY_DESC, description);
		values.put(KEY_DATE, time);
		
		// db.execSQL("CREATE TABLE " + EVENTS + "(id INTEGER PRIMARY KEY, clubName TEXT, description TEXT, date TEXT)");
		db.insert(EVENTS, null, values);
		db.close();
	}
	
	public ArrayList<String> getEventsById(String clubid) {
		ArrayList<String> events = new ArrayList<String>();
		
		// String selection = "SELECT description FROM " + EVENTS + " WHERE clubName='" + clubid + "' ORDER BY date";
		String selection = "SELECT " + KEY_DESC + " FROM " + EVENTS + " WHERE " + KEY_CLUBNAME + "='" + clubid + "' ORDER BY " + KEY_DATE;
		
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
	
	public ArrayList<String> getDateEventsById(String clubid) {
		ArrayList<String> events = new ArrayList<String>();
		// String selection = "SELECT description FROM " + EVENTS + " WHERE clubName='" + clubid + "' ORDER BY date";
		String selection = "SELECT " + KEY_DATE + " FROM " + EVENTS + " WHERE " + KEY_CLUBNAME + "='" + clubid + "' ORDER BY " + KEY_DATE;
		
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
		
		//db.delete(EVENTS, "clubName='" + clubid + "' AND description='" + desc + "'", null);
		db.delete(EVENTS, KEY_CLUBNAME + "='" + clubid + "' AND " + KEY_DESC + "='" + desc + "'", null);
		
		db.close();
	}
	
	public void deleteEventsByClubId(String clubid) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		//db.rawQuery("DELETE FROM " + EVENTS + " WHERE clubName='" + clubid + "'", null);
		
		//db.delete(EVENTS, "clubName='" + clubid + "'", null);
		db.delete(EVENTS, KEY_CLUBNAME + "='" + clubid + "'", null);
		
		db.close();
	}

}
