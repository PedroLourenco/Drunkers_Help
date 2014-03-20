package com.drunkers_helper.util;



import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;

import com.drunkers_help.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;




public class BaseActivity extends SlidingFragmentActivity {
SlidingMenu menu;
private int mTitleRes;
protected ListFragment mFrag;

public BaseActivity(int titleRes) {
  mTitleRes = titleRes;
}

@Override
public void onCreate(Bundle savedInstanceState) {
   super.onCreate(savedInstanceState);

   setTitle(mTitleRes);

   // set the Behind View
   setBehindContentView(R.layout.menu_frame);
   if (savedInstanceState == null) {
       FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
       mFrag = new SampleListFragment();
       t.replace(R.id.menu_frame, mFrag);
       t.commit();
   } else {
       mFrag = (ListFragment)this.getSupportFragmentManager().findFragmentById(R.id.menu_frame);
   }

   // customize the SlidingMenu
   SlidingMenu sm = getSlidingMenu();
   sm.setShadowWidthRes(R.dimen.shadow_width);
   sm.setShadowDrawable(R.drawable.shadow);
   sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
   sm.setFadeDegree(0.35f);
   sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
   
  
   



}


  }
