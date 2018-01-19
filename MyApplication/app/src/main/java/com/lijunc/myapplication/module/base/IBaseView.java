package com.lijunc.myapplication.module.base;

import com.lijunc.myapplication.widget.EmptyLayout;
import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * Created by lijunc on 2017/12/27.
 */

public interface IBaseView {
    //显示加载动画
    void showLoading();

    void hideLoading();

    void showNetError(EmptyLayout.OnRetryListener onRetryListener);

    <T> LifecycleTransformer<T> bindToLife();

    void finishRefresh();
}
