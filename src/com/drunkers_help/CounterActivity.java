/**
 * 
 */
package com.drunkers_help;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

 
/**
 * @author mejaime
 *
 */

public class CounterActivity extends Activity {
 
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);
 
        // get intent data
        Intent i = getIntent();
 
        // Selected image id
        int position = i.getExtras().getInt("id");
        ImageAdapter imageAdapter = new ImageAdapter(this); 
        ImageView imageView = (ImageView) findViewById(R.id.mImgView1);
        imageView.setImageResource(imageAdapter.mThumbIds[position]);
    }
 
    
}