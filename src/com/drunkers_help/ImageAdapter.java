package com.drunkers_help;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {

	Context mContext;  
	public Integer[] mThumbIds = {
			R.drawable.amigos, R.drawable.amstel,
            R.drawable.asahi_black, R.drawable.asahi,
            R.drawable.baltika, R.drawable.bavaria,
            R.drawable.becks, R.drawable.birra_moretti,
            R.drawable.bitburger, R.drawable.brahama,
            R.drawable.budweiser, R.drawable.carlsberg,
            R.drawable.chang, R.drawable.cobra,
            R.drawable.corona, R.drawable.cristal,
            R.drawable.duff, R.drawable.duvel,
            R.drawable.foster, R.drawable.guiness,
            R.drawable.heineken,R.drawable.karlovacko, 
            R.drawable.kilkenny, R.drawable.kronenbourg_1994,
            R.drawable.london_pride, R.drawable.superbock,
            R.drawable.old_speckled_hen, R.drawable.peroni_nastro,
            R.drawable.pint, R.drawable.royal_dutch,
            R.drawable.sagres, R.drawable.san_miguel,
            R.drawable.stella_artois, R.drawable.strela,
            R.drawable.tagus
    };
	 
	 public ImageAdapter(Context c){ 
	        mContext = c; 
	    } 


	public int getCount() {
		return mThumbIds.length; 
		
	}

	public Object getItem(int arg0) {
		return mThumbIds[arg0]; 
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

    
    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(110, 110));
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setPadding(2, 2, 2, 2);
        } else {
            imageView = (ImageView) convertView;
        }

        
        
        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }
    
    

}
