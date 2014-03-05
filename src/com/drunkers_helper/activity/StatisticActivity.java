package com.drunkers_helper.activity;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import com.drunkers_help.R;
import com.drunkers_helper.datasource.BeersDataSource;
import com.drunkers_helper.util.Globalconstant;

/**
 * @author PedroLourenco
 * 
 */

public class StatisticActivity extends Activity { 

	private String TAG = "StatisticActivity"; 
	private BeersDataSource beer_datasource;
	private String total = "0";
	String[] fields = new String[]  { "Beer_Name", "_id" };
    int[] views = new int[] { R.id.text1, R.id.text2};


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activiy_statistic);
		
		beer_datasource = new BeersDataSource(this);
		beer_datasource.open();
		
		Cursor listcounter = null;
		Intent intent = getIntent();
		
		Integer option = intent.getExtras().getInt("option");
		
		
		try{
			
		
		if(option == 1) {
			listcounter = beer_datasource.getTopTenBeerss();
			total = "N/A";
			
			
		}else if (option == 2){
			listcounter = beer_datasource.getLastDayBeers();
			total = beer_datasource.getLastDayBeersTotal();
		}
		
		else if (option == 3){
			listcounter = beer_datasource.getLastWeekBeers();
			total = beer_datasource.getLastWeekBeersTotal();
		}
		else if (option == 4){
			
			listcounter = beer_datasource.getLastMonthBeers();
			total = beer_datasource.getLastMonthBeersTotal();
			
		}
		else if (option == 5){
			listcounter = beer_datasource.getLastYearBeers();
			total = beer_datasource.getLastYearBeersTotal();
		}
		
		
		}catch(Exception e){
			if (Globalconstant.LOG)
				Log.d(TAG, "No Records!");
			
		}
		
		
		
		
		ListView list = (ListView) findViewById(R.id.dailyListView2);
				
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				R.layout.list_view2, listcounter,
				fields, views,0);
		
		if(!total.equals("0") && !total.equals("N/A")){
			
			View header = (View)getLayoutInflater().inflate(R.layout.list_view_statistic_header, null);
			list.addHeaderView(header);
		    TextView header_text = (TextView) findViewById(R.id.txtHeader);
			header_text.setText(total + " Beers");
			
		}
		
		if(total.equals("0")){
			View header = (View)getLayoutInflater().inflate(R.layout.list_view_statistic_header, null);
			list.addHeaderView(header);
		    TextView header_text = (TextView) findViewById(R.id.txtHeader);
			header_text.setText(" No records...");
			header_text.setTextSize(15);
		}
		 
		list.setAdapter(adapter);

	}

	

}
