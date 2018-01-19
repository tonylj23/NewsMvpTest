package com.lijunc.myapplication.injector.modules;

import android.content.Context;

import com.lijunc.myapplication.MvpApplication;
import com.lijunc.myapplication.local.table.DaoSession;
import com.lijunc.myapplication.rxbus.RxBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lijunc on 2017/12/25.
 */
@Module
public class ApplicationModule {
    private final MvpApplication mApplication;
    private final DaoSession mDaoSession;
    private final RxBus mRxBus;

    public ApplicationModule(MvpApplication application, DaoSession daoSession, RxBus rxBus) {
        mApplication = application;
        mDaoSession = daoSession;
        mRxBus = rxBus;
    }

    @Provides
    @Singleton
    Context provideApplicationContext(){
        return mApplication.getApplicationContext();
    }

    @Provides
    @Singleton
    RxBus provideRxBus(){
        return mRxBus;
    }

    @Provides
    @Singleton
    DaoSession provideDaoSession(){
        return mDaoSession;
    }
}
