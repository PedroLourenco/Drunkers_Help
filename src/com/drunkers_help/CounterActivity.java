package com.drunkers_help;

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
	private DbHelper dh;
	private Integer position;
	private String beerName;
	private ImageView beerImage;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_counter);

		this.dh = new DbHelper(this);

		// get intent data
		Intent i = getIntent();

		// Selected image id
		Integer aux_position = i.getExtras().getInt("id");

		try {

			beerImage = (ImageView) findViewById(R.id.mImgView1);
			beerImage.setImageResource(globalconstant.mThumbIds[aux_position]);
			position = aux_position + 1;
			beerName = dh.getBeerName(position.toString());

		} catch (ArrayIndexOutOfBoundsException e) {
			beerImage.setImageResource(R.drawable.defaultb);
			beerName = i.getExtras().getString("beerName");
		}

		TextView title = (TextView) findViewById(R.id.title);
		counter = (TextView) findViewById(R.id.counter);

		title.setText(beerName);

		counterValue = dh.getBeerNameCounter(beerName);
		counter.setText(counterValue.toString());

		beerImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				counter.setText(dh.incrementCounter(beerName).toString());
			}
		});

		Button bt_minus = (Button) findViewById(R.id.btnMinus);
		bt_minus.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				counter.setText(dh.decrementCounter(beerName).toString());
			}

		});

	}

}