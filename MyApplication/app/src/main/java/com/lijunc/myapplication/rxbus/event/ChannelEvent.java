package com.lijunc.myapplication.rxbus.event;

import com.lijunc.myapplication.local.table.NewsTypeInfo;

import org.w3c.dom.Element;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Administrator on 2018/1/21.
 */

public class ChannelEvent {
    public static final int ADD_EVENT=301;
    public static final int DEL_EVENT=302;
    public static final int SWAP_EVENT=303;

    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.PARAMETER)
    public @interface ChanelEventType{}

    public int eventType;
    public NewsTypeInfo newsInfo;
    public int fromPos=-1;
    public int toPos=-1;

    public ChannelEvent(@ChanelEventType int eventType, NewsTypeInfo newsInfo) {
        this.eventType = eventType;
        this.newsInfo = newsInfo;
    }

    public ChannelEvent(@ChanelEventType int eventType, int fromPos, int toPos) {
        this.eventType = eventType;
        this.fromPos = fromPos;
        this.toPos = toPos;
    }
}
