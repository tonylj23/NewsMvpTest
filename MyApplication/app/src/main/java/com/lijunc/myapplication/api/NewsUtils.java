package com.lijunc.myapplication.api;

import android.support.annotation.NonNull;

import com.lijunc.myapplication.api.bean.NewsInfo;

/**
 * Created by Administrator on 2018/1/21.
 */

public class NewsUtils {
    public NewsUtils() {
        throw new RuntimeException("NewsUtils cannot be initialized");
    }

    private static final int HAS_HEAD=1;
    private static final int NEWS_ID_LENGTH = 16;
    private static final String NEWS_ID_PREFIX = "BV";
    private static final String NEWS_ID_SUFFIX = ".html";

    private static final String NEWS_ITEM_SPECIAL = "special";
    private static final String NEWS_ITEM_PHOTO_SET = "photoset";

    public static boolean isAbNews(@NonNull NewsInfo newsBean) {
        return (newsBean.getHasHead() == HAS_HEAD &&
                newsBean.getAds() != null && newsBean.getAds().size() > 1);
    }

    public static String clipNewsIdFromUrl(String url){
        String newsId=null;
        int index=url.indexOf(NEWS_ID_PREFIX);
        if(index!=-1){
            newsId=url.substring(index,index+NEWS_ID_LENGTH);
        }else if(url.endsWith(NEWS_ID_SUFFIX)){
            index=url.length()-NEWS_ID_SUFFIX.length()-NEWS_ID_LENGTH;
            newsId = url.substring(index, index + NEWS_ID_LENGTH);
        }
        return newsId;
    }

    public static boolean isNewsSpecial(String skipType) {
        return NEWS_ITEM_SPECIAL.equals(skipType);
    }

    public static boolean isNewsPhotoSet(String skipType) {
        return NEWS_ITEM_PHOTO_SET.equals(skipType);
    }
}
