package com.cav.DriverphTruckerlearningPH2020;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class ImageAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private int[] mImageIds = new int[]{R.drawable.vr1, R.drawable.vr2, R.drawable.vr3, R.drawable.vr4, R.drawable.vr5};
    ImageAdapter(Context context) {
        this.context = context;
    }
    @Override
    public int getCount() {
        return mImageIds.length;
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item, null);
        ImageView imageView = view.findViewById(R.id.image_view);
        imageView.setImageResource(mImageIds[position]);
        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view, 0);
        return view;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);
    }
}

