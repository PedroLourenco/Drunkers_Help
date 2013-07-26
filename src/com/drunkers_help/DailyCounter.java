/**
 * 
 */
package com.drunkers_help;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author PedroLourenco
 *
 */
public class DailyCounter extends Activity{

	private DbHelper dh;
	 public static ArrayList ArrayofName = new ArrayList(); 
	 private GridView gridView;

	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_dailycounter);
	 

	        this.dh = new DbHelper(this);
	        //List<String> counters = dh.selectCounterBeers();
 	    	//StringBuilder sb = new StringBuilder();
 	        //sb.append("Names in database:\n");
 	        //for (String counterss : counters) {
 	        //   sb.append(counterss + "\n");
 	        //}

 	        //Log.d("EXAMPLE", "names - " + sb.toString());
	 
	 Cursor listcounter = dh.selectCounterBeers();
	 System.out.println("selectCounterBeers:22 ");
	 
	 
	 
 	       SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.test_list_item, listcounter, 
 	              new String[] {"_id"}, new int[] {android.R.id.text1}, 0);
 	      System.out.println("selectCounterBeers:23 ");
 	  ListView list = (ListView) findViewById(R.id.dailyListView);
 	  
 	 System.out.println("selectCounterBeers:24 ");
 	  list.setAdapter(adapter);
	
	 
	 
	 }

}
