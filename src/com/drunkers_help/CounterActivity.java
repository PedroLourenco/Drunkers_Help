/**
 * 
 */
package com.drunkers_help;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

 
/**
 * @author mejaime
 *
 */

public class CounterActivity extends Activity {
	
	Integer counterValue  = 0;
	TextView counter;
	private DbHelper dh;
	Integer position;
	String beerName;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);
 
        this.dh = new DbHelper(this);
        
        // get intent data
        Intent i = getIntent();
 
        // Selected image id
        Integer aux_position = i.getExtras().getInt("id");
        ImageAdapter imageAdapter = new ImageAdapter(this); 
        ImageView beerImage = (ImageView) findViewById(R.id.mImgView1);
        beerImage.setImageResource(imageAdapter.mThumbIds[aux_position]);
        Button bt_minus = (Button) findViewById(R.id.btnMinus);
    
        TextView title = (TextView) findViewById(R.id.title);
        counter = (TextView) findViewById(R.id.counter);
        position = aux_position +1;
    	
    	beerName = dh.getBeerName(position.toString()); 
    	
    	title.setText(beerName);
        title.setTextSize(30);
        title.setHorizontallyScrolling(false);
        
        counterValue = dh.getBeerNameCounter(beerName);
        counter.setText(counterValue.toString());       
        counter.setTextSize(50);
        counter.setHorizontallyScrolling(false);
        
    
        
	
	beerImage.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View v) {	       
	    	
	    	counter.setText(dh.incrementCounter(position.toString()).toString());	    	
	    }
	});
	

	bt_minus.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View v) {     
	    	
	    	counter.setText(dh.decrementCounter(position.toString()).toString());		    		
	    }    	    	
	  
	});
	
	
	
}
	
	 
	
	
	
}