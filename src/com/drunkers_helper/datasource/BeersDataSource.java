package com.drunkers_helper.datasource;

import java.util.ArrayList;
import java.util.List;

import com.drunkers_helper.datasource.BeersDBHelper;




import com.drunkers_helper.util.globalconstant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

public class BeersDataSource {


	
	private SQLiteDatabase db;
	
	private BeersDBHelper beer_db_helper;
	
	private String[] Column_Counter = { BeersDBHelper.COL_COUNTER };
	private String[] Column_Name = { BeersDBHelper.COL_NAME };
	
	
	

	public BeersDataSource(Context context) {
		beer_db_helper = new BeersDBHelper(context);	
	}

	
	
	public void open() throws SQLException {
		
		db = beer_db_helper.getWritableDatabase();
	}

	public void close() {
		beer_db_helper.close();
	}
	
	
	
	/**
	 * Get all Beers on Data Base
	 * @return list of beers
	 */
	
	public List<String> selectAllBeers() {
		List<String> list = new ArrayList<String>();
		Cursor cursor = this.db.query(BeersDBHelper.BEERS_TABLE, new String[] { BeersDBHelper.COL_ID,
				BeersDBHelper.COL_NAME }, null, null, null, null, BeersDBHelper.COL_NAME + " ASC");
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
	
	/**
	 * Get beer ID by beer name
	 * @param name	Beer name
	 * @return 		beer id
	 */

	public int getBeerId(String name) {
		int result = -1;
		
		Cursor cursor = db.rawQuery("select " + BeersDBHelper.COL_ID + " from " + BeersDBHelper.BEERS_TABLE
				+ " where UPPER(" + BeersDBHelper.COL_NAME + ") = UPPER (?)", new String[] { name });

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

	/**
	 * Get beer name by beer ID
	 * @param id 	Beer Id (position on array list)
	 * @return		beer name or -1 if not exist 
	 */
	public String getBeerName(String id) {
		String result = "-1";
		
		if (globalconstant.LOG)
			Log.d(globalconstant.TAG, "Beer ID: " + id);
		
		
		Cursor cursor  = db.query(BeersDBHelper.BEERS_TABLE, Column_Name, 
				BeersDBHelper.COL_ID + " = ?" , new String[] { id }, null, null, null);
		

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

	/**
	 * Get number of beers by beer name
	 * @param beerName	Beer name 
	 * @return			Number of beers if not exist return 0
	 */
	public int getBeerNameCounter(String beerName) {
		int result = 0;
		
		
		Cursor cursor  = db.query(BeersDBHelper.BEER_COUNTER_TABLE, Column_Counter, 
				"UPPER("+ BeersDBHelper.COL_NAME + ") =  UPPER( ? )", new String[] { beerName }, null, null, null);

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
	
	/**
	 * Get number of beers by beer name
	 * @param beerName	Beer name 
	 * @return			Number of beers if not exist return -1
	 */
	public int getBeerCounter(String beerName) {
		int result = -1;
		
		Cursor cursor  = db.query(BeersDBHelper.BEER_COUNTER_TABLE, Column_Counter, 
				"UPPER("+ BeersDBHelper.COL_NAME + ") =  UPPER(?)", new String[] { beerName }, null, null, null);
		
		
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
	
	
	/**
	 * Get all beers on Data Base. Used to create shareIntent()
	 * @return	String with beers
	 */
	public String selectAllBeerCounter() {

		Cursor cursor = this.db.rawQuery("select " + BeersDBHelper.COL_COUNTER + " ||'-'|| " + BeersDBHelper.COL_NAME + " as _id  from " + BeersDBHelper.BEER_COUNTER_TABLE + " order by " + BeersDBHelper.COL_NAME + " ASC",new String[] {});
		
		String result="";
		if ( cursor.getCount() == 0){
			return result;
		}
	
		if (cursor.moveToFirst()) {

			do {
				result += cursor.getString(0).toString() + ", ";

			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}

		result = result.substring(0, result.length() - 2);
				

		return result;
	}
	
	
	/**
	 * Reset daily counter 
	 */
	public void resetCounterTable() {
		this.db.delete(BeersDBHelper.BEER_COUNTER_TABLE, null, null);
	}
	
	/**
	 * Delete beer by beer name
	 * @param beerName	
	 */
	public void deleteCounterName(String beerName) {
		db.delete(BeersDBHelper.BEER_COUNTER_TABLE, "UPPER(" + BeersDBHelper.COL_NAME + ") = UPPER(?)",
				new String[] { beerName });
	}
	
	
	/**
	 * Get all beers on Table counter
	 * @return	cursor with beers
	 */
	public Cursor selectCounterBeers() {
		Cursor cursor = this.db.rawQuery(
				"select " + BeersDBHelper.COL_NAME + " ||' - '|| " + BeersDBHelper.COL_COUNTER + " as _id  from "
						+ BeersDBHelper.BEER_COUNTER_TABLE + " order by " + BeersDBHelper.COL_NAME + " ASC",
				new String[] {});
		return cursor;
	}

	
	/**
	 * Increment beer counter by beer name
	 * @param beerName
	 * @return			total beers by name		
	 */
	public Integer incrementCounter(String beerName) {
		
		ContentValues insertValues = new ContentValues();
		ContentValues insertValuesHist = new ContentValues();
		int counter = getBeerCounter(beerName); 
		
		
		if (counter == -1) {
			insertValues.put(BeersDBHelper.COL_NAME, beerName);
			insertValues.put(BeersDBHelper.COL_COUNTER, 1);

			db.insert(BeersDBHelper.BEER_COUNTER_TABLE, null, insertValues);

		} else {
			insertValues.put(BeersDBHelper.COL_COUNTER, counter + 1);
			db.update(BeersDBHelper.BEER_COUNTER_TABLE, insertValues,
					"UPPER(" + BeersDBHelper.COL_NAME + ") = UPPER(?)", new String[] { beerName });
		}
		
		insertValuesHist.put(BeersDBHelper.COL_NAME, beerName);
		insertValuesHist.put(BeersDBHelper.COL_LOCATION, globalconstant.cityName);
		db.insert(BeersDBHelper.BEER_HISTORY_TABLE, null, insertValuesHist);
		return getBeerCounter(beerName);
	}

		
	/**
	 * Decrement beer counter by beer name
	 * @param beerName
	 * @return			total beers by name		
	 */
	public Integer decrementCounter(String beerName) {
		
		ContentValues insertValues = new ContentValues();
		int counter = getBeerCounter(beerName);
		
		if (globalconstant.LOG)
			Log.d(globalconstant.TAG, "counter: " + counter);

		if (counter > 1 && counter != -1) {
			insertValues.put(BeersDBHelper.COL_COUNTER, counter - 1);
			db.update(BeersDBHelper.BEER_COUNTER_TABLE, insertValues,
					"UPPER(" + BeersDBHelper.COL_NAME + ") = UPPER(?)", new String[] { beerName });
			counter = counter - 1;
			deleteHistory(beerName);
		} else if (counter == 1) {
			counter = 0;
			deleteCounterName(beerName);
			deleteHistory(beerName);
			
		} else if (counter == -1) {
			counter = 0;
		}
		
		return counter;
	}
	
	
	/**
	 * Delete register from table history. Used to delete one register of beer. 
	 * @param beerName
	 */
	public void deleteHistory(String beerName){
		
		 db.delete(BeersDBHelper.BEER_HISTORY_TABLE, "UPPER(" + BeersDBHelper.COL_NAME + ") = UPPER(?) and " + BeersDBHelper.COL_ID + " = (select max(" + BeersDBHelper.COL_ID + ") from " + BeersDBHelper.BEER_HISTORY_TABLE +")" ,new String[] { beerName });			
		
	}
	
	
	public Cursor selectAllHistory() {
		Cursor cursor = this.db.rawQuery(
				"select " + BeersDBHelper.COL_NAME + " ||' - '||date as _id, " + BeersDBHelper.COL_LOCATION + " from "	+ BeersDBHelper.BEER_HISTORY_TABLE + " order by date DESC",
				new String[] {});
		return cursor;
			}	
	
	
	
	public void addNewBeer(String beerName){
		ContentValues insertValues = new ContentValues();
		
		try{
			insertValues.put(BeersDBHelper.COL_NAME, beerName);
			db.insert(BeersDBHelper.BEERS_TABLE, null, insertValues);
		}
		catch(SQLiteException exception){			
			
		}		
	}
	
	

	

}