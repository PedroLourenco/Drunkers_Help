package com.drunkers_help;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * @author Pedro Lourenco
 * 
 */

public class MainActivity extends Activity implements OnQueryTextListener {
	TextView mSearchText;

	private Context context;
	private DbHelper dbHelper;
	private long lastPressedTime;
	private static final int PERIOD = 2000;

	String[] imageUrls = globalconstant.IMAGES;
	protected AbsListView listView;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	DisplayImageOptions options;

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if(globalconstant.LOG) Log.d(globalconstant.TAG, "APP started!");
		
		imageLoader.init(ImageLoaderConfiguration.createDefault(this));
		this.dbHelper = new DbHelper(this);
		context = MainActivity.this;

		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.ic_stub)
				.showImageForEmptyUri(R.drawable.ic_empty)
				.showImageOnFail(R.drawable.ic_error).cacheInMemory(true)
				.cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565).build();

		listView = (GridView) findViewById(R.id.gridview);
		((GridView) listView).setAdapter(new ImageAdapter());
				
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {

				// Sending image id to another activity
				Intent i = new Intent(getApplicationContext(),
						CounterActivity.class);
				// passing array index
				System.out.println("position: " + (position - 1));
				i.putExtra("id", position);
				startActivity(i);
			}
		});

		Button bt_dayCounter = (Button) findViewById(R.id.btnCounter);
		bt_dayCounter.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent i = new Intent(getApplicationContext(),
						DailyCounter.class);
				startActivity(i);
			}
		});

		Button bt_resetCounter = (Button) findViewById(R.id.btnResetCounter);
		bt_resetCounter.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				dbHelper.resetCounterTable();
				Toast.makeText(getApplicationContext(), "Counter reseted!",
						Toast.LENGTH_SHORT).show();
			}
		});
		

		if (globalconstant.count == 0) {
			globalconstant.count++;

			CheckGPSStatus gps = new CheckGPSStatus(this);

			if (!gps.networkStatus() && !gps.displayGpsStatus() ) {
				
				showSettingsAlert(context);
			}
			else{
				startService(new Intent(context, GPSTracker.class)); 			
			}

		}
	}

	/**
	 * Show alert and get user to Location settings
	 * 
	 * @param context
	 * @return
	 */

	public void showSettingsAlert(final Context mContext) {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

		// Setting Dialog Message
		alertDialog
				.setMessage(R.string.alert_msg);

		// On pressing Settings button
		alertDialog.setPositiveButton(R.string.alert_location,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(
								Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						mContext.startActivity(intent);
						startService(new Intent(context, GPSTracker.class));
					}
				});

		// on pressing cancel button
		alertDialog.setNegativeButton(R.string.alert_cancel,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
		// Showing Alert Message
		alertDialog.show();
	}

	/**
	 * Create option to search in actionbar
	 * 
	 * @param Menu
	 * @return boolean
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		SearchView searchView = (SearchView) menu.findItem(R.id.menu_search)
				.getActionView();
		searchView.setQueryHint("Beer Name");
		searchView.setOnQueryTextListener(this);
		return true;
	}

	/**
	 * Implement onKeyDown to stop service, clean day counter and exit app
	 * 
	 * @param Menu
	 * @return boolean
	 */
	// Exit APP when click back key twice
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {

			switch (event.getAction()) {
			case KeyEvent.ACTION_DOWN:
				if (event.getDownTime() - lastPressedTime < PERIOD) {
					dbHelper.resetCounterTable();
					stopService(new Intent(context, GPSTracker.class));
					globalconstant.cityName = null;
					globalconstant.count = 0;
					finish();

				} else {

					Toast.makeText(getApplicationContext(),
							"Press again to exit.", Toast.LENGTH_SHORT).show();
					lastPressedTime = event.getEventTime();
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return super.onPrepareOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.menu_History:
			Intent i = new Intent(getApplicationContext(),
					HistoryActivity.class);
			startActivity(i);
			return true;
		case R.id.menu_addBeer:
			Intent i_addBeer = new Intent(getApplicationContext(),
					AddNewBeer.class);
			startActivity(i_addBeer);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	// The following callbacks are called for the
	// SearchView.OnQueryChangeListener
	public boolean onQueryTextChange(String newText) {

		return true;
	}

	public boolean onQueryTextSubmit(String query) {

		int result = dbHelper.getBeerId(query);

		if (result != -1) {
			Intent is = new Intent(getApplicationContext(),
					CounterActivity.class);
			is.putExtra("id", result - 1);
			is.putExtra("beerName", query);
			startActivity(is);
		} else {
			Toast.makeText(this, "Are you drunk? ", Toast.LENGTH_SHORT).show();
		}

		return true;
	}

	public class ImageAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return imageUrls.length;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final ImageView imageView;
			if (convertView == null) {
				imageView = (ImageView) getLayoutInflater().inflate(
						R.layout.item_grid_image, parent, false);
			} else {
				imageView = (ImageView) convertView;
			}

			imageLoader.displayImage(imageUrls[position], imageView, options);

			return imageView;
		}
	}

}