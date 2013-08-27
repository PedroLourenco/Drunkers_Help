package com.drunkers_help;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.util.Log;

/**
 * @author Pedro Lourenco
 * 
 */

public class CheckGPSStatus extends Service {
	private final Context mContext;

	public CheckGPSStatus(Context context) {

		this.mContext = context;
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	/*----Method to Check locations settings status ----- */
	public Boolean locationsStatus() {
		LocationManager locationManager = (LocationManager) mContext
				.getSystemService(LOCATION_SERVICE);

		boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

		if (!isGPSEnabled && !isNetworkEnabled) {
			return false;

		} else {

			return true;
		}

	}

	/*----Method to Check Data connection ----- */
	public Boolean connectivityStatus() {

		ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

		boolean is3g = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
		boolean isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();

				
		if (!is3g && !isWifi) {
			return false;

		} else {
			return true;

		}

	}

}
