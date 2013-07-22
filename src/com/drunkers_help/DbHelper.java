package com.drunkers_help;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

public class DbHelper {

   private static final String DATABASE_NAME = "Beers.db";
   private static final int DATABASE_VERSION = 1;
   private static final String TABLE_NAME = "BeersName";
   public static final String BeerID = "_id";
   public static final String BeerName = "BeerName";
   private Context context;
   private SQLiteDatabase db;
   private SQLiteStatement insertStmt;
   private static final String INSERT = "insert into "
      + TABLE_NAME + "(beerName) values (?)";
   

   public DbHelper(Context context) {
      this.context = context;
      OpenHelper openHelper = new OpenHelper(this.context);
      this.db = openHelper.getWritableDatabase();
      this.insertStmt = this.db.compileStatement(INSERT);
   }

   

   public void deleteAll() {
      this.db.delete(TABLE_NAME, null, null);
   }
   
   
   public void dropTableBeersName(){
	   
	  db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
	   
   }
   
   
   public long insertBeerName(String beerName) {
       this.insertStmt.bindString(1, beerName);
       return this.insertStmt.executeInsert();
    }

   public List<String> selectAll() {
      List<String> list = new ArrayList<String>();
     Cursor cursor = this.db.query(TABLE_NAME, new String[] { "id","beerName" },
        null, null, null, null, "beerName ASC");
      if (cursor.moveToFirst()) {
         do { 
            list.add(cursor.getString(0));
         } while (cursor.moveToNext());
      }
      if (cursor != null && !cursor.isClosed()) {
         cursor.close();
      }
      return list;
   }

   private static class OpenHelper extends SQLiteOpenHelper {

      OpenHelper(Context context) {
         super(context, DATABASE_NAME, null, DATABASE_VERSION);
      }

      
      
      
      public long populateBeersNameTb(SQLiteDatabase db, String name){
    	  ContentValues insertValues = new ContentValues();
    	  insertValues.put(BeerName, name);
    	 
    	  return db.insert(TABLE_NAME, null, insertValues);
      }
      
      @Override
      public void onCreate(SQLiteDatabase db) {
         db.execSQL("CREATE TABLE " + TABLE_NAME + " (id INTEGER PRIMARY KEY autoincrement, BeerName TEXT not null unique)");
         populateBeersNameTb(db,"Amigos");
         populateBeersNameTb(db,"Amstel");
         populateBeersNameTb(db,"Asahi Black");
         
      }

      @Override
      public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
         Log.w("Example", "Upgrading database, this will drop tables and recreate.");
         db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
         onCreate(db);
      }
   }
}