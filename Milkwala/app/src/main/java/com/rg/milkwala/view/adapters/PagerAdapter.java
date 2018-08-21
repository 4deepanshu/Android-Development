package com.rg.milkwala.view.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.rg.milkwala.R;
import com.rg.milkwala.view.activities.DescriptionActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mobile on 1/11/2017.
 */

public class PagerAdapter extends android.support.v4.view.PagerAdapter {
    List<String> IMAGES = new ArrayList<>();
    Activity activity;

    public PagerAdapter(DescriptionActivity descriptionActivity, List<String> images) {
        this.activity = descriptionActivity;
        this.IMAGES = images;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_viewpager, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageViewIcon);
        Picasso.with(activity).load(IMAGES.get(position)).into(imageView);
        collection.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((RelativeLayout) view);
    }

    @Override
    public int getCount() {
        return IMAGES.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


}
