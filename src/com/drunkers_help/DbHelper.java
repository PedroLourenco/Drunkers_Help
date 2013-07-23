package com.drunkers_help;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper {

   private static final String DATABASE_NAME = "Beers.db";
   private static final int DATABASE_VERSION = 1;
   private static final String TABLE_NAME = "BeersName";
   public static final String BeerID = "_id";
   public static final String BeerName = "BeerName";
   public static final String Photo = "Photo";
   private static Context context;
   private SQLiteDatabase db;
  
   

   public DbHelper(Context context) {
      DbHelper.context = context;
      OpenHelper openHelper = new OpenHelper(DbHelper.context);
      this.db = openHelper.getWritableDatabase();
     
   }

   

   public void deleteAll() {
      this.db.delete(TABLE_NAME, null, null);
   }
   
   
   public void dropTableBeersName(){	   
	  db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);	   
   }
   
     
   public List<String> selectAllBeers() {
      List<String> list = new ArrayList<String>();
     Cursor cursor = this.db.query(TABLE_NAME, new String[] { "id","beerName" },
        null, null, null, null, "beerName ASC");
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

   
   public int selectBeerId(String name) {
	      int result = -1; 
	      Cursor cursor = db.rawQuery("select id from " + TABLE_NAME + " where UPPER(beerName) = UPPER (?)", new String[] {name});	            
	     
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
   
   
   
   private static class OpenHelper extends SQLiteOpenHelper {

      OpenHelper(Context context) {
         super(context, DATABASE_NAME, null, DATABASE_VERSION);
      }

                  
      //insert beerName on table BeersName without photo
      public long populateBeersNameTb(SQLiteDatabase db, String name){
    	  ContentValues insertValues = new ContentValues();
    	  
    	  insertValues.put(BeerName, name);
    	     	 
    	  return db.insert(TABLE_NAME, null, insertValues);
      }

      
      
      
/*      
      public long populateBeersNameTb(SQLiteDatabase db, String name, int image ){
    	  ContentValues insertValues = new ContentValues();
    	  Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), image);

    	  ByteArrayOutputStream bos = new ByteArrayOutputStream();

    	  bitmap.compress(CompressFormat.PNG, 0 , bos);

    	  byte[] bitmapdata = bos.toByteArray();
    	  
    	  insertValues.put(BeerName, name);
    	  insertValues.put(Photo, bitmapdata);
    	 
    	  return db.insert(TABLE_NAME, null, insertValues);
      }
   
   
   */
      
      @Override
      public void onCreate(SQLiteDatabase db) {
         db.execSQL("CREATE TABLE " + TABLE_NAME + " (id INTEGER PRIMARY KEY autoincrement, BeerName TEXT not null unique, Photo BLOB)");
        
         populateBeersNameTb(db,"Amigos");
         populateBeersNameTb(db,"Amstel");
         populateBeersNameTb(db,"Asahi Black");
         populateBeersNameTb(db,"Asahi");         
         populateBeersNameTb(db,"Baltika");
         populateBeersNameTb(db,"Bavaria");
         populateBeersNameTb(db,"Becks");
         populateBeersNameTb(db,"Birra Moretti");
         populateBeersNameTb(db,"Bitburger");
         populateBeersNameTb(db,"Brahama");
         populateBeersNameTb(db,"Budweiser");
         populateBeersNameTb(db,"Carlsberg");
         populateBeersNameTb(db,"Chang");
         populateBeersNameTb(db,"Cobra");
         populateBeersNameTb(db,"Corona");         
         populateBeersNameTb(db,"Cristal");
         populateBeersNameTb(db,"Duff");
         populateBeersNameTb(db,"Duvel");
         populateBeersNameTb(db,"Foster");
         populateBeersNameTb(db,"Guiness");
         populateBeersNameTb(db,"Heineken");
         populateBeersNameTb(db,"Kilkenny");
         populateBeersNameTb(db,"Kronenbourg 1994");
         populateBeersNameTb(db,"London Pride");
         populateBeersNameTb(db,"Mini");
         populateBeersNameTb(db,"Old Speckled Hen");         
         populateBeersNameTb(db,"Peroni Nastro");
         populateBeersNameTb(db,"Pint");
         populateBeersNameTb(db,"Royal Dutch");
         populateBeersNameTb(db,"Sagres");
         populateBeersNameTb(db,"San Miguel");
         populateBeersNameTb(db,"Stella Artois");
         populateBeersNameTb(db,"Strela");
         populateBeersNameTb(db,"Superbock");
         populateBeersNameTb(db,"Tagus");         
         
      }
      
      
      

      @Override
      public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
         Log.w("Example", "Upgrading database, this will drop tables and recreate.");
         db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
         onCreate(db);
      }
   }
}