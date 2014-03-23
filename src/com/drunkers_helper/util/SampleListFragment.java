package com.drunkers_helper.util;

import com.drunkers_help.R;
import com.drunkers_helper.activity.StatisticActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author Pedro Lourenco
 * 
 */

public class SampleListFragment extends ListFragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.leftlist, null);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		Spanned spannedContent = Html.fromHtml(getResources().getString(R.string.last_week));
		
		
		SampleAdapter adapter = new SampleAdapter(getActivity());
		    adapter.add(new SampleItem(getResources().getString(R.string.top_ten) ,R.drawable.top_10));
		    adapter.add(new SampleItem(getResources().getString(R.string.last_day),R.drawable.cup));
		    adapter.add(new SampleItem(spannedContent.toString(),R.drawable.cup));
		    adapter.add(new SampleItem(getResources().getString(R.string.last_month),R.drawable.cup));
		    adapter.add(new SampleItem(getResources().getString(R.string.last_year),R.drawable.cup));
		    setListAdapter(adapter);

		}
	private class SampleItem {
		public String tag;
		public int iconRes;
		public SampleItem(String tag, int iconRes) {
			this.tag = tag; 
			this.iconRes = iconRes;
		}
	}
	
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
	        
		Intent intent = new Intent(v.getContext(), StatisticActivity.class);
		switch (position) { 
	            case 0:	            	
	                intent.putExtra("option", 1);	            	              
	                break;

	            case 1:
	            	intent.putExtra("option", 2);
	                break;

	            case 2:
	            	intent.putExtra("option", 3);
	                break;
	            case 3:
	            	intent.putExtra("option", 4);
	                break;
	            case 4:
	            	intent.putExtra("option", 5);
	                break;

	            default:
	                break;
	        }
		startActivity(intent);
	
	}

	public class SampleAdapter extends ArrayAdapter<SampleItem> {

		public SampleAdapter(Context context) {
			super(context, 0);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.row, null);
			}
			ImageView icon = (ImageView) convertView.findViewById(R.id.row_icon);
			icon.setImageResource(getItem(position).iconRes);
			TextView title = (TextView) convertView.findViewById(R.id.row_title);
			title.setText(getItem(position).tag);

			return convertView;
		}

	}
}
