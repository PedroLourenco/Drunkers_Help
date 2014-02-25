package com.drunkers_helper.activity;

import com.drunkers_help.R;
import com.drunkers_helper.datasource.BeersDataSource;
import com.drunkers_helper.util.globalconstant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author Pedro Lourenco
 * 
 */

public class CounterActivity extends Activity {

	private Integer counterValue = 0;
	private TextView counter;
	private BeersDataSource beer_datasource;	
	private String beerName;
	private ImageView beerImage;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_counter);

		beer_datasource = new BeersDataSource(this);
		beer_datasource.open();

		// get intent data
		Intent i = getIntent();

		// Selected image id
		Integer aux_position = i.getExtras().getInt("id");

		try {
			beerImage = (ImageView) findViewById(R.id.mImgView1);
			beerImage.setImageResource(globalconstant.mThumbIds[aux_position]);
			Integer position = aux_position + 1;
			beerName = beer_datasource.getBeerName(position.toString());

		} catch (ArrayIndexOutOfBoundsException e) {
			beerImage.setImageResource(R.drawable.defaultb);
			beerName = i.getExtras().getString("beerName");
		}

		TextView title = (TextView) findViewById(R.id.title);
		counter = (TextView) findViewById(R.id.counter);

		title.setText(beerName);

		counterValue = beer_datasource.getBeerNameCounter(beerName);
		counter.setText(counterValue.toString());

		beerImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				counter.setText(beer_datasource.incrementCounter(beerName).toString());
			}
		});

		Button bt_minus = (Button) findViewById(R.id.btnMinus);
		bt_minus.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				counter.setText(beer_datasource.decrementCounter(beerName).toString());
			}

		});
	}
}