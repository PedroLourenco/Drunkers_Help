package com.drunkers_helper.location;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.drunkers_helper.util.Globalconstant;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MyLocation {

	private static final long ONE_MIN = 1000 * 60;
	private static final long TWO_MIN = ONE_MIN * 2;
	private static final long FIVE_MIN = ONE_MIN * 5;
	private static final long MEASURE_TIME = 1000 * 30;
	private static final long POLLING_FREQ = 1000 * 10;
	private static final float MIN_ACCURACY = 25.0f;
	private static final float MIN_LAST_READ_ACCURACY = 500.0f;
	private static final float MIN_DISTANCE = 10.0f;
	

	// Views for display location information
	private TextView mAccuracyView;
	private TextView mTimeView;
	private TextView mLatView;
	private TextView mLngView;

	

	// Current best location estimate
	private Location mBestReading;

	// Reference to the LocationManager and LocationListener
	private LocationManager mLocationManager;
	private LocationListener mLocationListener;

	///private final String TAG = "LocationGetLocationActivity";

	

	
	public void startLocation(Context context) {

		Log.i(Globalconstant.TAG, "MyLocationd!");
		// Acquire reference to the LocationManager
		if (null != (mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE))){
			Log.i(Globalconstant.TAG, "MyLocationd!--------------------------------");

		// Get best last location measurement
		mBestReading = bestLastKnownLocation(MIN_LAST_READ_ACCURACY, FIVE_MIN);
		Log.i(Globalconstant.TAG, "MyLocationd!--------------------------------");
		// Display last reading information
		if (null != mBestReading) {
			Log.i(Globalconstant.TAG, "MyLocationd!--------------------------------1");
			getLocation(mBestReading);
			Log.i(Globalconstant.TAG, "MyLocationd!--------------------------------2");

		} else {

			
			Log.i(Globalconstant.TAG, "No Initial Reading Available");

		}

		mLocationListener = new LocationListener() {

			// Called back when location changes

			public void onLocationChanged(Location location) {

				
				// Determine whether new location is better than current best
				// estimate

				if (null == mBestReading
						|| location.getAccuracy() < mBestReading.getAccuracy()) {

					// Update best estimate
					mBestReading = location;

					// Update display
					getLocation(location);

					if (mBestReading.getAccuracy() < MIN_ACCURACY)
						mLocationManager.removeUpdates(mLocationListener);

				}
			}

			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// NA
			}

			public void onProviderEnabled(String provider) {
				// NA
			}

			public void onProviderDisabled(String provider) {
				// NA
			}
		};
		}
	}

	
	public void onResume() {
		

		// Determine whether initial reading is
		// "good enough"

		if (mBestReading.getAccuracy() > MIN_LAST_READ_ACCURACY
				|| mBestReading.getTime() < System.currentTimeMillis()
						- TWO_MIN) {

			// Register for network location updates
			mLocationManager.requestLocationUpdates(
					LocationManager.NETWORK_PROVIDER, POLLING_FREQ, MIN_DISTANCE,
					mLocationListener);

			// Register for GPS location updates
			mLocationManager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER, POLLING_FREQ, MIN_DISTANCE,
					mLocationListener);

			// Schedule a runnable to unregister location listeners

			Executors.newScheduledThreadPool(1).schedule(new Runnable() {

				@Override
				public void run() {

					Log.i(Globalconstant.TAG, "location updates cancelled");

					mLocationManager.removeUpdates(mLocationListener);

				}
			}, MEASURE_TIME, TimeUnit.MILLISECONDS);
		}
	}

	// Unregister location listeners

	
	public void onPause() {
		
		mLocationManager.removeUpdates(mLocationListener);

	}

	// Get the last known location from all providers
	// return best reading is as accurate as minAccuracy and
	// was taken no longer then minTime milliseconds ago

	private Location bestLastKnownLocation(float minAccuracy, long minTime) {

		Location bestResult = null;
		float bestAccuracy = Float.MAX_VALUE;
		long bestTime = Long.MIN_VALUE;

		Log.i(Globalconstant.TAG, "bestLastKnownLocation!--------------------------------");
		
		List<String> matchingProviders = mLocationManager.getAllProviders();

		for (String provider : matchingProviders) {

			Location location = mLocationManager.getLastKnownLocation(provider);

			if (location != null) {
				Log.i(Globalconstant.TAG, "bestLastKnownLocation!--------------------------------");
				float accuracy = location.getAccuracy();
				long time = location.getTime();

				if (accuracy < bestAccuracy) {

					bestResult = location;
					bestAccuracy = accuracy;
					bestTime = time;

				}
			}
		}

		// Return best reading or null
		if (bestAccuracy > minAccuracy || bestTime < minTime) {
			return null;
		} else {
			return bestResult;
		}
	}

	// Update display
	private void getLocation(Location location) {

		Log.i(Globalconstant.TAG, "getLocation!--------------------------------1");
		Globalconstant.latitude = location.getLatitude();
		Globalconstant.longitude = location.getLongitude();
		
		Log.i(Globalconstant.TAG, "getLocation!--------------------------------2");

	}

	
	

}