package com.schoolshieldparent_ui.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.schoolshieldparent_ui.R;
import com.schoolshieldparent_ui.presenter.WebServiceConnection;

import java.util.List;

public class Adapter_Gallery extends BaseAdapter {

    List<String> images;
    Activity activity;

    private int width;
    private DisplayImageOptions options;
    public static ImageLoader imageLoader;

    public Adapter_Gallery(Activity activity, List<String> images) {
        this.activity = activity;
        this.images = images;

        DisplayMetrics metrics = new DisplayMetrics();
        this.activity.getWindowManager().getDefaultDisplay().getMetrics( metrics );

        width = metrics.widthPixels;
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading( R.drawable.gallery_img_paceholder )
                .showImageForEmptyUri( R.drawable.gallery_img_paceholder )
                .showImageOnFail( R.drawable.gallery_img_paceholder )
                .cacheInMemory( true )
                .cacheOnDisk( true )
                .considerExifParams( true )
                .bitmapConfig( Bitmap.Config.RGB_565 )
                .build();
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflate = (LayoutInflater) activity.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            convertView = inflate.inflate( R.layout.view_gallery, null );
            holder.image = (ImageView) convertView.findViewById( R.id.imageViewGallery );
            LayoutParams layoutParams = new LayoutParams( (int) (width / 3), (int) (width / 3) );
            layoutParams.setMargins( 8, 4, 0, 8 );
            holder.image.setLayoutParams( layoutParams );
            convertView.setTag( holder );
        } else {
            holder = (ViewHolder) convertView.getTag();

        }
        imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage( WebServiceConnection.GALLERY_IMAGES_URLS + images.get( position ), holder.image, options, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            }
        }, new ImageLoadingProgressListener() {
            @Override
            public void onProgressUpdate(String imageUri, View view, int current, int total) {
            }
        } );

        return convertView;

    }

    public class ViewHolder {
        ImageView image;
    }


}
