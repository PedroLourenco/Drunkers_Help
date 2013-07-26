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
   private static final String TABLE_BEERS_NAME = "BeersName";
   private static final String TABLE_BEER_COUNTER = "BeerCounter";
   private static final String TABLE_BEER_HISTORY = "BeerHistory";   
   public static final String BeerName = "BeerName";
   public static final String Photo = "Photo";
   public static final String Counter = "Counter";
   private static Context context;
   private SQLiteDatabase db;
  
   

   public DbHelper(Context context) {
      DbHelper.context = context;
      OpenHelper openHelper = new OpenHelper(DbHelper.context);
      this.db = openHelper.getWritableDatabase();     
   }

   
   public void deleteAll() {
      this.db.delete(TABLE_BEERS_NAME, null, null);
   }
   
   
   public void dropTableBeersName(){	   
	  db.execSQL("DROP TABLE IF EXISTS " + TABLE_BEERS_NAME);	   
   }
   
     
   public List<String> selectAllBeers() {
      List<String> list = new ArrayList<String>();
     Cursor cursor = this.db.query(TABLE_BEERS_NAME, new String[] { "id","beerName" },
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

   
   public int getBeerId(String name) {
	      int result = -1; 
	      Cursor cursor = db.rawQuery("select id from " + TABLE_BEERS_NAME + " where UPPER(beerName) = UPPER (?)", new String[] {name});	            
	     
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
	      
	      System.out.println("ID: " + id);
	      Cursor cursor = db.rawQuery("select BeerName from " + TABLE_BEERS_NAME + " where id = ?", new String[] {id});	            
	     
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
	      Cursor cursor = db.rawQuery("select Counter from " + TABLE_BEER_COUNTER + " where UPPER(beerName) = UPPER (?)", new String[] {beerName});	            
	     
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
	   Cursor cursor = db.rawQuery("select Counter from " + TABLE_BEER_COUNTER + " where UPPER(beerName) = UPPER (?)", new String[] {beerName});	
	     
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
   
   
   public void resetCounterTable() {
	      this.db.delete(TABLE_BEER_COUNTER, null, null);
	   }
   
   public void deleteCounterName(String beerName) {
	      db.delete(TABLE_BEER_COUNTER, "UPPER(beerName) = UPPER(?)", new String[] {beerName});
	   }
   
   
   public Cursor selectCounterBeers() {
	   System.out.println("selectCounterBeers: ");
	     Cursor cursor = this.db.rawQuery("select beerName ||' - '|| Counter as _id  from " + TABLE_BEER_COUNTER + " order by beerName ASC" , new String[] {});
	     System.out.println("selectCounterBeers:44 ");
	      return cursor;
	   }
   
   
   //insert beerName on table BeersName without photo
   public Integer incrementCounter(String id){
 	  ContentValues insertValues = new ContentValues();
 	 
 	  int counter;
 	  String beerName;
 	  beerName = getBeerName(id);
 	  counter = getBeerCounter(beerName);
 	  System.out.println("beerName: " + beerName);
 	  System.out.println("counter: " + counter);
 	  
 	  if(counter == -1){		  
 		 insertValues.put("beerName", beerName);
 		 insertValues.put(Counter, 1);
 		 
 		 db.insert(TABLE_BEER_COUNTER, null, insertValues);
 		  
 	  }else{ 
 		 insertValues.put(Counter, counter+1);
 		  db.update(TABLE_BEER_COUNTER, insertValues, "UPPER(beerName) = UPPER(?)",new String[] {beerName}); 	  
 	  }
 	  
 	 System.out.println("counter2: " + getBeerCounter(beerName));
 	  
 	  return getBeerCounter(beerName);
   }
   
 //insert beerName on table BeersName without photo
   public Integer decrementCounter(String id){
 	  ContentValues insertValues = new ContentValues();
 	 
 	  int counter;
 	  String beerName;
 	  beerName = getBeerName(id);
 	  counter = getBeerCounter(beerName);
 	  System.out.println("beerName: " + beerName);
 	  System.out.println("counter: " + counter);
 	  
 	  		  
 	  if(counter > 0 && counter != -1) { 
 		 System.out.println("counter2: ");
 		  insertValues.put(Counter, counter-1);
 		  db.update(TABLE_BEER_COUNTER, insertValues, "UPPER(beerName) = UPPER(?)",new String[] {beerName}); 	  
 	  }
 	  else if (counter == 0){
 		 deleteCounterName(beerName); 		  
 	  }
 	  
 	   	  
 	   	  
 	 System.out.println("counter2: " + getBeerCounter(beerName));
 	  
 	  return getBeerCounter(beerName);
   }
   
   
   private static class OpenHelper extends SQLiteOpenHelper {

      OpenHelper(Context context) {
         super(context, DATABASE_NAME, null, DATABASE_VERSION);
      }

                  
      //insert beerName on table BeersName without photo
      public long populateBeersNameTb(SQLiteDatabase db, String name){
    	  ContentValues insertValues = new ContentValues();
    	  
    	  insertValues.put(BeerName, name);
    	     	 
    	  return db.insert(TABLE_BEERS_NAME, null, insertValues);
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
         db.execSQL("CREATE TABLE " + TABLE_BEERS_NAME + "(id INTEGER PRIMARY KEY autoincrement, BeerName TEXT not null unique, Photo BLOB)");
         db.execSQL("CREATE TABLE " + TABLE_BEER_COUNTER + " (beerName TEXT PRIMARY KEY not null unique, Counter INTEGER not null)");
         db.execSQL("CREATE TABLE " + TABLE_BEER_HISTORY + " (BeerName TEXT PRIMARY KEY not null unique, Date DATE, City TEXT)");
        
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
         db.execSQL("DROP TABLE IF EXISTS " + TABLE_BEERS_NAME);
         onCreate(db);
      }
   }
}