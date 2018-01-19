package com.lijunc.myapplication.module.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lijunc.myapplication.MvpApplication;
import com.lijunc.myapplication.R;
import com.lijunc.myapplication.injector.components.ApplicationComponent;
import com.lijunc.myapplication.utils.SwipeRefreshHelper;
import com.lijunc.myapplication.widget.EmptyLayout;
import com.orhanobut.logger.Logger;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lijunc on 2018/1/5.
 */

public abstract class BaseFragment<T extends IBasePresenter> extends RxFragment implements IBaseView {


    private Activity mContext;
    private View mRootView;
    private boolean mIsMulti = false;
    @Nullable
    @BindView(R.id.empty_layout)
    EmptyLayout mEmptyLayout;
    @Nullable
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Inject
    protected T mPresenter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(attachLayoutRes(), null);
            ButterKnife.bind(this, mRootView);
            initInjector();
            initViews();
            initSwipeRefresh();
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getUserVisibleHint() && mRootView != null && !mIsMulti) {
            mIsMulti = true;
            updateViews(false);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser && isVisible() && mRootView != null && !mIsMulti) {
            mIsMulti = true;
            updateViews(false);
        } else {
            super.setUserVisibleHint(isVisibleToUser);
        }
    }

    protected abstract void updateViews(boolean b);


    protected abstract void initViews();

    protected abstract void initInjector();

    protected abstract int attachLayoutRes();


    @Override
    public void showLoading() {
        if(mEmptyLayout!=null){
            mEmptyLayout.setEmptyStatus(EmptyLayout.STATUS_LOADING);
            SwipeRefreshHelper.enableRefresh(mSwipeRefreshLayout,false);
        }
    }

    @Override
    public void hideLoading() {
        if(mEmptyLayout!=null){
            mEmptyLayout.hide();
            SwipeRefreshHelper.enableRefresh(mSwipeRefreshLayout,true);
            SwipeRefreshHelper.controlRefresh(mSwipeRefreshLayout,false);
        }
    }

    @Override
    public void showNetError(EmptyLayout.OnRetryListener onRetryListener) {
        if(mEmptyLayout!=null){
            mEmptyLayout.setEmptyStatus(EmptyLayout.STATUS_NO_NET);
            mEmptyLayout.setRetryListener(onRetryListener);
            SwipeRefreshHelper.enableRefresh(mSwipeRefreshLayout,false);
        }
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.<T>bindToLifecycle();
    }

    @Override
    public void finishRefresh() {
        Logger.w("finishRefresh");
        if(mSwipeRefreshLayout!=null){
            Logger.e("finishRefresh");
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }


    protected ApplicationComponent getAppComponent(){
        return MvpApplication.getAppComponent();
    }

    protected void initToolBar(Toolbar toolbar, boolean homeAsUpEnabled, String title) {
        ((BaseActivity)getActivity()).initToolBar(toolbar, homeAsUpEnabled, title);
    }

    private void initSwipeRefresh(){
        if(mSwipeRefreshLayout!=null){
            SwipeRefreshHelper.init(mSwipeRefreshLayout, new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    updateViews(true);
                }
            });
        }
    }
}
