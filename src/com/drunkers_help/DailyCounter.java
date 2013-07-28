/**
 * 
 */
package com.drunkers_help;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.ShareActionProvider;

/**
 * @author PedroLourenco
 * 
 */
public class DailyCounter extends Activity {

	private DbHelper dh;
	private ShareActionProvider myShareActionProvider;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dailycounter);

		this.dh = new DbHelper(this);
		Cursor listcounter = dh.selectCounterBeers();
		
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				android.R.layout.test_list_item, listcounter,
				new String[] { "_id" }, new int[] { android.R.id.text1 }, 0);
		ListView list = (ListView) findViewById(R.id.dailyListView);
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
		Intent shareIntent = new Intent(Intent.ACTION_SEND);
		shareIntent.setType("text/plain");
		shareIntent.putExtra(Intent.EXTRA_TEXT, dh.selectAllBeerCounter());
		return shareIntent;
	}

}
