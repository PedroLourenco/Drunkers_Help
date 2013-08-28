package com.drunkers_helper.datasource;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BeersDBHelper extends SQLiteOpenHelper {
	
	
	//Data Base version
	public static final int DATABASE_VERSION = 1;
			
		public static final String DATABASE_NAME = "DrunkersHelper.db";
		
		//Tables
		public static final String BEERS_TABLE = "Beers_Name";
		public static final String BEER_COUNTER_TABLE = "Beer_Counter";
		public static final String BEER_HISTORY_TABLE = "Beer_History";
		
		//Columns
		public static final String COL_ID = "_id";
		public static final String COL_NAME = "Beer_Name";
		public static final String COL_COUNTER = "Counter";
		public static final String COL_LOCATION = "Location";
		public static final String COL_DATE = "Date";

		
		public BeersDBHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}	
		
		
		
		public long populateBeersNameTb(SQLiteDatabase db, String name) {
			ContentValues insertValues = new ContentValues();

			insertValues.put(COL_NAME, name);

			return db.insert(BEERS_TABLE, null, insertValues);
		}
		
		
		
	@Override
	public void onCreate(SQLiteDatabase db) {
				
		db.execSQL("CREATE TABLE "
				+ BEERS_TABLE 	+ "(" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_NAME + " LONGTEXT NOT NULL UNIQUE "+ ");");
		
		db.execSQL("CREATE TABLE "
				+ BEER_COUNTER_TABLE + "(" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_NAME + " LONGTEXT NOT NULL UNIQUE," + COL_COUNTER + " INTEGER NOT NULL " + ");");
	
		
		db.execSQL("CREATE TABLE "	+ BEER_HISTORY_TABLE + "(" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_NAME + " LONGTEXT NOT NULL," +  COL_DATE + " DEFAULT (datetime('now','localtime')), " + COL_LOCATION + " LONGTEXT" + ");"); 
				
	
		populateBeersNameTb(db, "Amigos");
		populateBeersNameTb(db, "Amstel");
		populateBeersNameTb(db, "Asahi Black");
		populateBeersNameTb(db, "Asahi");
		populateBeersNameTb(db, "Bajitnka");
		populateBeersNameTb(db, "Banks Caribbean");			
		populateBeersNameTb(db, "Baltika");
		populateBeersNameTb(db, "Bavaria");	
		populateBeersNameTb(db, "Becks");
		populateBeersNameTb(db, "Birra Moretti");
		populateBeersNameTb(db, "Bitburger");
		populateBeersNameTb(db, "Brahama");
		populateBeersNameTb(db, "Bochkovoe");			
		populateBeersNameTb(db, "Bud Light Chelada");			
		populateBeersNameTb(db, "Budweiser");
		populateBeersNameTb(db, "Buyjiu");
		populateBeersNameTb(db, "Carlsberg");
		populateBeersNameTb(db, "Carta Blanca");
		populateBeersNameTb(db, "Chang");
		populateBeersNameTb(db, "Cobra");
		populateBeersNameTb(db, "Coral");
		populateBeersNameTb(db, "Corona");
		populateBeersNameTb(db, "Cristal");
		populateBeersNameTb(db, "Cruzcampo");
		populateBeersNameTb(db, "Daura");
		populateBeersNameTb(db, "Dos Equis");
		populateBeersNameTb(db, "Duff");
		populateBeersNameTb(db, "Duvel");
		populateBeersNameTb(db, "Estrella");
		populateBeersNameTb(db, "Falcon");
		populateBeersNameTb(db, "Fino");
		populateBeersNameTb(db, "Foster");
		populateBeersNameTb(db, "Guiness");
		populateBeersNameTb(db, "Heineken");
		populateBeersNameTb(db, "Karhu");
		populateBeersNameTb(db, "karlovacko");
		populateBeersNameTb(db, "Kilkenny");
		populateBeersNameTb(db, "Kronenbourg 1994");
		populateBeersNameTb(db, "London Pride");
		populateBeersNameTb(db, "Mahou");
		populateBeersNameTb(db, "Mariestads Prima");
		populateBeersNameTb(db, "Modelo Especial");
		populateBeersNameTb(db, "Murphys");
		populateBeersNameTb(db, "Negra Modelo");
		populateBeersNameTb(db, "Super Bock");
		populateBeersNameTb(db, "Skol");
		populateBeersNameTb(db, "Old Speckled Hen");
		populateBeersNameTb(db, "Pacifico");
		populateBeersNameTb(db, "Palma Louca");
		populateBeersNameTb(db, "Peroni Nastro");
		populateBeersNameTb(db, "Pint");
		populateBeersNameTb(db, "Royal Dutch");
		populateBeersNameTb(db, "Sagres");
		populateBeersNameTb(db, "San Miguel");
		populateBeersNameTb(db, "Sol");
		populateBeersNameTb(db, "Stella Artois");
		populateBeersNameTb(db, "Strela");
		populateBeersNameTb(db, "Tagus");
		populateBeersNameTb(db, "Tecate");
		populateBeersNameTb(db, "Victoria");
	
	
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
