package com.drunkers_helper.activity;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
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

import com.drunkers_help.R;
import com.drunkers_helper.datasource.BeersDataSource;
import com.drunkers_helper.location.MyLocation;
import com.drunkers_helper.location.MyLocation.LocationResult;
import com.drunkers_helper.util.CheckGPSStatus;
import com.drunkers_helper.util.globalconstant;
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
	private BeersDataSource beer_datasource;;

	private Location mlocation;
	private long lastPressedTime;
	private static final int PERIOD = 2000;
	private static final AndroidHttpClient ANDROID_HTTP_CLIENT = AndroidHttpClient
			.newInstance(MyLocation.class.getName());
	private boolean running = false;

	String[] imageUrls = globalconstant.IMAGES;
	protected AbsListView listView;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	DisplayImageOptions options;
	MyLocation myLocation = new MyLocation();
	CheckGPSStatus gps = new CheckGPSStatus(this);

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (globalconstant.LOG)
			Log.d(globalconstant.TAG, "APP started!");

		imageLoader.init(ImageLoaderConfiguration.createDefault(this));
		beer_datasource = new BeersDataSource(this);
		beer_datasource.open();
		context = MainActivity.this;

		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.ic_stub)
				.showImageForEmptyUri(R.drawable.ic_empty)
				.showImageOnFail(R.drawable.ic_error).cacheInMemory(true)
				.cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565).build();

		findCurrentLocation();

		listView = (GridView) findViewById(R.id.gridview);
		((GridView) listView).setAdapter(new ImageAdapter());

		// check providers
		if (globalconstant.count == 0) {
			globalconstant.count = 1;

			if (!gps.locationsStatus() && !gps.connectivityStatus(context)) {

				Intent action = new Intent(
						Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				showSettingsAlert(context,
						R.string.alert_msg_LocationSettings_WIFI,
						R.string.alert_location, action, 0, null, false);

			} else if (gps.locationsStatus()
					&& !gps.connectivityStatus(context)) {
				Intent okAction = new Intent(Settings.ACTION_WIFI_SETTINGS);
				Intent neutralAction = new Intent(
						Settings.ACTION_WIRELESS_SETTINGS);
				showSettingsAlert(context, R.string.alert_msg_NetworkPresent,
						R.string.alert_wifi_settings, okAction,
						R.string.alert_3G_settings, neutralAction, true);

			} else if (!gps.locationsStatus()
					&& gps.connectivityStatus(context)) {
				Intent action = new Intent(
						Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				showSettingsAlert(context,
						R.string.alert_msg_LocationSettings_WIFI,
						R.string.alert_location, action, 0, null, false);

			}
		}

		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				fetchCityName(mlocation);
				// Sending image id to another activity
				Intent i = new Intent(getApplicationContext(),
						CounterActivity.class);
				// passing array index
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

				beer_datasource.resetCounterTable();
				Toast.makeText(getApplicationContext(), "Counter reseted!",
						Toast.LENGTH_SHORT).show();
			}
		});

	}

	private void findCurrentLocation() {
		myLocation.getLocation(this, locationResult);

	}

	public LocationResult locationResult = new LocationResult() {

		@Override
		public void gotLocation(Location location) {
			// TODO Auto-generated method stub
			if (location != null) {
				mlocation = location;
				if (globalconstant.LOG)
					Log.v(globalconstant.TAG, location.getLatitude() + ","
							+ location.getLongitude());

				try {
					Toast.makeText(
							getApplicationContext(),
							location.getLatitude() + ","
									+ location.getLongitude(),
							Toast.LENGTH_SHORT).show();
				} catch (Exception e) {

				}

			}
			if (gps.connectivityStatus(context)) {

			} else {
				if (globalconstant.LOG)
					Log.v(globalconstant.TAG,
							getResources().getString(R.string.City_msg1));

				try {
					if (globalconstant.count == 0)
						Toast.makeText(getApplicationContext(),
								getResources().getString(R.string.City_msg1),
								Toast.LENGTH_SHORT).show();
				} catch (Exception e) {

				}

			}

		}
	};

	public void showSettingsAlert(final Context mContext, int message,
			int okSeeting, final Intent okAction, int neutralSettings,
			final Intent neutralAction, boolean neutralButton) {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

		// Setting Dialog Message
		alertDialog.setMessage(message);

		// On pressing Settings button
		alertDialog.setPositiveButton(okSeeting,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(okAction);
						mContext.startActivity(intent);

					}
				});

		if (neutralButton) {
			// On pressing Settings button
			alertDialog.setNeutralButton(neutralSettings,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							Intent intent = new Intent(neutralAction);
							mContext.startActivity(intent);

						}
					});
		}

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
					beer_datasource.resetCounterTable();
					stopService(new Intent(context, MyLocation.class));
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

		int result = beer_datasource.getBeerId(query);

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

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
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

	/** Get city name */
	public void fetchCityName(final Location location) {
		if (running)
			return;

		new AsyncTask<Void, Void, String>() {
			protected void onPreExecute() {
				running = true;
			};

			@Override
			protected String doInBackground(Void... params) {
				try {
					String googleMapUrl = "http://maps.googleapis.com/maps/api/geocode/json?latlng="
							+ location.getLatitude()
							+ ","
							+ location.getLongitude()
							+ "&sensor=false&language=fr";

					JSONObject googleMapResponse = new JSONObject(
							ANDROID_HTTP_CLIENT.execute(new HttpGet(
									googleMapUrl), new BasicResponseHandler()));

					// many nested loops.. not great -> use expression instead
					// loop among all results
					JSONArray results = (JSONArray) googleMapResponse
							.get("results");
					for (int i = 0; i < results.length(); i++) {
						// loop among all addresses within this result
						JSONObject result = results.getJSONObject(i);
						if (result.has("address_components")) {
							JSONArray addressComponents = result
									.getJSONArray("address_components");
							// loop among all address component to find a
							// 'locality' or 'sublocality'
							for (int j = 0; j < addressComponents.length(); j++) {
								JSONObject addressComponent = addressComponents
										.getJSONObject(j);
								if (result.has("types")) {
									JSONArray types = addressComponent
											.getJSONArray("types");

									// search for locality and sublocality
									String cityName = null;

									for (int k = 0; k < types.length(); k++) {
										if ("locality".equals(types
												.getString(k))
												&& cityName == null) {
											if (addressComponent
													.has("long_name")) {
												cityName = addressComponent
														.getString("long_name");
											} else if (addressComponent
													.has("short_name")) {
												cityName = addressComponent
														.getString("short_name");
											}
										}
										if ("sublocality".equals(types
												.getString(k))) {
											if (addressComponent
													.has("long_name")) {
												cityName = addressComponent
														.getString("long_name");
											} else if (addressComponent
													.has("short_name")) {
												cityName = addressComponent
														.getString("short_name");
											}
										}
									}
									if (cityName != null) {
										return cityName;
									}
								}
							}
						}
					}
				} catch (Exception ignored) {
					ignored.printStackTrace();
				}
				return null;
			}

			protected void onPostExecute(String cityName) {
				running = false;
				if (cityName != null) {
					globalconstant.cityName = cityName;

					if (!cityName.equals(globalconstant.cityName)) {

						Toast.makeText(context, globalconstant.cityName,
								Toast.LENGTH_SHORT).show();
						// Do something with cityName
						if (globalconstant.LOG)
							Log.d(globalconstant.TAG, globalconstant.cityName);
					}
				} else {

					Toast.makeText(context, "Cannot get city name!",
							Toast.LENGTH_SHORT).show();
				}

			};
		}.execute();
	}

}