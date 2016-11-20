package com.jiang.android.rxjavaapp.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

    private static final int REQUEST_STORAGE = 1010;
    private Toolbar toolbar;
    private LinearLayout mHeadView;

    RecyclerView mNavRecyclerView;
    BaseAdapter mAdapter;
    BaseAdapter mContentAdapter;
    private int checkedPosition = 0;

    private List<operators> mList = new ArrayList<>();
    private List<alloperators> mContentLists = new ArrayList<>();
    private RecyclerView mContentRecyclerView;
    private ArrayList<String> photos;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;


    @Override
    protected void initViewsAndEvents() {
        initToolBar();
        initNavigationView();
        initNavRecycerView();
        mContentRecyclerView = (RecyclerView) findViewById(R.id.id_content);


    }

    private void getAllOperatorById(final long parent_id) {
        Observable.create(new Observable.OnSubscribe<List<alloperators>>() {
            @Override
            public void call(Subscriber<? super List<alloperators>> subscriber) {
                try {
                    subscriber.onNext(DbUtil.getAllOperatorsService()
                            .query("where operators_id=?", new String[]{String.valueOf(parent_id)}));
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

    private void initContentRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mContentRecyclerView.setLayoutManager(manager);
        mContentRecyclerView.setHasFixedSize(true);
        if (mList != null && mList.size() > 0) {
            getAllOperatorById(mList.get(0).getOuter_id());
        }

    }

    private void initContentAdapter() {
        if (mContentAdapter == null) {
            mContentAdapter = new BaseAdapter() {
                @Override
                protected void onBindView(BaseViewHolder holder, final int position) {

                    ImageView iv = holder.getView(R.id.item_content_iv);
                    TextView title = holder.getView(R.id.item_content_title);
                    TextView desc = holder.getView(R.id.item_content_desc);
                    TextView thread = holder.getView(R.id.item_content_thread);
                    if (TextUtils.isEmpty(mContentLists.get(position).getThread())) {
                        thread.setText("默认线程");
                    } else {
                        thread.setText(mContentLists.get(position).getThread());
                    }
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
        } else {
            mContentRecyclerView.getLayoutManager().scrollToPosition(0);
            mContentAdapter.notifyDataSetChanged();
        }
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
                if (position == checkedPosition) {
                    tv.setBackgroundColor(getResources().getColor(R.color._eeeeee));
                } else {
                    tv.setBackgroundColor(getResources().getColor(R.color.white));
                }
            }

            @Override
            protected int getLayoutID(int position) {
                return R.layout.item_nav_head;
            }
        };
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                checkedPosition = position;
                mAdapter.notifyDataSetChanged();
                getAllOperatorById(mList.get(position).getOuter_id());
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
            }
        });
        mNavRecyclerView.setAdapter(mAdapter);
    }


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }

    private void initNavigationView() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);

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
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                Bundle bundle = new Bundle();
                bundle.putString(BaseWebActivity.BUNDLE_KEY_URL, "https://github.com/jiang111?tab=repositories");
                bundle.putString(BaseWebActivity.BUNDLE_KEY_TITLE, "关于");
                bundle.putBoolean(BaseWebActivity.BUNDLE_KEY_SHOW_BOTTOM_BAR, true);
                readyGo(BaseWebActivity.class, bundle);
                break;
            case R.id.share:
                shareText(item.getActionView());
                break;
            case R.id.mark:
                try {
                    Intent viewIntent = new Intent("android.intent.action.VIEW",
                            Uri.parse("market://details?id=" + getPackageName()));
                    startActivity(viewIntent);
                } catch (Exception e) {
                    e.printStackTrace();
                    toast("手机未安装应用市场");
                }
        }


        return super.onOptionsItemSelected(item);
    }

    private void toast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    public void shareText(View view) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Hi,我正在学习RxJava,推荐你下载这个app一起学习吧 到应用商店或者https://github.com/jiang111/RxJavaApp/releases即可下载");
        shareIntent.setType("text/plain");
        startActivity(Intent.createChooser(shareIntent, "分享到"));
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
