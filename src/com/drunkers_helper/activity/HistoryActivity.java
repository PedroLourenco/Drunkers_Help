/**
 * 
 */
package com.drunkers_helper.activity;

import com.drunkers_help.R;
import com.drunkers_helper.datasource.BeersDataSource;

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

	private BeersDataSource beer_datasource; 

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history);

		beer_datasource = new BeersDataSource(this);
		beer_datasource.open();

		Cursor listcounter = beer_datasource.selectAllHistory();

		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_list_item_2, listcounter, new String[] {
						"_id", "Location" }, new int[] { android.R.id.text1,
						android.R.id.text2 }, 0);
		ListView list = (ListView) findViewById(R.id.histListView);
		list.setAdapter(adapter);
	}
}