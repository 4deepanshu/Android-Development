package com.schoolshieldparent_ui.view.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.schoolshieldparent_ui.R;
import com.schoolshieldparent_ui.app.MyApplication;
import com.schoolshieldparent_ui.presenter.WebServiceConnection;
import com.schoolshieldparent_ui.view.custom_controls.TextView_Regular;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class CustomPagerAdapter extends PagerAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<String> images = new ArrayList<>();

    public CustomPagerAdapter(Context context, List<String> images) {
        mContext = context;
        this.images = images;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.view_gallery_pager, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageViewGallery_Pager);
        ImageLoader.getInstance().displayImage(WebServiceConnection.GALLERY_IMAGES_URLS + images.get(position),
                imageView);

        TextView_Regular textName = (TextView_Regular) itemView.findViewById(R.id.chidName);
        textName.setText(mContext.getString(R.string.imagefrom) + " " + MyApplication.currentChildName);
        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}
