package com.jiang.android.rxjavaapp.actiity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jiang.android.rxjavaapp.InitDataService;
import com.jiang.android.rxjavaapp.R;
import com.jiang.android.rxjavaapp.common.CommonString;
import com.jiang.android.rxjavaapp.common.SPKey;
import com.jiang.android.rxjavaapp.utils.SharePrefUtil;
import com.jiang.android.rxjavaapp.utils.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class splashActivity extends Activity {


    @Bind(R.id.splash_index)
    SimpleDraweeView mSplashIndex;

    private AlphaAnimation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewLayoutID());
        ButterKnife.bind(this);
        initViewsAndEvents();
    }

    protected void initViewsAndEvents() {
        mSplashIndex.setImageURI(Utils.getUri(CommonString.SPLASH_INDEX_URL));
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
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSplashIndex.startAnimation(animation);
    }

    private void toGuideOrMain() {
        boolean isFirst = SharePrefUtil.getBoolean(this, SPKey.FIRST_ENTER, true);
        if (isFirst) {
            Intent intent = new Intent(this, InitDataService.class);
            startService(intent);
        } else {
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    protected int getContentViewLayoutID() {
        return R.layout.activity_splash;
    }
}
