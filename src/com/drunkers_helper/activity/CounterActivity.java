package com.drunkers_helper.activity;

import com.drunkers_help.R;
import com.drunkers_helper.datasource.BeersDataSource;
import com.drunkers_helper.util.Globalconstant;

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

	private Integer mCounterValue = 0;
	private BeersDataSource mBeerDatasource;	
	private String mBeerName;
	private ImageView mBeerImage;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_counter);

		mBeerDatasource = new BeersDataSource(this);
		mBeerDatasource.open();

		final TextView mCounter;	
		// get intent data
		Intent i = getIntent();

		// Selected image id
		Integer aux_position = i.getExtras().getInt("id");

		try {
			mBeerImage = (ImageView) findViewById(R.id.mImgView1);
			mBeerImage.setImageResource(Globalconstant.mThumbIds[aux_position]);
			Integer position = aux_position + 1;
			mBeerName = mBeerDatasource.getBeerName(position.toString());

		} catch (ArrayIndexOutOfBoundsException e) {
			mBeerImage.setImageResource(R.drawable.defaultb);
			mBeerName = i.getExtras().getString("beerName");
		}

		TextView title = (TextView) findViewById(R.id.title);
		mCounter = (TextView) findViewById(R.id.counter);

		title.setText(mBeerName);

		mCounterValue = mBeerDatasource.getBeerNameCounter(mBeerName);
		mCounter.setText(mCounterValue.toString());

		mBeerImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mCounter.setText(mBeerDatasource.incrementCounter(mBeerName).toString());
			}
		});

		Button bt_minus = (Button) findViewById(R.id.btnMinus);
		bt_minus.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				mCounter.setText(mBeerDatasource.decrementCounter(mBeerName).toString());
			}

		});
	}
}