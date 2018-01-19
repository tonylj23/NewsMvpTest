package com.lijunc.myapplication.module.base;

/**
 * Created by lijunc on 2017/12/27.
 */

public interface IBasePresenter {
    //获取网络数据，更新界面
    //isRefresh表示是否下拉刷新
    void getData(boolean isRefresh);

    //加载更多数据
    void getMoreData();
}
