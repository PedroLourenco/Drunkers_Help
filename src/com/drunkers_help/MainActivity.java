package com.drunkers_help;

import java.util.List;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;


public class MainActivity extends Activity  implements OnQueryTextListener {
    TextView mSearchText;
   
	
	private DbHelper dh;
	private long lastPressedTime;
	private static final int PERIOD = 2000;
	private static final int FindViewById = 0;
	/** Called when the activity is first created. */
	GridView gridview;
    @Override
    
    
    public void onCreate(Bundle savedInstanceState) {
    	// mSearchText = new TextView(this);
         //mSearchText.setPadding(10, 10, 10, 10);
         //mSearchText.setText("Action Bar Usage example from CoderzHeaven");
         //setContentView(mSearchText);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        
        this.dh = new DbHelper(this);
        List<String> names = this.dh.selectAll();
        StringBuilder sb = new StringBuilder();
        sb.append("Names in database:\n");
        for (String beerName : names) {
           sb.append(beerName + "\n");
        }

        Log.d("EXAMPLE", "names - " + sb.toString());

               
        
        
         gridview =(GridView) findViewById(R.id.gridview);  
         gridview.setAdapter(new ImageAdapter(this)); 
           
        //When You Click Image It Show Full Size Image in New Activity
         gridview.setOnItemClickListener(new OnItemClickListener() { 
         public void onItemClick(AdapterView<?> parent, View v, int position, long id) { 
   
        	 // Sending image id to another activity 
        	 Intent i = new Intent(getApplicationContext(), CounterActivity.class); 
        	 // passing array index 
        	 i.putExtra("id", position); 
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
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Press again to exit.",
                            Toast.LENGTH_SHORT).show();
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
 
    @Override
    public boolean    onOptionsItemSelected       (MenuItem item) {
        Toast.makeText(this, "Selected Item: " + item.getTitle(), Toast.LENGTH_SHORT).show();
        return true;
    }
    
    // The following callbacks are called for the SearchView.OnQueryChangeListener
    public boolean onQueryTextChange(String newText) {
        
        return true;
    }
	 public boolean onQueryTextSubmit (String query) {
	        //Toast.makeText(this, "Searching for: " + query + "...", Toast.LENGTH_SHORT).show();	        	       
	        int result = dh.selectAll1(query);	        
	        
	        if(result != -1){
	        	Intent is = new Intent(getApplicationContext(), CounterActivity.class);	      	 	
	          	is.putExtra("id", result-1); 
	      	 	startActivity(is);		        
	        }
	        else{
	        	 Toast.makeText(this, "Are you drunk? ", Toast.LENGTH_SHORT).show();	        	
	        }    	
	       
	        
	        return true;
	 }
	 
}