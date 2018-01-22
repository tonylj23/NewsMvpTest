package com.lijunc.myapplication.injector.modules;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lijunc.myapplication.injector.PerFragment;
import com.lijunc.myapplication.module.base.IBasePresenter;
import com.lijunc.myapplication.module.news.newslist.NewsListFragment;
import com.lijunc.myapplication.module.news.newslist.NewsListPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2018/1/21.
 */
@Module
public class NewsListModule {
    private final NewsListFragment mNewsListView;
    private final String mNewsId;

    public NewsListModule(NewsListFragment mNewsListView, String mNewsId) {
        this.mNewsListView = mNewsListView;
        this.mNewsId = mNewsId;
    }

    @PerFragment
    @Provides
    public IBasePresenter providePresenter(){
        return new NewsListPresenter(mNewsListView,mNewsId);
    }

    @PerFragment
    @Provides
    public BaseQuickAdapter providerAdapter(){
        return new
    }
}
