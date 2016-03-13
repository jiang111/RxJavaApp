package com.jiang.android.rxjavaapp.actiity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiang.android.rxjavaapp.R;
import com.jiang.android.rxjavaapp.adapter.BaseAdapter;
import com.jiang.android.rxjavaapp.adapter.holder.BaseViewHolder;
import com.jiang.android.rxjavaapp.adapter.inter.OnItemClickListener;
import com.jiang.android.rxjavaapp.base.BaseActivity;
import com.jiang.android.rxjavaapp.base.BaseWebActivity;
import com.jiang.android.rxjavaapp.common.CommonString;
import com.jiang.android.rxjavaapp.database.alloperators;
import com.jiang.android.rxjavaapp.database.helper.DbUtil;
import com.jiang.android.rxjavaapp.database.operators;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private LinearLayout mHeadView;

    RecyclerView mNavRecyclerView;
    BaseAdapter mAdapter;
    BaseAdapter mContentAdapter;

    private List<operators> mList = new ArrayList<>();
    private List<alloperators> mContentLists = new ArrayList<>();
    private RecyclerView mContentRecyclerView;
    private ArrayList<String> photos;

    @Override
    protected void initViewsAndEvents() {
        initToolBar();
        initNavigationView();
        initNavRecycerView();
        mContentRecyclerView = (RecyclerView) findViewById(R.id.id_content);

    }

    private void initContentRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mContentRecyclerView.setLayoutManager(manager);
        mContentRecyclerView.setHasFixedSize(true);
        Observable.create(new Observable.OnSubscribe<List<alloperators>>() {
            @Override
            public void call(Subscriber<? super List<alloperators>> subscriber) {
                try {
                    subscriber.onNext(DbUtil.getAllOperatorsService()
                            .query("where operators_id=?", new String[]{String.valueOf(mList.get(0).getOuter_id())}));
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }

            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<alloperators>>() {
                    @Override
                    public void call(List<alloperators> operatorses) {
                        mContentLists.clear();
                        mContentLists.addAll(operatorses);
                        initContentAdapter();
                    }
                });

    }

    private void initContentAdapter() {
        mContentAdapter = new BaseAdapter() {
            @Override
            protected void onBindView(BaseViewHolder holder, final int position) {

                ImageView iv = holder.getView(R.id.item_content_iv);
                TextView title = holder.getView(R.id.item_content_title);
                TextView desc = holder.getView(R.id.item_content_desc);
                title.setText(mContentLists.get(position).getName());
                desc.setText(mContentLists.get(position).getDesc());
                ImageLoader.getInstance().displayImage(mContentLists.get(position).getImg(), iv);
                iv.setClickable(true);
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showImgFullScreen(position);
                    }
                });
            }

            @Override
            protected int getLayoutID(int position) {
                return R.layout.item_index_content;
            }

            @Override
            public int getItemCount() {
                return mContentLists.size();
            }
        };
        mContentAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Bundle bundle = new Bundle();
                bundle.putString(BaseWebActivity.BUNDLE_KEY_TITLE, mContentLists.get(position).getName());
                bundle.putString(BaseWebActivity.BUNDLE_KEY_URL, mContentLists.get(position).getUrl());
                bundle.putBoolean(BaseWebActivity.BUNDLE_KEY_SHOW_BOTTOM_BAR, true);
                readyGo(BaseWebActivity.class, bundle);

            }
        });
        mContentRecyclerView.setAdapter(mContentAdapter);
    }

    private void initNavRecycerView() {

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mNavRecyclerView.setLayoutManager(manager);
        mNavRecyclerView.setHasFixedSize(true);

        Observable.create(new Observable.OnSubscribe<List<operators>>() {
            @Override
            public void call(Subscriber<? super List<operators>> subscriber) {
                try {
                    subscriber.onNext(DbUtil.getOperatorsService().queryAll());
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }

            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<operators>>() {
                    @Override
                    public void call(List<operators> operatorses) {
                        mList.clear();
                        mList.addAll(operatorses);
                        initAdapter();
                        initContentRecyclerView();
                    }
                });


    }

    private void initAdapter() {

        mAdapter = new BaseAdapter() {
            @Override
            public int getItemCount() {
                return mList.size();
            }

            @Override
            protected void onBindView(BaseViewHolder holder, int position) {
                TextView tv = holder.getView(R.id.item_nav_head_v);
                tv.setText(mList.get(position).getName());
            }

            @Override
            protected int getLayoutID(int position) {
                return R.layout.item_nav_head;
            }
        };
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                showToast(mNavRecyclerView, mList.get(position).getName());
            }
        });
        mNavRecyclerView.setAdapter(mAdapter);
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
        mNavRecyclerView = (RecyclerView) navigationView.getHeaderView(0).findViewById(R.id.index_nav_recycler);
        mHeadView.setClickable(true);
        mHeadView.setOnClickListener(this);

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


    public void showImgFullScreen(int pos) {
        if (photos == null) {
            photos = new ArrayList<>();
        }
        if (photos.size() != mContentLists.size()) {
            photos.clear();
            for (int i = 0; i < mContentLists.size(); i++) {
                photos.add(mContentLists.get(i).getImg());
            }
        }
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("files", photos);
        bundle.putInt("position", pos);
        readyGo(PhotoPagerActivity.class, bundle);

    }
}
