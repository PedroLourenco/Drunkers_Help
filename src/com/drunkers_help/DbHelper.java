package com.drunkers_help;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper {

	private static final String DATABASE_NAME = "Beers.db";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_BEERS_NAME = "BeersName";
	private static final String TABLE_BEER_COUNTER = "BeerCounter";
	private static final String TABLE_BEER_HISTORY = "BeerHistory";
	public static final String BeerName = "BeerName";
	public static final String Photo = "Photo";
	public static final String Counter = "Counter";
	private static Context context;
	private SQLiteDatabase db;
	private GetCurrentLocation getCurrentLocation; 

	public DbHelper(Context context) {
		DbHelper.context = context;
		OpenHelper openHelper = new OpenHelper(DbHelper.context);
		this.db = openHelper.getWritableDatabase();
		this.getCurrentLocation = new GetCurrentLocation();
	}

	public void deleteAll() {
		this.db.delete(TABLE_BEERS_NAME, null, null);
	}

	public void dropTableBeersName() {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_BEERS_NAME);
	}

	public List<String> selectAllBeers() {
		List<String> list = new ArrayList<String>();
		Cursor cursor = this.db.query(TABLE_BEERS_NAME, new String[] { "id",
				"beerName" }, null, null, null, null, "beerName ASC");
		if (cursor.moveToFirst()) {
			do {
				list.add(cursor.getString(0));
				list.add(cursor.getString(1));
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return list;
	}

	public int getBeerId(String name) {
		int result = -1;
		Cursor cursor = db.rawQuery("select id from " + TABLE_BEERS_NAME
				+ " where UPPER(beerName) = UPPER (?)", new String[] { name });

		if (cursor.moveToFirst()) {
			do {
				result = cursor.getInt(0);
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return result;
	}

	public String getBeerName(String id) {
		String result = "-1";

		Cursor cursor = db.rawQuery("select BeerName from " + TABLE_BEERS_NAME
				+ " where id = ?", new String[] { id });

		if (cursor.moveToFirst()) {
			do {
				result = cursor.getString(0);
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return result;
	}

	public int getBeerNameCounter(String beerName) {
		int result = 0;
		Cursor cursor = db.rawQuery("select Counter from " + TABLE_BEER_COUNTER
				+ " where UPPER(beerName) = UPPER (?)",
				new String[] { beerName });

		if (cursor.moveToFirst()) {
			do {
				result = cursor.getInt(0);
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return result;
	}

	public int getBeerCounter(String beerName) {
		int result = -1;
		Cursor cursor = db.rawQuery("select Counter from " + TABLE_BEER_COUNTER
				+ " where UPPER(beerName) = UPPER (?)",
				new String[] { beerName });

		if (cursor.moveToFirst()) {
			do {
				result = cursor.getInt(0);
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return result;
	}

	public String selectAllBeerCounter() {

		Cursor cursor = this.db.rawQuery("select counter ||'-'|| beerName as _id  from " + TABLE_BEER_COUNTER + " order by beerName ASC",new String[] {});
		String result = "Today i drunk ";
		if (cursor.moveToFirst()) {

			do {
				result += cursor.getString(0).toString() + ",";

			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}

		result = result.substring(0, result.length() - 1)
				+ ".#DrunkersHelper for #Android";

		return result;
	}

	public void resetCounterTable() {
		this.db.delete(TABLE_BEER_COUNTER, null, null);
	}

	public void deleteCounterName(String beerName) {
		db.delete(TABLE_BEER_COUNTER, "UPPER(beerName) = UPPER(?)",
				new String[] { beerName });
	}

	public Cursor selectCounterBeers() {
		Cursor cursor = this.db.rawQuery(
				"select beerName ||' - '|| Counter as _id  from "
						+ TABLE_BEER_COUNTER + " order by beerName ASC",
				new String[] {});
		return cursor;
	}

	
	public Integer incrementCounter(String beerName) {
		
		ContentValues insertValues = new ContentValues();
		ContentValues insertValuesHist = new ContentValues();
		int counter = getBeerCounter(beerName); 

		if (counter == -1) {
			insertValues.put("beerName", beerName);
			insertValues.put(Counter, 1);

			db.insert(TABLE_BEER_COUNTER, null, insertValues);

		} else {
			insertValues.put(Counter, counter + 1);
			db.update(TABLE_BEER_COUNTER, insertValues,
					"UPPER(beerName) = UPPER(?)", new String[] { beerName });
		}
		
		insertValuesHist.put("beerName", beerName);
		//insertValuesHist.put("city", getCurrentLocation.getCityName());
		db.insert(TABLE_BEER_HISTORY, null, insertValuesHist);
		return getBeerCounter(beerName);
	}

	
	public Integer decrementCounter(String beerName) {
		
		ContentValues insertValues = new ContentValues();
		int counter = getBeerCounter(beerName);

		if (counter > 0 && counter != -1) {
			insertValues.put(Counter, counter - 1);
			db.update(TABLE_BEER_COUNTER, insertValues,
					"UPPER(beerName) = UPPER(?)", new String[] { beerName });
			counter = counter - 1;
			deleteHistory(beerName);
		} else if (counter == 0) {
			deleteCounterName(beerName);
			deleteHistory(beerName);
			
		} else if (counter == -1) {
			counter = 0;
		}
		
		return counter;
	}
	
	
	
	
	
	public void deleteHistory(String beerName){
		
		 db.delete(TABLE_BEER_HISTORY, "UPPER(beerName) = UPPER(?) and id = (select max(id) from " + TABLE_BEER_HISTORY +")" ,new String[] { beerName });			
		
	}
	
	
	public Cursor selectAllHistory() {
		Cursor cursor = this.db.rawQuery(
				"select beerName ||' - '||date as _id, city  from "	+ TABLE_BEER_HISTORY + " order by date DESC",
				new String[] {});
		return cursor;
			}	
	
	
	
	public void addNewBeer(String beerName){
		ContentValues insertValues = new ContentValues();
		
		try{
			insertValues.put(BeerName, beerName);
			db.insert(TABLE_BEERS_NAME, null, insertValues);
		}
		catch(SQLiteException exception){
			
			
		}

		 
		
	}
	
	

	private static class OpenHelper extends SQLiteOpenHelper {

		OpenHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		public long populateBeersNameTb(SQLiteDatabase db, String name) {
			ContentValues insertValues = new ContentValues();

			insertValues.put(BeerName, name);

			return db.insert(TABLE_BEERS_NAME, null, insertValues);
		}

		/*
		 * public long populateBeersNameTb(SQLiteDatabase db, String name, int
		 * image ){ ContentValues insertValues = new ContentValues(); Bitmap
		 * bitmap = BitmapFactory.decodeResource(context.getResources(), image);
		 * 
		 * ByteArrayOutputStream bos = new ByteArrayOutputStream();
		 * 
		 * bitmap.compress(CompressFormat.PNG, 0 , bos);
		 * 
		 * byte[] bitmapdata = bos.toByteArray();
		 * 
		 * insertValues.put(BeerName, name); insertValues.put(Photo,
		 * bitmapdata);
		 * 
		 * return db.insert(TABLE_NAME, null, insertValues); }
		 */
		
	
		
		
		

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE "
					+ TABLE_BEERS_NAME
					+ "(id INTEGER PRIMARY KEY autoincrement, BeerName TEXT not null unique, Photo BLOB)");
			db.execSQL("CREATE TABLE "
					+ TABLE_BEER_COUNTER
					+ " (beerName TEXT PRIMARY KEY not null unique, Counter INTEGER not null)");
			
			db.execSQL("CREATE TABLE "	+ TABLE_BEER_HISTORY + " (id INTEGER PRIMARY KEY autoincrement, BeerName TEXT, DATE default (datetime(current_timestamp)), City TEXT)");

			populateBeersNameTb(db, "Amigos");
			populateBeersNameTb(db, "Amstel");
			populateBeersNameTb(db, "Asahi Black");
			populateBeersNameTb(db, "Asahi");
			populateBeersNameTb(db, "Baltika");
			populateBeersNameTb(db, "Bavaria");
			populateBeersNameTb(db, "Becks");
			populateBeersNameTb(db, "Birra Moretti");
			populateBeersNameTb(db, "Bitburger");
			populateBeersNameTb(db, "Brahama");
			populateBeersNameTb(db, "Budweiser");
			populateBeersNameTb(db, "Carlsberg");
			populateBeersNameTb(db, "Chang");
			populateBeersNameTb(db, "Cobra");
			populateBeersNameTb(db, "Corona");
			populateBeersNameTb(db, "Cristal");
			populateBeersNameTb(db, "Duff");
			populateBeersNameTb(db, "Duvel");
			populateBeersNameTb(db, "Foster");
			populateBeersNameTb(db, "Guiness");
			populateBeersNameTb(db, "Heineken");
			populateBeersNameTb(db, "karlovacko");
			populateBeersNameTb(db, "Kilkenny");
			populateBeersNameTb(db, "Kronenbourg 1994");
			populateBeersNameTb(db, "London Pride");
			populateBeersNameTb(db, "Super Bock");
			populateBeersNameTb(db, "Old Speckled Hen");
			populateBeersNameTb(db, "Peroni Nastro");
			populateBeersNameTb(db, "Pint");
			populateBeersNameTb(db, "Royal Dutch");
			populateBeersNameTb(db, "Sagres");
			populateBeersNameTb(db, "San Miguel");
			populateBeersNameTb(db, "Stella Artois");
			populateBeersNameTb(db, "Strela");
			populateBeersNameTb(db, "Tagus");

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w("Example",
					"Upgrading database, this will drop tables and recreate.");
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_BEERS_NAME);
			onCreate(db);
		}
	}
}