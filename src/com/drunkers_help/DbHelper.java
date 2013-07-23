package com.drunkers_help;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

public class DbHelper {

   private static final String DATABASE_NAME = "Beers.db";
   private static final int DATABASE_VERSION = 1;
   private static final String TABLE_NAME = "BeersName";
   public static final String BeerID = "_id";
   public static final String BeerName = "BeerName";
   public static final String Photo = "Photo";
   private static Context context;
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

                  
      public long populateBeersNameTb(SQLiteDatabase db, String name, int image ){
    	  ContentValues insertValues = new ContentValues();
    	  Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), image);

    	  ByteArrayOutputStream bos = new ByteArrayOutputStream();

    	  bitmap.compress(CompressFormat.PNG, 0 /*ignored for PNG*/, bos);

    	  byte[] bitmapdata = bos.toByteArray();
    	  
    	  insertValues.put(BeerName, name);
    	  insertValues.put(Photo, bitmapdata);
    	 
    	  return db.insert(TABLE_NAME, null, insertValues);
      }
      
      @Override
      public void onCreate(SQLiteDatabase db) {
         db.execSQL("CREATE TABLE " + TABLE_NAME + " (id INTEGER PRIMARY KEY autoincrement, BeerName TEXT not null unique, Photo BLOB)");
         
         System.out.println("TESTETETETET");
         populateBeersNameTb(db,"Amigos", R.drawable.amigos);
         populateBeersNameTb(db,"Amstel",R.drawable.amstel);
         populateBeersNameTb(db,"Asahi Black",R.drawable.asahi_black);
         populateBeersNameTb(db,"Asahi",R.drawable.asahi);
         
      }

      @Override
      public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
         Log.w("Example", "Upgrading database, this will drop tables and recreate.");
         db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
         onCreate(db);
      }
   }
}