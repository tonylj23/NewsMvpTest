package com.lijunc.myapplication.utils;

import android.content.IntentSender;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;

/**
 * Created by lijunc on 2018/1/4.
 */

public class SwipeRefreshHelper {
    private SwipeRefreshHelper() {
        throw new AssertionError();
    }

    /**
     * 初始化，关联AppBarLayout,处理滑动冲突
     */
    public static void init(final SwipeRefreshLayout refreshLayout, AppBarLayout appBarLayout,
                            SwipeRefreshLayout.OnRefreshListener listener) {
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        refreshLayout.setOnRefreshListener(listener);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset >= 0) {
                    refreshLayout.setEnabled(true);
                } else {
                    refreshLayout.setEnabled(false);
                }
            }
        });
    }

    /**
     * 初始化
     */
    public static void init(SwipeRefreshLayout refreshLayout, SwipeRefreshLayout.OnRefreshListener listener) {
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        refreshLayout.setOnRefreshListener(listener);
    }

    public static void enableRefresh(SwipeRefreshLayout refreshLayout,boolean isRefresh){
        if(refreshLayout!=null){
            refreshLayout.setEnabled(isRefresh);
        }
    }

    /**
     * 控制刷新
     */
    public static void controlRefresh(SwipeRefreshLayout refreshLayout,boolean isRefresh){
        if(refreshLayout!=null){
            if(isRefresh!=refreshLayout.isRefreshing()){
                refreshLayout.setRefreshing(isRefresh);
            }
        }
    }

}
