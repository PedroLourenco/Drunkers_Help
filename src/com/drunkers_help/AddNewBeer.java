/**
 * 
 */
package com.drunkers_help;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

/**
 * @author mejaime
 *
 */
public class AddNewBeer extends Activity {

	private DbHelper db;
	
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_addbeer);
	        
	        
	        this.db = new DbHelper(this);
	        
	 }
	 
	 	 
 
		 public boolean onOptionsItemSelected(MenuItem item) {
		        // Handle item selection
		        switch (item.getItemId()) {
		            case R.id.menu_addBeer:
		            	
		            	EditText new_beer = (EditText) findViewById(R.id.newBeer);
		            	
		            	if (new_beer.getText().toString().isEmpty()) {		            		
		            		Toast.makeText(getApplicationContext(), "Beer name is empty!",Toast.LENGTH_SHORT).show();
		            		
		            		System.out.println("Beer name is empty");
		            	}
		            	else{
		            		db.addNewBeer(new_beer.getText().toString());
		        		
		            		Intent i_main = new Intent(getApplicationContext(), MainActivity.class); 
		               	 	startActivity(i_main); 
		            	}
		        
		        
		                return true;
		           
		            default:
		                return super.onOptionsItemSelected(item);
		        }
		    }
	 

	 
	 
	 @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	    	 MenuInflater inflater = getMenuInflater();
	         inflater.inflate(R.menu.actionbar_newbeer, menu);
	         
	         return true;
	    }
	 
	        
}
