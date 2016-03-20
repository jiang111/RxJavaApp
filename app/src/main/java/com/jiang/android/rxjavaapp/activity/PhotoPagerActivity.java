package com.jiang.android.rxjavaapp.activity;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.jiang.android.rxjavaapp.R;
import com.jiang.android.rxjavaapp.base.BaseActivity;
import com.jiang.android.rxjavaapp.widget.HackyViewPager;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;


public class PhotoPagerActivity extends BaseActivity {

    HackyViewPager viewPager;

    private ArrayList<String> photos;
    private int position;

    void initViews() {
        photos = getIntent().getStringArrayListExtra("files");
        position = getIntent().getIntExtra("position", 0);
        viewPager.setAdapter(new SamplePagerAdapter());
        viewPager.setCurrentItem(position);
    }
    @Override
    protected void initViewsAndEvents() {
        viewPager = (HackyViewPager) findViewById(R.id.photo_view_pager);
        initViews();

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_photoview;
    }

    class SamplePagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return photos.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            PhotoView photoView = new PhotoView(container.getContext());
            ImageLoader.getInstance().displayImage(photos.get(position), photoView);
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

}
