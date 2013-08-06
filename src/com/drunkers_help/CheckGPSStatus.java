package com.drunkers_help;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.IBinder;

/**
 * @author Pedro Lourenco
 * 
 */

public class CheckGPSStatus extends Service {
	private final Context mContext;

	// flag for GPS status
	boolean isGPSEnabled = false;
	
	boolean isWifiEnabled = false;
	// Declaring a Location Manager
	protected LocationManager locationManager;

	public CheckGPSStatus(Context context) {

		this.mContext = context;
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	/*----Method to Check GPS is enable or disable ----- */
	public Boolean displayGpsStatus() {
		locationManager = (LocationManager) mContext
				.getSystemService(LOCATION_SERVICE);

		// getting GPS status
		isGPSEnabled = locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER);

		if (isGPSEnabled) {
			return true;

		} else {

			return false;
		}

	}
	
	/*----Method to Check GPS is enable or disable ----- */
	public Boolean networkStatus() {
		locationManager = (LocationManager) mContext
				.getSystemService(LOCATION_SERVICE);

		// getting GPS status
		isWifiEnabled = locationManager .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		System.out.println("isWifiEnabled:" + isWifiEnabled);
		

		if (isWifiEnabled) {
			return true;

		} else {

			return false;
		}

	}

}
