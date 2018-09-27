package com.fan.collapsinglayout;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CollapsingLayout.OnScrollCallback {

    private CollapsingLayout mCollapsingLayout;
    private ViewPager vp;
    private List<TestFragment> fragments;
    private TabLayout mTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCollapsingLayout = findViewById(R.id.scroll);
        mTab = findViewById(R.id.tab);
        mCollapsingLayout.setOnScrollCallback(this);
        fragments = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            fragments.add(new TestFragment());
        }
        vp = findViewById(R.id.vp);
        vp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return "title " + position;
            }
        });
        mTab.setupWithViewPager(vp);
    }

    @Override
    public boolean canChildScroll(int direction) {
        return fragments.get(vp.getCurrentItem()).canScrollVertically(direction);
    }

}
