		package com.drunkers_helper.util;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONArray;
import org.json.JSONObject;



import android.content.Context;
import android.database.Cursor;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;

import com.drunkers_helper.datasource.BeersDataSource;
import com.drunkers_helper.location.MyLocation;

public class ProccessAssyncTask {
	
	
	private BeersDataSource beer_datasource;
	private static final AndroidHttpClient ANDROID_HTTP_CLIENT = AndroidHttpClient.newInstance(MyLocation.class.getName());
	CheckGPSStatus gps;
	
	
	public ProccessAssyncTask(Context context){
		
		beer_datasource = new BeersDataSource(context);
		
		System.out.println("TESTETETEETE1111!!!!");
		
		//Starting the task.
	   new PostTask().execute("");		
		
	}
	


	private String getCityName(String latitude, String longitude){
		String cityName = null;
		
		try {
			String googleMapUrl = "http://maps.googleapis.com/maps/api/geocode/json?latlng="
					+ latitude
					+ ","
					+ longitude
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
	
	
	
	
	 // The definition of our task class
	 private class PostTask extends AsyncTask<String, Integer, String> {
		   
			   
	   @Override
	   protected void onPreExecute() {
	      super.onPreExecute();
	      System.out.println("TESTETETEETE2222!!!!");
	      
	   }
	 
	   @Override
	   protected String doInBackground(String... params) {
		  
		   System.out.println("TESTETETEETE3333!!!!");
		   
		   Cursor listBeers = beer_datasource.getRegistersWithoutLocation();
		   
		   System.out.println("TESTETETEETE44444!!!!");
		   
		   if (listBeers.getCount() != 0 ){
				
			   listBeers.moveToFirst();
			   
			   do {
				   
				   String  colunmId = listBeers.getString(0).toString();
				   String cityName = getCityName(listBeers.getString(1).toString(), listBeers.getString(2).toString());
				
				   if(!cityName.isEmpty()){
					   
					   //update table beer history
					   beer_datasource.setLocationOnTableBeerHistory(colunmId, cityName);					   
				   }			   
			   
			   } while (listBeers.moveToNext());			   
			   
			}		   
		   
		   
	      return "All Done!";
	   }
	 
	   @Override
	   protected void onProgressUpdate(Integer... values) {
	      super.onProgressUpdate(values);
	   }
	 
	   @Override
	   protected void onPostExecute(String result) {
	      super.onPostExecute(result);
	     
	   }
	}

}
