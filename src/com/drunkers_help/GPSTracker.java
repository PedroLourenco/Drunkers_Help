package com.drunkers_help;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;


/**
 * @author Pedro Lourenco
 *
 */

public class GPSTracker extends Service {
private static final int FIFTEEN_MINUTES = 1000 * 60 * 1;
public LocationManager locationManager;
public MyLocationListener listener;
public Location previousBestLocation = null;
private static final AndroidHttpClient ANDROID_HTTP_CLIENT = AndroidHttpClient.newInstance(GPSTracker.class.getName());

private boolean running = false;
int counter = 0;





@Override
public void onCreate() {
    super.onCreate();    
}

@Override
public void onStart(Intent intent, int startId) {      
    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    listener = new MyLocationListener(); 
    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, FIFTEEN_MINUTES, listener);
    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, FIFTEEN_MINUTES, listener);
   
}

@Override
public IBinder onBind(Intent intent) {
	locationManager.removeUpdates(listener);
    return null;
}

protected boolean isBetterLocation(Location location, Location currentBestLocation) {
    if (currentBestLocation == null) {
        // A new location is always better than no location
        return true;
    }

    // Check whether the new location fix is newer or older
    long timeDelta = location.getTime() - currentBestLocation.getTime();
    boolean isSignificantlyNewer = timeDelta > FIFTEEN_MINUTES;
    boolean isSignificantlyOlder = timeDelta < -FIFTEEN_MINUTES;
    boolean isNewer = timeDelta > 0;

    // If it's been more than two minutes since the current location, use the new location
    // because the user has likely moved
    if (isSignificantlyNewer) {
        return true;
    // If the new location is more than two minutes older, it must be worse
    } else if (isSignificantlyOlder) {
        return false;
    }

    // Check whether the new location fix is more or less accurate
    int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
    boolean isLessAccurate = accuracyDelta > 0;
    boolean isMoreAccurate = accuracyDelta < 0;
    boolean isSignificantlyLessAccurate = accuracyDelta > 200;

    // Check if the old and new location are from the same provider
    boolean isFromSameProvider = isSameProvider(location.getProvider(),
            currentBestLocation.getProvider());

    // Determine location quality using a combination of timeliness and accuracy
    if (isMoreAccurate) {
        return true;
    } else if (isNewer && !isLessAccurate) {
        return true;
    } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
        return true;
    }
    return false;
}

/** Checks whether two providers are the same */
private boolean isSameProvider(String provider1, String provider2) {
    if (provider1 == null) {
      return provider2 == null;
    }
    return provider1.equals(provider2);
}

@Override
public void onDestroy() {    
    super.onDestroy();
    Log.v("STOP_SERVICE", "DONE");
    locationManager.removeUpdates(listener);        
}   

public static Thread performOnBackgroundThread(final Runnable runnable) {
    final Thread t = new Thread() {
        @Override
        public void run() {
            try {
                runnable.run();
            } finally {

            }
        }
    };
    t.start();
    return t;
}

/** Get city name */
public void fetchCityName(final Location location)
{
    if (running)
        return;

    new AsyncTask<Void, Void, String>()
    {
        protected void onPreExecute()
        {
            running = true;
        };

        @Override
        protected String doInBackground(Void... params)
        {
            String googleMapUrl = "http://maps.googleapis.com/maps/api/geocode/json?latlng=" + location.getLatitude() + ","
                    + location.getLongitude() + "&sensor=false&language=fr";

            try
            {
                JSONObject googleMapResponse = new JSONObject(ANDROID_HTTP_CLIENT.execute(new HttpGet(googleMapUrl),
                        new BasicResponseHandler()));

                // many nested loops.. not great -> use expression instead
                // loop among all results
                JSONArray results = (JSONArray) googleMapResponse.get("results");
                for (int i = 0; i < results.length(); i++)
                {
                    // loop among all addresses within this result
                    JSONObject result = results.getJSONObject(i);
                    if (result.has("address_components"))
                    {
                        JSONArray addressComponents = result.getJSONArray("address_components");
                        // loop among all address component to find a 'locality' or 'sublocality'
                        for (int j = 0; j < addressComponents.length(); j++)
                        {
                            JSONObject addressComponent = addressComponents.getJSONObject(j);
                            if (result.has("types"))
                            {
                                JSONArray types = addressComponent.getJSONArray("types");

                                // search for locality and sublocality
                                String cityName = null;

                                for (int k = 0; k < types.length(); k++)
                                {
                                    if ("locality".equals(types.getString(k)) && cityName == null)
                                    {
                                        if (addressComponent.has("long_name"))
                                        {
                                            cityName = addressComponent.getString("long_name");
                                        }
                                        else if (addressComponent.has("short_name"))
                                        {
                                            cityName = addressComponent.getString("short_name");
                                        }
                                    }
                                    if ("sublocality".equals(types.getString(k)))
                                    {
                                        if (addressComponent.has("long_name"))
                                        {
                                            cityName = addressComponent.getString("long_name");
                                        }
                                        else if (addressComponent.has("short_name"))
                                        {
                                            cityName = addressComponent.getString("short_name");
                                        }
                                    }
                                }
                                if (cityName != null)
                                {
                                    return cityName;
                                }
                            }
                        }
                    }
                }
            }
            catch (Exception ignored)
            {
                ignored.printStackTrace();
            }
            return null;
        }

        

        protected void onPostExecute(String cityName)
        {
            running = false;
            if (cityName != null)
            {
                globalconstant.cityName = cityName;
            	
                if(!cityName.equals(globalconstant.cityName)){
                	
                	Toast.makeText(getApplicationContext(),globalconstant.cityName ,Toast.LENGTH_SHORT).show();
                	// Do something with cityName
                	Log.i("GeocoderHelper", globalconstant.cityName);
                }
            }
            else{
            	Toast.makeText(getApplicationContext(),"Cannot get city name!" ,Toast.LENGTH_SHORT).show();
            }           	
            
        };
    }.execute();
}


public class MyLocationListener implements LocationListener
{

    public void onLocationChanged(final Location loc)
    {
        Log.i("**************************************", "Location changed");
        if(isBetterLocation(loc, previousBestLocation)) {
            globalconstant.lat = loc.getLatitude();
            globalconstant.lon = loc.getLongitude();
            fetchCityName(loc);
        }                      
        
        
        System.out.println("lat:"  + globalconstant.lat + "lon:" + globalconstant.lon);
        
        
    }
    
    
    
    

    public void onProviderDisabled(String provider)    {
    	
        Toast.makeText( getApplicationContext(), "Gps Disabled", Toast.LENGTH_SHORT ).show();
    }


    public void onProviderEnabled(String provider)
    {
        Toast.makeText( getApplicationContext(), "Gps Enabled", Toast.LENGTH_SHORT).show();
    }


    public void onStatusChanged(String provider, int status, Bundle extras)
    {

    }

}
}