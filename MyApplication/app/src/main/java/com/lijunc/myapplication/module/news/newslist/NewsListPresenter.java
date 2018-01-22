package com.lijunc.myapplication.module.news.newslist;

import com.lijunc.myapplication.adapter.item.NewsMultiItem;
import com.lijunc.myapplication.api.NewsUtils;
import com.lijunc.myapplication.api.RetrofitService;
import com.lijunc.myapplication.api.bean.NewsInfo;
import com.lijunc.myapplication.module.base.IBasePresenter;

import java.util.List;


import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * Created by Administrator on 2018/1/21.
 */

public class NewsListPresenter implements IBasePresenter {

    private INewsListView mView;
    private String mNewsId;

    private int mPage = 0;

    public NewsListPresenter(INewsListView mView, String mNewsId) {
        this.mView = mView;
        this.mNewsId = mNewsId;
    }

    @Override
    public void getData(final boolean isRefresh) {
        RetrofitService.getNewsList(mNewsId, mPage).map(new Function<NewsInfo, NewsMultiItem>() {
            @Override
            public NewsMultiItem apply(NewsInfo newsInfo) throws Exception {
                if (NewsUtils.isNewsPhotoSet(newsInfo.getSkipType())) {
                    return new NewsMultiItem(NewsMultiItem.ITEM_TYPE_PHOTO_SET, newsInfo);
                }
                return new NewsMultiItem(NewsMultiItem.ITEM_TYPE_NORMAL, newsInfo);
            }
        }).toList().compose(mView.<List<NewsMultiItem>>bindToLife())
                .subscribe(new SingleObserver<List<NewsMultiItem>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<NewsMultiItem> newsMultiItems) {
                        mView.loadMoreData(newsMultiItems);
                        mPage++;
                    }

                    @Override
                    public void onError(Throwable e) {
                        com.orhanobut.logger.Logger.e(e.toString());
                        mView.loadNoData();
                    }
                });

    }

    @Override
    public void getMoreData() {
        RetrofitService.getNewsList(mNewsId, mPage).map(new Function<NewsInfo, NewsMultiItem>() {
            @Override
            public NewsMultiItem apply(NewsInfo newsInfo) throws Exception {
                if (NewsUtils.isNewsPhotoSet(newsInfo.getSkipType())) {
                    return new NewsMultiItem(NewsMultiItem.ITEM_TYPE_PHOTO_SET, newsInfo);
                }
                return new NewsMultiItem(NewsMultiItem.ITEM_TYPE_NORMAL, newsInfo);
            }
        }).toList().compose(mView.<List<NewsMultiItem>>bindToLife())
                .subscribe(new SingleObserver<List<NewsMultiItem>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<NewsMultiItem> newsMultiItems) {
                        mView.loadMoreData(newsMultiItems);
                        mPage++;
                    }

                    @Override
                    public void onError(Throwable e) {
                        com.orhanobut.logger.Logger.e(e.toString());
                        mView.loadNoData();
                    }
                });
    }
}
