package com.drunkers_helper.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import com.drunkers_help.R;

/**
 * @author Pedro Lourenco
 * 
 */

public class AboutActivity extends Activity{


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);		
		
		SpannableStringBuilder builder = new SpannableStringBuilder();

		String e_mail = getResources().getString(R.string.msg_email);
		SpannableString redSpannable= new SpannableString(e_mail);
		redSpannable.setSpan(new ForegroundColorSpan(Color.GRAY), 0, e_mail.length(), 0);
		builder.append(redSpannable);

		String msg = getResources().getString(R.string.email);
		SpannableString whiteSpannable= new SpannableString(msg);
		whiteSpannable.setSpan(new ForegroundColorSpan(Color.LTGRAY), 0, msg.length(), 0);
		builder.append(whiteSpannable);

		TextView email =  (TextView) findViewById(R.id.email);		
		email.setText(builder, BufferType.SPANNABLE);
		
		email.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
				intent.setType("text/plain");
				intent.setData(Uri.parse("mailto:pdrolourenco@gmail.com")); 
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
				startActivity(intent);
			}
		});
		
		TextView browser =  (TextView) findViewById(R.id.web_link);
		browser.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.web_link)));
				startActivity(browserIntent);
				browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
				startActivity(browserIntent);
			}
		});
	
	}		
}