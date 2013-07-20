package com.drunkers_help;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;


public class MainActivity extends Activity {
   
	private static final int FindViewById = 0;
	/** Called when the activity is first created. */
	GridView gridview;
    @Override
    
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
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
		// Inflate the menu; this adds items to the action bar if it is present.

		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}