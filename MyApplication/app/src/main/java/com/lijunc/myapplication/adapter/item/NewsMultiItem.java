package com.lijunc.myapplication.adapter.item;

import android.support.annotation.IntDef;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.lijunc.myapplication.api.bean.NewsInfo;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Administrator on 2018/1/21.
 */

public class NewsMultiItem extends MultiItemEntity {
    public static final int ITEM_TYPE_NORMAL = 1;
    public static final int ITEM_TYPE_PHOTO_SET = 2;

    private NewsInfo mNewsBean;

    public NewsMultiItem(@NewsItemType int itemType,NewsInfo mNewsBean) {
        this.mNewsBean = mNewsBean;
        setItemType(itemType);
    }

    public NewsInfo getmNewsBean(){
        return mNewsBean;
    }

    @Override
    public void setItemType(@NewsItemType int itemType) {
        super.setItemType(itemType);
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({ITEM_TYPE_NORMAL,ITEM_TYPE_PHOTO_SET})
    public @interface NewsItemType{}
}
