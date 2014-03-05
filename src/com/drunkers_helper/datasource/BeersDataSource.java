package com.drunkers_helper.datasource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.drunkers_helper.datasource.BeersDBHelper;




import com.drunkers_helper.util.Globalconstant;

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
	private String[] Column_LAT = { BeersDBHelper.COL_LAT };
	
	
	

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
		
		if (Globalconstant.LOG)
			Log.d(Globalconstant.TAG, "Beer ID: " + id);
		
		
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
				"select '* '||" + BeersDBHelper.COL_NAME + " ||': '|| " + BeersDBHelper.COL_COUNTER + " as _id  from "
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
		insertValuesHist.put(BeersDBHelper.COL_LOCATION, Globalconstant.cityName);
		insertValuesHist.put(BeersDBHelper.COL_LAT, Globalconstant.latitude);
		insertValuesHist.put(BeersDBHelper.COL_LONG, Globalconstant.longitude);
		
		
		
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
		
		if (Globalconstant.LOG)
			Log.d(Globalconstant.TAG, "counter: " + counter);

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
	
	
	
	
	/**
	 * Get Top Ten
	 * @param beerName	Beer name 
	 * @return			Number of beers if not exist return 0
	 */
	public Cursor getTopTenBeerss() {
		Cursor cursor = this.db.rawQuery(
				"select " + BeersDBHelper.COL_NAME + ",count(*) as _id  from "
						+ BeersDBHelper.BEER_HISTORY_TABLE + " group by " + BeersDBHelper.COL_NAME + " order by _id DESC LIMIT 10",
				 new String[] {});
		
		
		return cursor;
	}
	
	
	/**
	 * Get Last Day beers
	 * @param beerName	Beer name 
	 * @return			Number of beers if not exist return 0
	 */
	public Cursor getLastDayBeers() {
		
		Cursor cursor = this.db.rawQuery(
				"select " + BeersDBHelper.COL_NAME + ",count(*) as _id  from "
						+ BeersDBHelper.BEER_HISTORY_TABLE + " WHERE " + BeersDBHelper.COL_DATE + " BETWEEN datetime('now', 'start of day') AND datetime('now', 'localtime') group by " + BeersDBHelper.COL_NAME + " order by _id DESC",
				 new String[] {});
		
		return cursor;
	}
	
	
	/**
	 * Get Last Day beers total
	 * @param beerName	Beer name 
	 * @return			Number of beers if not exist return 0
	 */
	public String getLastDayBeersTotal() {
		
		Cursor cursor = this.db.rawQuery(
				"select " + " count(*)  from "
						+ BeersDBHelper.BEER_HISTORY_TABLE + " WHERE " + BeersDBHelper.COL_DATE + " BETWEEN datetime('now', 'start of day') AND datetime('now', 'localtime')",
				 new String[] {});
		
		String result="";
		if (cursor.moveToFirst()) {

			do {
				result = cursor.getString(0).toString();				

			} while (cursor.moveToNext());
		}
	
		return result;
	}
	
	
	
	
	
	
	
	
	/**
	 * Get Last Week beers
	 * @param beerName	Beer name 
	 * @return			Number of beers if not exist return 0
	 */
	public Cursor getLastWeekBeers() {
		
		Cursor cursor = this.db.rawQuery(
				"select " + BeersDBHelper.COL_NAME + ",count(*) as _id  from "
						+ BeersDBHelper.BEER_HISTORY_TABLE + " WHERE " + BeersDBHelper.COL_DATE + " BETWEEN datetime('now', '-6 days') and datetime('now', 'localtime') group by " + BeersDBHelper.COL_NAME + " order by _id DESC",
				 new String[] {});
		
		return cursor;
	}
	
	
	/**
	 * Get Last Week beers total
	 * @param beerName	Beer name 
	 * @return			Number of beers if not exist return 0
	 */
	public String getLastWeekBeersTotal() {
		
		
		Cursor cursor = this.db.rawQuery(
				"select " +  "count(*)  from "
						+ BeersDBHelper.BEER_HISTORY_TABLE + " WHERE " + BeersDBHelper.COL_DATE + " BETWEEN datetime('now', '-6 days') and datetime('now', 'localtime')",
				 new String[] {});
		
		String result="";
		if (cursor.moveToFirst()) {

			do {
				result = cursor.getString(0).toString();

			} while (cursor.moveToNext());
		}
	
		return result;
				
	}
	
	
	
	/**
	 * Get Last Month beers
	 * @param beerName	Beer name 
	 * @return			Number of beers if not exist return 0
	 */
	public Cursor getLastMonthBeers() {
		
		String where= "DATE('now', '-30' days)";
		
		Cursor cursor = this.db.rawQuery(
				"select " + BeersDBHelper.COL_NAME + ",count(*) as _id  from "
						+ BeersDBHelper.BEER_HISTORY_TABLE + " WHERE " + BeersDBHelper.COL_DATE + " BETWEEN datetime('now', 'start of month') AND datetime('now', 'localtime') group by " + BeersDBHelper.COL_NAME + " order by _id DESC",
				 new String[] {});
		
		
		
		return cursor;
	}
	
	
	/**
	 * Get Last Month beers total
	 * @param beerName	Beer name 
	 * @return			Number of beers if not exist return 0
	 */
	public String getLastMonthBeersTotal() {
		
		String where= "DATE('now', '-30' days)";
		
		Cursor cursor = this.db.rawQuery(
				"select " +  "count(*)  from "
						+ BeersDBHelper.BEER_HISTORY_TABLE + " WHERE " + BeersDBHelper.COL_DATE + " BETWEEN datetime('now', 'start of month') AND datetime('now', 'localtime')",
				 new String[] {});
		
		String result="";
		if (cursor.moveToFirst()) {

			do {
				result = cursor.getString(0).toString();

			} while (cursor.moveToNext());
		}
	
		return result;
	}
	
	
	/**
	 * Get Last Year beers
	 * @param beerName	Beer name 
	 * @return			Number of beers if not exist return 0
	 */
	public Cursor getLastYearBeers() {
		
		Cursor cursor = this.db.rawQuery(
				"select " + BeersDBHelper.COL_NAME + ",count(*) as _id  from "
						+ BeersDBHelper.BEER_HISTORY_TABLE + " WHERE " + BeersDBHelper.COL_DATE + " BETWEEN datetime('now', 'start of year') AND datetime('now', 'localtime') group by " + BeersDBHelper.COL_NAME + " order by _id DESC",
				 new String[] {});
		
		return cursor;
	}
	
	
	/**
	 * Get Last Month beers total
	 * @param beerName	Beer name 
	 * @return			Number of beers if not exist return 0
	 */
	public String getLastYearBeersTotal() {
		
		Cursor cursor = this.db.rawQuery(
				"select " +  "count(*)  from "
						+ BeersDBHelper.BEER_HISTORY_TABLE + " WHERE " + BeersDBHelper.COL_DATE + " BETWEEN datetime('now', 'start of year') AND datetime('now', 'localtime')",
				 new String[] {});
		
		String result="";
		if (cursor.moveToFirst()) {

			do {
				result = cursor.getString(0).toString();

			} while (cursor.moveToNext());
		}
	
		return result;
	}
	
	
	
	/**
	 * Find resgiters without city name on table Beer History
	 * 
	 * @return  Cursor with all afected registers
	 */
	 
	public Map<String, String> getRegistersWithoutLocation(){
		
		Map<String,String> coordenadas = new HashMap<String,String>();
		Cursor cursor = this.db.rawQuery("select " + BeersDBHelper.COL_LAT + ", " + BeersDBHelper.COL_LONG + " from " + BeersDBHelper.BEER_HISTORY_TABLE + " where " + BeersDBHelper.COL_LOCATION + " is null",new String[] {});
		
		
		
		String result="";
		if ( cursor.getCount() == 0){
			
		}
	
		if (cursor.moveToFirst()) {

			do {
				
				coordenadas.put(cursor.getString(0).toString(), cursor.getString(1).toString());
				
				result += cursor.getString(0).toString() +"|";
				System.out.println("RESULT: " + result);

			} while (cursor.moveToNext());
		}
		
		
		Iterator it = coordenadas.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
	        System.out.println(pairs.getKey() + " = " + pairs.getValue());
	        //it.remove(); // avoids a ConcurrentModificationException
	    }
		return coordenadas;		
		
	}
	
	
	
	public void setLocationOnTableBeerHistory(String colunmId, String cityName){
		
		ContentValues insertValues = new ContentValues();
		
		insertValues.put(BeersDBHelper.COL_LOCATION, cityName);
		db.update(BeersDBHelper.BEER_HISTORY_TABLE, insertValues,  BeersDBHelper.COL_ID + " = ?", new String[] { colunmId });
		
	}
	

}