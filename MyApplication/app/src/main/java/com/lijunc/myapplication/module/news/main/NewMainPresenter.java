package com.lijunc.myapplication.module.news.main;

import android.support.annotation.MainThread;

import com.lijunc.myapplication.local.table.NewsTypeInfo;
import com.lijunc.myapplication.local.table.NewsTypeInfoDao;
import com.lijunc.myapplication.module.base.IRxBusPresenter;
import com.lijunc.myapplication.rxbus.RxBus;
import com.orhanobut.logger.Logger;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by lijunc on 2018/1/6.
 */

public class NewMainPresenter implements IRxBusPresenter {

    private final INewsmainView mView;
    private final NewsTypeInfoDao mDbDao;
    private final RxBus mRxBus;

    public NewMainPresenter(INewsmainView view, NewsTypeInfoDao dbDao, RxBus rxBus) {
        mView = view;
        mDbDao = dbDao;
        mRxBus = rxBus;
    }

    @Override
    public void getData(boolean isRefresh) {
        List<NewsTypeInfo> list = mDbDao.queryBuilder().list();
        Observable.just(list).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<NewsTypeInfo>>() {
                    @Override
                    public void accept(@NonNull List<NewsTypeInfo> infos) throws Exception {
                        mView.loadData(infos);
                    }
                });

    }

    @Override
    public void getMoreData() {

    }

    @Override
    public <T> void registerRxBus(Class<T> eventType, Consumer<T> action) {
        Disposable disposable = mRxBus.doSubscribe(eventType, action, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                Logger.e(throwable.toString());
            }
        });
        mRxBus.addSubscription(this,disposable);
    }

    @Override
    public void unregisterRxBus() {
        mRxBus.unSubscribe(this);
    }
}
