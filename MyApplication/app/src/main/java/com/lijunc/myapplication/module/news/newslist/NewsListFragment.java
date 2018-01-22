package com.lijunc.myapplication.module.news.newslist;

import com.lijunc.myapplication.R;
import com.lijunc.myapplication.adapter.item.NewsMultiItem;
import com.lijunc.myapplication.api.bean.NewsInfo;
import com.lijunc.myapplication.module.base.BaseFragment;
import com.lijunc.myapplication.module.base.IBasePresenter;

import java.util.List;

/**
 * Created by Administrator on 2018/1/21.
 */

public class NewsListFragment extends BaseFragment<IBasePresenter> implements INewsListView{
    @Override
    public void loadData(List<NewsMultiItem> data) {

    }

    @Override
    public void loadMoreData(List<NewsMultiItem> data) {

    }

    @Override
    public void loadNoData() {

    }

    @Override
    public void loadAdData(NewsInfo newsBean) {

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
        return R.layout.fragment_news_list;
    }
}
