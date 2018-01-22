package com.lijunc.myapplication.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lijunc.myapplication.R;
import com.lijunc.myapplication.adapter.item.NewsMultiItem;
import com.lijunc.myapplication.api.bean.NewsInfo;
import com.lijunc.myapplication.utils.DefIconFactory;

import java.util.List;

/**
 * Created by Administrator on 2018/1/22.
 */

public class NewsMultiListAdapter extends BaseMultiItemQuickAdapter<NewsMultiItem> {
    public NewsMultiListAdapter(Context context, List<NewsMultiItem> data) {
        super(context, data);
        addItmeType(NewsMultiItem.ITEM_TYPE_NORMAL, R.layout.adapter_news_list);
        addItmeType(NewsMultiItem.ITEM_TYPE_PHOTO_SET, R.layout.adapter_news_photo_set);
    }

    @Override
    protected void addItmeType(int type, int layoutResId) {
        super.addItmeType(type, layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, NewsMultiItem newsMultiItem) {
        switch (newsMultiItem.getItemType()){
            case NewsMultiItem.ITEM_TYPE_NORMAL:
                _handleNewsNormal(baseViewHolder,newsMultiItem.getmNewsBean());
                break;
            case NewsMultiItem.ITEM_TYPE_PHOTO_SET:
                _handleNewsPhotoSet(baseViewHolder,newsMultiItem.getmNewsBean());
                break;
        }
    }

    private void _handleNewsPhotoSet(BaseViewHolder baseViewHolder, NewsInfo newsInfo) {
        ImageView newsIcon = baseViewHolder.convertView.findViewById(R.id.iv_icon);
        Glide.with(mContext).load(newsInfo.getImgsrc()).placeholder(DefIconFactory.provideIcon());
    }

    private void _handleNewsNormal(BaseViewHolder baseViewHolder, NewsInfo newsInfo) {

    }
}
