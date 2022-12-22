package com.seca.skincare.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.seca.skincare.R;

import java.util.ArrayList;

public  class MyPagerAdapter extends FragmentPagerAdapter {
	    private static int NUM_ITEMS = 2;
	    public Context mContext;
	    public ArrayList<Fragment> fragments ;
	    public int count =0;

    public void setCount(int count) {
        this.count = count;
    }

    public MyPagerAdapter(FragmentManager fragmentManager, Context context, ArrayList<Fragment> frags) {
            super(fragmentManager);
            mContext= context;
            fragments = frags;
        }
        
        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }
 
        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
               return fragments.get(0);
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return fragments.get(1);

            default:
            	return null;
            }
        }
        
        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return "All Products";
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return "Cart";


            }
            return "";
        }

    public View getTabView(int position) {
        // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
        View v = LayoutInflater.from(mContext).inflate(R.layout.tab_view, null);
        TextView tv = (TextView) v.findViewById(R.id.title);
        ImageView img = (ImageView) v.findViewById(R.id.image);
        AppCompatTextView badge = (AppCompatTextView) v.findViewById(R.id.badge);

        switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                tv.setText("All Products");
                img.setVisibility(View.GONE);
                badge.setVisibility(View.GONE);
                break;

            case 1: // Fragment # 0 - This will show FirstFragment different title
                img.setVisibility(View.VISIBLE);
                badge.setVisibility(View.GONE);
                tv.setText("Cart");
                badge.setText(String.valueOf(count));

        }
        return v;
    }

}