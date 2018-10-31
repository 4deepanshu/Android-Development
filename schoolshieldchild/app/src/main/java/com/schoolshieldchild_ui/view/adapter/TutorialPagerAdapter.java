package com.schoolshieldchild_ui.view.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.schoolshieldchild_ui.R;
import com.schoolshieldchild_ui.view.custom_controls.TextView_Regular;

import java.util.List;

/**
 * Created by root on 20/5/16.
 */
public class TutorialPagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<Integer> Tutorialimages;
    private String[] TutorialText;
   public static int pos = 0;

    public TutorialPagerAdapter(Context mContext, List<Integer> Tutorialimages) {
        this.mContext = mContext;
        this.Tutorialimages = Tutorialimages;
        TutorialText = mContext.getResources().getStringArray(R.array.TutorialText);
    }

    @Override
    public int getCount() {
        return Tutorialimages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.tutorialpager_item, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.img_pager_item);
        TextView_Regular textView_PagerTitle = (TextView_Regular) itemView.findViewById(R.id.textView_PagerTitle);
        imageView.setBackgroundResource(Tutorialimages.get(position));
        textView_PagerTitle.setText(TutorialText[position]);

        container.addView(itemView);
        pos = position;

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}

