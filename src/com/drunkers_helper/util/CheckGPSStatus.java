package com.drunkers_helper.util;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiManager;
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
	public boolean locationsStatus() {
		LocationManager locationManager = (LocationManager) mContext
				.getSystemService(LOCATION_SERVICE);

		boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

		if (!isNetworkEnabled && !isGPSEnabled) {
			
			if (globalconstant.LOG)
				Log.v(globalconstant.TAG, "locationStatus: FALSE");
			return false;

		} else {
			if (globalconstant.LOG)
				Log.v(globalconstant.TAG, "locationStatus: TRUE");
			return true;
		}

	}

	/*----Method to Check Data connection ----- */
	public boolean connectivityStatus(Context context) {

		boolean isNetworkAvailable = false;
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		try {

			if (cm != null) {
				NetworkInfo netInfo = cm.getActiveNetworkInfo();
				if (netInfo != null) {
					isNetworkAvailable = netInfo.isConnectedOrConnecting();
				}
			}
		} catch (Exception ex) {
			if (globalconstant.LOG)
				Log.e("Network Avail Error", ex.getMessage());
		}
		// check for wifi also
		if (!isNetworkAvailable) {
			WifiManager connec = (WifiManager) context
					.getSystemService(Context.WIFI_SERVICE);
			State wifi = cm.getNetworkInfo(1).getState();
			if (connec.isWifiEnabled()
					&& wifi.toString().equalsIgnoreCase("CONNECTED")) {
				isNetworkAvailable = true;
			} else {

				isNetworkAvailable = false;
			}

		}
		return isNetworkAvailable;
	}

}
