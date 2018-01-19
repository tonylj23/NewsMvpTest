package com.lijunc.myapplication.injector.components;

import android.content.Context;

import com.lijunc.myapplication.injector.modules.ApplicationModule;
import com.lijunc.myapplication.local.table.DaoSession;
import com.lijunc.myapplication.rxbus.RxBus;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Module;

/**
 * Created by lijunc on 2017/12/25.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    Context getContext();
    RxBus getRxBus();
    DaoSession getDaoSession();
}
