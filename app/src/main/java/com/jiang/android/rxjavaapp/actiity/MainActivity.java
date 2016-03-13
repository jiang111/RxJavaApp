package com.jiang.android.rxjavaapp.actiity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.jiang.android.rxjavaapp.R;
import com.jiang.android.rxjavaapp.base.BaseActivity;
import com.jiang.android.rxjavaapp.base.BaseWebActivity;
import com.jiang.android.rxjavaapp.common.CommonString;
import com.jiang.android.rxjavaapp.common.SPKey;
import com.jiang.android.rxjavaapp.utils.SharePrefUtil;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private Toolbar toolbar;
    private LinearLayout mHeadView;

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return true;
    }

    @Override
    protected void initViewsAndEvents() {
        boolean isFirst = SharePrefUtil.getBoolean(this, SPKey.FIRST_ENTER, true);

        initToolBar();
        initNavigationView();
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }

    private void initNavigationView() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        mHeadView = (LinearLayout) navigationView.getHeaderView(0);
        mHeadView.setClickable(true);
        mHeadView.setOnClickListener(this);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.common_toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.index_head:
                Bundle bundle = new Bundle();
                bundle.putString(BaseWebActivity.BUNDLE_KEY_URL, CommonString.GITHUB_URL);
                bundle.putBoolean(BaseWebActivity.BUNDLE_KEY_SHOW_BOTTOM_BAR, true);
                bundle.putString(BaseWebActivity.BUNDLE_KEY_TITLE, getString(R.string.github));
                readyGo(BaseWebActivity.class, bundle);
                break;
        }
    }
}
