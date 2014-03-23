/**
 * 
 */
package com.drunkers_helper.activity;

import com.drunkers_help.R;
import com.drunkers_helper.datasource.BeersDataSource;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author Pedro Lourenco
 * 
 */

public class AddBeerActivity extends Activity {

	private BeersDataSource mBeerDatasource;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addbeer);

		mBeerDatasource = new BeersDataSource(this);
		mBeerDatasource.open();
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		 // Handle item selection
		switch (item.getItemId()) {
		case R.id.menu_addBeer:

			EditText new_beer = (EditText) findViewById(R.id.newBeer);

			if (new_beer.getText().toString().isEmpty()) {
				Toast.makeText(getApplicationContext(), getResources().getString(R.string.add_beer),Toast.LENGTH_SHORT).show();
			} else {
				mBeerDatasource.addNewBeer(new_beer.getText().toString());
				Intent i_main = new Intent(getApplicationContext(),	MainActivity.class);
				// clear all the activities on top of home
				i_main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i_main);
			}

			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.actionbar_newbeer, menu);

		return true;
	}
}