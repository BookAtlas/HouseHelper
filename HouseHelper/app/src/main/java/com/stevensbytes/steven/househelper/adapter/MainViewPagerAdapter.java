package com.stevensbytes.steven.househelper.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Steven on 4/9/2017.
 */

public class MainViewPagerAdapter extends PagerAdapter {

    private List<View> viewList;

    public MainViewPagerAdapter(List<View> mListViews) {
        this.viewList = mListViews;
    }

    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewList.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewList.get(position), 0);
        return viewList.get(position);
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }
}
