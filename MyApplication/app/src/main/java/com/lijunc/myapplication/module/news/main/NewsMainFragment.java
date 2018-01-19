package com.lijunc.myapplication.module.news.main;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.lijunc.myapplication.R;
import com.lijunc.myapplication.local.table.NewsTypeInfo;
import com.lijunc.myapplication.module.base.BaseFragment;
import com.lijunc.myapplication.module.base.IRxBusPresenter;

import java.util.List;

import butterknife.BindView;

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


    @Override
    public void loadData(List<NewsTypeInfo> checkList) {

    }

    @Override
    protected void updateViews(boolean b) {

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_news_main;
    }
}
