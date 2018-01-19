package com.lijunc.myapplication.module.news.main;

import com.lijunc.myapplication.local.table.NewsTypeInfo;

import java.util.List;

/**
 * Created by lijunc on 2018/1/6.
 */

public interface INewsmainView {
    void loadData(List<NewsTypeInfo> checkList);
}
