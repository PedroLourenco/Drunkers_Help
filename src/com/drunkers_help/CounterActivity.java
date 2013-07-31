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
	ImageView beerImage;
	ImageAdapter imageAdapter;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);
 
        this.dh = new DbHelper(this);
        
        // get intent data
        Intent i = getIntent();
 
        // Selected image id
        
        Integer aux_position = i.getExtras().getInt("id");
    	String aux_beerName = i.getExtras().getString("beerName");
        try{
        	
        	imageAdapter = new ImageAdapter(this); 
        	beerImage = (ImageView) findViewById(R.id.mImgView1);
            beerImage.setImageResource(imageAdapter.mThumbIds[aux_position]);            
            position = aux_position +1;        	
        	beerName = dh.getBeerName(position.toString()); 
        
        }catch(ArrayIndexOutOfBoundsException e){        	
        	beerImage.setImageResource(R.drawable.defaultb);
        	beerName = aux_beerName;
        }
        
        	
        TextView title = (TextView) findViewById(R.id.title);
        counter = (TextView) findViewById(R.id.counter);
       
    	
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
	    	
	    	counter.setText(dh.incrementCounter(beerName).toString());	    	
	    }
	});
	
	Button bt_minus = (Button) findViewById(R.id.btnMinus);
	bt_minus.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View v) {     
	    	
	    	counter.setText(dh.decrementCounter(beerName).toString());		    		
	    }    	    	
	  
	});
	
	
	
}
	
	 
	
	
	
}