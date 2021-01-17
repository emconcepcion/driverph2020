package com.cav.DriverphTruckerlearningPH2020;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

public class Help extends AppCompatActivity {
    WormDotsIndicator dot, dot1, dot2,dot3;
    ViewPager viewPager, viewPager2, viewPager3, viewPager4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        viewPager = findViewById(R.id.viewPager);
        dot = findViewById(R.id.dot);
        ImageAdapter adapter = new ImageAdapter(this);
        viewPager.setAdapter(adapter);
        dot.setViewPager(viewPager);

        viewPager2 = findViewById(R.id.viewPager2);
        dot1 = findViewById(R.id.dot1);
        ImageAdapter1 adapter1 = new ImageAdapter1(this);
        viewPager2.setAdapter(adapter1);
        dot1.setViewPager(viewPager2);
    }
}