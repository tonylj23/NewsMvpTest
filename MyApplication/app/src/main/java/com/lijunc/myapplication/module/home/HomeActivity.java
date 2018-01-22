package com.lijunc.myapplication.module.home;

import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.lijunc.myapplication.R;
import com.lijunc.myapplication.module.base.BaseActivity;
import com.lijunc.myapplication.module.news.main.NewsMainFragment;
import com.trello.rxlifecycle2.LifecycleTransformer;

import butterknife.BindView;

/**
 * Created by lijunc on 2018/1/4.
 */

public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{

    @BindView(R.id.fl_container)
    FrameLayout mFrameLayout;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    private int mItemId = -1;

    private Handler mHandler=new Handler(new Handler.Callback(){
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case R.id.nav_news:
                    break;
            }
            return true;
        }

    });

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_home;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initViews() {
        _initDrawerLayout(mDrawerLayout,mNavView);
    }

    private void _initDrawerLayout(DrawerLayout layout, NavigationView view) {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            WindowManager.LayoutParams attributes = getWindow().getAttributes();
            attributes.flags=(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS|attributes.flags);
            layout.setFitsSystemWindows(false);
            layout.setClipToPadding(true);
        }
        layout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerClosed(View drawerView) {
                mHandler.sendEmptyMessage(mItemId);
            }
        });
        view.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        mNavView.setCheckedItem(R.id.nav_news);
        addFragment(R.id.fl_container,new NewsMainFragment(),"News");
    }
}
