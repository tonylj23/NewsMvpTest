package com.lijunc.myapplication.module.base;

/**
 * Created by Administrator on 2018/1/21.
 */

public interface ILoadDataView<T> extends IBaseView {
    void loadData(T data);

    void loadMoreData(T data);

    void loadNoData();
}
