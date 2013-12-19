		package com.drunkers_helper.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONArray;
import org.json.JSONObject;









import android.content.Context;
import android.database.Cursor;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;

import com.drunkers_helper.datasource.BeersDataSource;
import com.drunkers_helper.location.MyLocation;

public class ProccessAssyncTask extends AsyncTask<Void,Void,Bundle> {
	
	
	private BeersDataSource beer_datasource;
	private static final AndroidHttpClient ANDROID_HTTP_CLIENT = AndroidHttpClient.newInstance(MyLocation.class.getName());
	CheckGPSStatus gps;
	Map<String,String> mcoordenadas = new HashMap<String,String>();
	
	
	public ProccessAssyncTask(Map<String, String>  coordenadas){
		
		
		mcoordenadas.putAll(coordenadas);
		
		
		
		System.out.println("TESTETETEETE1111!!!!");
		onPreExecute();
		doInBackground();
			
		
	}
	


	private String getCityName(String latitude, String longitude){
		String cityName = null;
		System.out.println("getCityName: ---------------------------");
		
		String latitudes = "41.188832";
		String longitudes = "-8.5948463";
		try {
			String googleMapUrl = "http://maps.googleapis.com/maps/api/geocode/json?latlng="
					+ latitudes
					+ ","
					+ longitudes
					+ "&sensor=false&language=fr";
			
			System.out.println("CITY NAME: ---------------------------67");

			JSONObject googleMapResponse = new JSONObject(
					ANDROID_HTTP_CLIENT.execute(new HttpGet(
							googleMapUrl), new BasicResponseHandler()));
			
			System.out.println("CITY NAME: ---------------------------71");

			// many nested loops.. not great -> use expression instead
			// loop among all results
			JSONArray results = (JSONArray) googleMapResponse
					.get("results");
			
			System.out.println("CITY NAME: ---------------------------78");
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
								
								System.out.println("CITY NAME: ---------------------------"+ cityName);
								return cityName;
							}
						}
					}
				}
			}
		} catch (Exception ignored) {
			System.out.println("getCityName: -------------ignored--------------" + ignored.getMessage());
			
			ignored.printStackTrace();
		}
		return null;
		
			
	}
	
	
	
	
	 // The definition of our task class
	/* private class PostTask extends AsyncTask<String, Integer, String> {
		   
			   
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

*/
	@Override
	   protected void onPreExecute() {
	      super.onPreExecute();
	      System.out.println("onPreExecute------------------------------!!!!");
	      
	      
	   }

	@Override
	protected Bundle doInBackground(Void... arg0) {
		
		 System.out.println("doInBackground------------------------------!!!!");
		Iterator it = mcoordenadas.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
	        System.out.println(pairs.getKey().toString() + "----> " + pairs.getValue().toString());
	        it.remove(); // avoids a ConcurrentModificationException
	   
	        getCityName(pairs.getKey().toString(), pairs.getValue().toString());
	    
	    
	    }
		
		
		
		
		
		
		//
		return null;
	}

}
