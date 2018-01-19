package com.lijunc.myapplication.module.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.lijunc.myapplication.MvpApplication;
import com.lijunc.myapplication.R;
import com.lijunc.myapplication.injector.components.ApplicationComponent;
import com.lijunc.myapplication.utils.SwipeRefreshHelper;
import com.lijunc.myapplication.widget.EmptyLayout;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.security.ProtectionDomain;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lijunc on 2017/12/27.
 */

public abstract class BaseActivity<T extends IBasePresenter> extends RxAppCompatActivity implements IBaseView{

    @Nullable
    @BindView(R.id.empty_layout)
    protected EmptyLayout mEmptyLayout;


    @Inject
    protected T mPresenter;

    @Nullable
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;


    /**
     * 绑定布局文件
     * @return
     */
    @LayoutRes
    protected abstract int attachLayoutRes();

    /**
     * Dagger注入
     */
    protected abstract void initInjector();

    /**
     * 初始化视图
     */
    protected abstract void initViews();


    //更新视图
    protected abstract void updateViews(boolean isRefresh);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(attachLayoutRes());
        ButterKnife.bind(this);
        initInjector();
        initViews();
        initSwipeRefresh();
        updateViews(false);
    }

    protected void initSwipeRefresh(){
        if(mSwipeRefresh!=null){
            SwipeRefreshHelper.init(mSwipeRefresh, new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    updateViews(true);
                }
            });
        }
    }

    protected ApplicationComponent getAppComponent(){
        return MvpApplication.getAppComponent();
    }
    protected void initToolBar(Toolbar toolbar,boolean homeAsUpEnable,String title){
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(homeAsUpEnable);
    }
    protected void initToolBar(Toolbar toolbar, boolean homeAsUpEnabled, int resTitle) {
        initToolBar(toolbar, homeAsUpEnabled, getString(resTitle));
    }

    /**
     * 添加Fragment
     */
    protected void addFragment(int containerView, Fragment fragment,String tag){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(containerView,fragment,tag);
        transaction.addToBackStack(tag);
        transaction.commit();
    }

    /**
     * 替换fragment
     */
    protected void replaceFragment(int containerView, Fragment fragment,String tag){
        if(getSupportFragmentManager().findFragmentByTag(tag)==null){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(containerView,fragment,tag);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            // 这里要设置tag，上面也要设置tag
            transaction.addToBackStack(tag);
            transaction.commit();
        }else{
            getSupportFragmentManager().popBackStack(tag,0);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showLoading() {
        if(mEmptyLayout!=null){
            mEmptyLayout.setEmptyStatus(EmptyLayout.STATUS_LOADING);
        }
    }

    @Override
    public void hideLoading() {
        if (mEmptyLayout != null) {
            mEmptyLayout.hide();
        }
    }

    @Override
    public void showNetError(final EmptyLayout.OnRetryListener onRetryListener) {
        if (mEmptyLayout != null) {
            mEmptyLayout.setEmptyStatus(EmptyLayout.STATUS_NO_NET);
            mEmptyLayout.setRetryListener(onRetryListener);
        }
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.<T>bindToLifecycle();
    }

    @Override
    public void finishRefresh() {
        if(mSwipeRefresh!=null){
            mSwipeRefresh.setEnabled(false);
        }
    }
}
