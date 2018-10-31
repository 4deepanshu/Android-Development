package com.schoolshieldparent_ui.view.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.GridView;

import com.schoolshieldparent_ui.R;
import com.schoolshieldparent_ui.app.MyApplication;
import com.schoolshieldparent_ui.controller.utils.DialogManager;
import com.schoolshieldparent_ui.presenter.WebServiceResult;
import com.schoolshieldparent_ui.view.adapter.Adapter_Gallery;
import com.schoolshieldparent_ui.view.adapter.CustomPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

public class Activity_Gallery extends AppCompatActivity {
    @BindView(R.id.gridViewGallery)
    GridView gridViewImages;

    @BindView(R.id.pagerGallery)
    ViewPager pagerGallery;
    int page = 1;
    static Activity_Gallery instance;
    private Adapter_Gallery adapter;
    private CustomPagerAdapter pagerAdapter;
    private List<String> galleryimages = new ArrayList<>();
    private boolean needNextPage = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        instance = this;
        getImages(getString(R.string.refresh));
        setSwipeLoader();


    }

    public static Activity_Gallery getInstance() {
        return instance;
    }

    private void getImages(String refresh) {
        if (refresh.equalsIgnoreCase(getString(R.string.refresh))) {
            WebServiceResult.GalleryImages(MyApplication.currentChildID + "", page + "");
        } else {
            DialogManager.startProgressDialog(Activity_Gallery.getInstance());
            WebServiceResult.GalleryImages(MyApplication.currentChildID + "", page + "");
        }
    }

    public void updateGallery(List<String> images) {
        setAdapter(images);
    }

    private void setAdapter(List<String> images) {
        if (images.size() > 0) {
            page++;
            needNextPage = true;
        } else {
            needNextPage = false;
        }

        if (galleryimages.size() == 0) {
            galleryimages.clear();
            galleryimages.addAll(images);
            adapter = new Adapter_Gallery(this, galleryimages);
            pagerAdapter = new CustomPagerAdapter(getApplicationContext(), galleryimages);
            gridViewImages.setAdapter(adapter);
            pagerGallery.setAdapter(pagerAdapter);

        } else {
            galleryimages.addAll(images);
            adapter.notifyDataSetChanged();
            pagerAdapter.notifyDataSetChanged();
        }
        if (needNextPage == true) {
            getImages(getString(R.string.refresh));
        }


    }



    @OnItemClick(R.id.gridViewGallery)
    public void showFullImageView(int position) {
        pagerGallery.setCurrentItem(position, false);
        pagerGallery.setVisibility(View.VISIBLE);

    }

    @OnClick(R.id.imageButton_Back)
    public void doback() {
        finish();
        overridePendingTransition(0, 0);

    }


    @Override
    public void onBackPressed() {
        if (pagerGallery.getVisibility() == View.VISIBLE) {
            pagerGallery.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
            overridePendingTransition(0, 0);
        }
    }

    @BindView(R.id.swipeContainerGallery)
    SwipeRefreshLayout swipeLayout;

    private void setSwipeLoader() {

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    swipeLayout.setRefreshing(false);
                    galleryimages.clear();
                    if(pagerAdapter!=null)
                    {
                        pagerAdapter.notifyDataSetChanged();
                    }
                    if(adapter!=null)
                    {
                        adapter.notifyDataSetChanged();

                    }

                    page = 1;
                    getImages(getString(R.string.refresh));
                } catch (NullPointerException e) {

                }
            }
        });

        swipeLayout.setColorSchemeResources(android.R.color.black,

                android.R.color.black,

                android.R.color.black,

                android.R.color.black);


    }

    @OnClick(R.id.imageButton_Back)
    public void goBack() {
        if (pagerGallery.getVisibility() == View.VISIBLE) {
            pagerGallery.setVisibility(View.GONE);
        } else {
            overridePendingTransition(0, 0);
        }
    }
}
