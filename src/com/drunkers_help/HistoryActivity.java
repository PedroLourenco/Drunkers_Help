/**
 * 
 */
package com.drunkers_help;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.widget.ListView;

/**
 * @author PedroLourenco
 * 
 */
public class HistoryActivity extends Activity {

	private DbHelper dh;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history);

		this.dh = new DbHelper(this);
		
		Cursor listcounter = dh.selectAllHistory();
		
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,	android.R.layout.simple_list_item_2, listcounter, new String[] { "_id", "City" }, new int[] { android.R.id.text1,android.R.id.text2 }, 0);
		ListView list = (ListView) findViewById(R.id.histListView);
		list.setAdapter(adapter);

	}

	

}
