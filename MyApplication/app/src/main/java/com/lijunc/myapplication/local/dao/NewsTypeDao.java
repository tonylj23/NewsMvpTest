package com.lijunc.myapplication.local.dao;

import android.content.Context;

import com.flyco.tablayout.widget.MsgView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.lijunc.myapplication.local.table.DaoSession;
import com.lijunc.myapplication.local.table.NewsTypeInfo;
import com.lijunc.myapplication.local.table.NewsTypeInfoDao;
import com.lijunc.myapplication.utils.AssetsHelper;
import com.lijunc.myapplication.utils.GsonHelper;

import java.util.List;

/**
 * Created by lijunc on 2017/12/25.
 */

public class NewsTypeDao {
    private static List<NewsTypeInfo> mAllChannels;

    public NewsTypeDao() {
    }

    public static void updateLocalData(Context context, DaoSession daoSession){
        String newsChannel = AssetsHelper.readData(context, "NewsChannel");
        mAllChannels = GsonHelper.convertEntity(newsChannel, NewsTypeInfo.class);
        NewsTypeInfoDao newsTypeInfoDao = daoSession.getNewsTypeInfoDao();
        if(newsTypeInfoDao.count()==0){
            newsTypeInfoDao.insertInTx(mAllChannels.subList(0,3));
        }
    }

    public static List<NewsTypeInfo> getmAllChannels(){
        return mAllChannels;
    }
}
