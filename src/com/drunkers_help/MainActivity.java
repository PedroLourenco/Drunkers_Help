package com.drunkers_help;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity  implements OnQueryTextListener {
    TextView mSearchText;
   
	
	private DbHelper dbHelper;
	private long lastPressedTime;
	private static final int PERIOD = 2000;
	/** Called when the activity is first created. */
	GridView gridview;
   
    
     @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);           
        
        this.dbHelper = new DbHelper(this);
                
        gridview =(GridView) findViewById(R.id.gridview);  
        gridview.setAdapter(new ImageAdapter(this)); 
        gridview.setOnItemClickListener(new OnItemClickListener() { 
         public void onItemClick(AdapterView<?> parent, View v, int position, long id) { 
   
        	 // Sending image id to another activity 
        	 Intent i = new Intent(getApplicationContext(), CounterActivity.class); 
        	 //passing array index 
        	 i.putExtra("id", position);
        	 startActivity(i); 
           } 
         }); 
    
     
     
     
         Button bt_dayCounter = (Button) findViewById(R.id.btnCounter); 
         bt_dayCounter.setOnClickListener(new OnClickListener() {
     	    @Override
     	    public void onClick(View v) {	       
     	    	
     	    	Intent i = new Intent(getApplicationContext(), DailyCounter.class); 
     	    	startActivity(i);
     	    	
     	    }
     	});
     
     
        Button bt_resetCounter = (Button) findViewById(R.id.btnResetCounter); 
        bt_resetCounter.setOnClickListener(new OnClickListener() {
     	    @Override
     	    public void onClick(View v) {	       
     	    	
     	    	dbHelper.resetCounterTable();
     	    	Toast.makeText(getApplicationContext(), "Counter reseted!",Toast.LENGTH_SHORT).show();
     	    	
     	    }
     	});
        
        Button bt_location = (Button) findViewById(R.id.btnLocation); 
        bt_location.setOnClickListener(new OnClickListener() {
     	    @Override
     	    public void onClick(View v) {	       
     	    	
     	    	Intent i = new Intent(getApplicationContext(), GetCurrentLocation.class); 
     	    	startActivity(i);
     	    	
     	    }
     	});
     
     }
        
                
     
     
   
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	 MenuInflater inflater = getMenuInflater();
         inflater.inflate(R.menu.main, menu);
         SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
         searchView.setOnQueryTextListener(this);
         return true;
    }
 
        
    
    //Exit APP when click back key twice
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            switch (event.getAction()) {
            case KeyEvent.ACTION_DOWN:
                if (event.getDownTime() - lastPressedTime < PERIOD) {
                	dbHelper.resetCounterTable();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Press again to exit.",Toast.LENGTH_SHORT).show();
                    lastPressedTime = event.getEventTime();
                }
                return true;
            }
        }
        return false;
    }

   
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }
 
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_History:
            	Intent i = new Intent(getApplicationContext(), HistoryActivity.class); 
     	    	startActivity(i);
                return true;
            case R.id.menu_addBeer:
            	Intent i_addBeer = new Intent(getApplicationContext(), AddNewBeer.class); 
     	    	startActivity(i_addBeer);
                return true;          
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    // The following callbacks are called for the SearchView.OnQueryChangeListener
    public boolean onQueryTextChange(String newText) {
        
        return true;
    }
	 public boolean onQueryTextSubmit (String query) {
	        //Toast.makeText(this, "Searching for: " + query + "...", Toast.LENGTH_SHORT).show();	        	       
	        int result = dbHelper.getBeerId(query);	        
	        
	        if(result != -1){
	        	Intent is = new Intent(getApplicationContext(), CounterActivity.class);	      	 	
	          	is.putExtra("id", result-1);
	          	is.putExtra("beerName", query);
	      	 	startActivity(is);		        
	        }
	        else{
	        	 Toast.makeText(this, "Are you drunk? ", Toast.LENGTH_SHORT).show();	        	
	        } 
	        
	        return true;
	 }
	 
	 
	 
}