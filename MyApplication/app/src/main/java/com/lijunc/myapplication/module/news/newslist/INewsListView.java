package com.lijunc.myapplication.module.news.newslist;

import com.lijunc.myapplication.adapter.item.NewsMultiItem;
import com.lijunc.myapplication.api.bean.NewsInfo;
import com.lijunc.myapplication.module.base.ILoadDataView;

import java.util.List;

/**
 * Created by Administrator on 2018/1/21.
 */

public interface INewsListView extends ILoadDataView<List<NewsMultiItem>> {
    void loadAdData(NewsInfo newsBean);
}
