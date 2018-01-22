package com.lijunc.myapplication.module.news.main;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.lijunc.myapplication.R;
import com.lijunc.myapplication.adapter.ViewPagerAdapter;
import com.lijunc.myapplication.injector.components.DaggerNewsMainComponent;
import com.lijunc.myapplication.injector.modules.NewsMainModule;
import com.lijunc.myapplication.local.table.NewsTypeInfo;
import com.lijunc.myapplication.module.base.BaseFragment;
import com.lijunc.myapplication.module.base.IRxBusPresenter;
import com.lijunc.myapplication.rxbus.event.ChannelEvent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * Created by lijunc on 2018/1/5.
 */

public class NewsMainFragment extends BaseFragment<IRxBusPresenter> implements INewsmainView {
    @BindView(R.id.tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @Inject
    ViewPagerAdapter mPagerAdapter;

    @Override
    public void loadData(List<NewsTypeInfo> checkList) {
        List<Fragment> fragments = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        for(NewsTypeInfo bean:checkList){
            titles.add(bean.getName());
            fragments.add()
        }

    }

    @Override
    protected void updateViews(boolean b) {

    }

    @Override
    protected void initViews() {
        initToolBar(mToolBar,true,"新闻");
        setHasOptionsMenu(true);
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mPresenter.registerRxBus(ChannelEvent.class, new Consumer<ChannelEvent>() {
            @Override
            public void accept(ChannelEvent channelEvent) throws Exception {
                _handleChannelEvent(channelEvent);
            }
        });
    }

    private void _handleChannelEvent(ChannelEvent channelEvent) {
        switch (channelEvent.eventType){
            case ChannelEvent.ADD_EVENT:

                break;
            case ChannelEvent.DEL_EVENT:
                mViewPager.setCurrentItem(0);
                mPagerAdapter.delItem(channelEvent.newsInfo.getName());
                break;
            case ChannelEvent.SWAP_EVENT:
                mPagerAdapter.swipItems(channelEvent.fromPos,channelEvent.toPos);
                break;
        }
    }

    @Override
    protected void initInjector() {
        DaggerNewsMainComponent.builder()
                .applicationComponent(getAppComponent())
                .newsMainModule(new NewsMainModule(this))
                .build()
                .inject(this);

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_news_main;
    }
}
