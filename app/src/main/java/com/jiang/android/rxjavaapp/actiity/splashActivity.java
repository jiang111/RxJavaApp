package com.jiang.android.rxjavaapp.actiity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.jiang.android.rxjavaapp.InitDataService;
import com.jiang.android.rxjavaapp.R;
import com.jiang.android.rxjavaapp.common.CommonString;
import com.jiang.android.rxjavaapp.common.SPKey;
import com.jiang.android.rxjavaapp.utils.SharePrefUtil;
import com.nostra13.universalimageloader.core.ImageLoader;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class splashActivity extends Activity {


    ImageView mSplashIndex;

    private AlphaAnimation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewLayoutID());
        boolean isFirst = SharePrefUtil.getBoolean(this, SPKey.FIRST_ENTER, true);
        if (isFirst) {
            Intent intent = new Intent(this, InitDataService.class);
            startService(intent);
            this.finish();
        }
        mSplashIndex = (ImageView) findViewById(R.id.splash_index);
        initViewsAndEvents();
    }

    protected void initViewsAndEvents() {
        ImageLoader.getInstance().displayImage(CommonString.SPLASH_INDEX_URL, mSplashIndex);
        animation = new AlphaAnimation(0, 1.0f);
        animation.setDuration(3 * 1000);
        animation.setFillAfter(true);


        animation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            // 动画结束监听
            @Override
            public void onAnimationEnd(Animation animation) {
                toGuideOrMain();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        mSplashIndex.startAnimation(animation);
    }

    private void toGuideOrMain() {

        startActivity(new Intent(this, MainActivity.class));
        this.finish();

    }

    protected int getContentViewLayoutID() {
        return R.layout.activity_splash;
    }
}
