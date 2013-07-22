package com.drunkers_help;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;


public class MainActivity extends Activity {
   
	private DbHelper dh;
	private long lastPressedTime;
	private static final int PERIOD = 2000;
	private static final int FindViewById = 0;
	/** Called when the activity is first created. */
	GridView gridview;
    @Override
    
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        
        this.dh = new DbHelper(this);
        //this.dh. insertBeerName("Amigos");
        //this.dh.insertBeerName("Amstel");
        //Sthis.dh.insertBeerName("Asahi Black");
        
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
		// Inflate the menu; this adds items to the action bar if it is present.

		getMenuInflater().inflate(R.menu.main, menu);
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
}