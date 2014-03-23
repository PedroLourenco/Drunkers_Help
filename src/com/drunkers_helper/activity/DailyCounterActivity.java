/**
 * 
 */
package com.drunkers_helper.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.ShareActionProvider;
import com.drunkers_help.R;
import com.drunkers_helper.datasource.BeersDataSource;

/**
 * @author Pedro Lourenco
 * 
 */

public class DailyCounterActivity extends Activity {

	private BeersDataSource mBeerDatasource;
	private ShareActionProvider myShareActionProvider;
	

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dailycounter);

		mBeerDatasource = new BeersDataSource(this);
		mBeerDatasource.open();
		Cursor listcounter = mBeerDatasource.selectCounterBeers();		
				
		ListView list = (ListView) findViewById(R.id.dailyListView);
				
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				R.layout.list_view, listcounter,
				new String[] { "_id" }, new int[] { android.R.id.text1 }, 0);
		
		list.setAdapter(adapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.actionbar_dalycounter, menu);
		MenuItem item = menu.findItem(R.id.menu_item_share);
		myShareActionProvider = (ShareActionProvider) item.getActionProvider();
		myShareActionProvider.setShareHistoryFileName(ShareActionProvider.DEFAULT_SHARE_HISTORY_FILE_NAME);
		myShareActionProvider.setShareIntent(createShareIntent());
		return true;
	}

	private Intent createShareIntent() {

		// get intent data
		Intent i = getIntent();
		String msg = i.getExtras().getString("message");
		Intent shareIntent = new Intent(Intent.ACTION_SEND);
		shareIntent.setType("text/plain");
		shareIntent.putExtra(Intent.EXTRA_TEXT,	getResources().getString(R.string.share_msg1) + msg	+ getResources().getString(R.string.share_msg2));
		return shareIntent;
	}		
}