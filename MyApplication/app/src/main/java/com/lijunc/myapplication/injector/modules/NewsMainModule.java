package com.lijunc.myapplication.injector.modules;

import com.lijunc.myapplication.adapter.ViewPagerAdapter;
import com.lijunc.myapplication.injector.PerFragment;
import com.lijunc.myapplication.local.table.DaoSession;
import com.lijunc.myapplication.module.base.IRxBusPresenter;
import com.lijunc.myapplication.module.news.main.NewMainPresenter;
import com.lijunc.myapplication.module.news.main.NewsMainFragment;
import com.lijunc.myapplication.rxbus.RxBus;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lijunc on 2018/1/6.
 */
@Module
public class NewsMainModule {
    private final NewsMainFragment mView;

    public NewsMainModule(NewsMainFragment view) {
        mView = view;
    }
    @PerFragment
    @Provides
    public IRxBusPresenter provideMainPresenter(DaoSession daoSession, RxBus rxBus){
        return new NewMainPresenter(mView,daoSession.getNewsTypeInfoDao(),rxBus);
    }

    @PerFragment
    @Provides
    public ViewPagerAdapter provideViewPagerAdapter(){
        return new ViewPagerAdapter(mView.getChildFragmentManager());
    }
}
