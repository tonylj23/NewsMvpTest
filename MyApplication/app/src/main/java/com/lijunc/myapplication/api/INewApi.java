package com.lijunc.myapplication.api;

import com.lijunc.myapplication.api.bean.NewsDetailInfo;
import com.lijunc.myapplication.api.bean.NewsInfo;
import com.lijunc.myapplication.api.bean.PhotoInfo;
import com.lijunc.myapplication.api.bean.PhotoSetInfo;
import com.lijunc.myapplication.api.bean.SpecialInfo;
import com.lijunc.myapplication.local.table.BeautyPhotoInfo;
import com.lijunc.myapplication.local.table.VideoInfo;

import java.util.List;
import java.util.Map;

import dagger.Provides;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.lijunc.myapplication.api.RetrofitService.AVOID_HTTP403_FORBIDDEN;
import static com.lijunc.myapplication.api.RetrofitService.CACHE_CONTROL_NETWORK;

/**
 * Created by lijunc on 2017/12/27.
 */

public interface INewApi {
    //获取新闻列表
    //eg: http://c.m.163.com/nc/article/headline/T1348647909107/60-20.html
    @Headers(CACHE_CONTROL_NETWORK)
    @GET("nc/article/{type}/{id}/{startPage}-20.html")
    Observable<Map<String,List<NewsInfo>>> getNewsList(@Path("type") String type,
                                                       @Path("id") String id,
                                                       @Path("startPage") int startPage);

    //获取专题
    @Headers(CACHE_CONTROL_NETWORK)
    @GET("nc/special/{specialId}.html")
    Observable<Map<String,SpecialInfo>> getSpecial(@Path("specialId") String specialIde);

    @Headers(AVOID_HTTP403_FORBIDDEN)
    @GET("nc/article/{newsId}/full.html")
    Observable<Map<String, NewsDetailInfo>> getNewsDetail(@Path("newsId") String newsId);
    @Headers(CACHE_CONTROL_NETWORK)
    @GET("photo/api/set/{photoId}.json")
    Observable<PhotoSetInfo> getPhotoSet(@Path("photoId") String photoId);

    /**
     * 获取图片列表
     * eg: http://c.3g.163.com/photo/api/list/0096/4GJ60096.json
     *
     * @return
     */
    @Headers(CACHE_CONTROL_NETWORK)
    @GET("photo/api/list/0096/4GJ60096.json")
    Observable<List<PhotoInfo>> getPhotoList();

    /**
     * 获取更多图片列表
     * eg: http://c.3g.163.com/photo/api/morelist/0096/4GJ60096/106571.json
     *
     * @return
     */
    @Headers(CACHE_CONTROL_NETWORK)
    @GET("photo/api/morelist/0096/4GJ60096/{setId}.json")
    Observable<List<PhotoInfo>> getPhotoMoreList(@Path("setId") String setId);

    /**
     * 获取美女图片，这个API不完整，省略了好多参数
     * eg: http://c.3g.163.com/recommend/getChanListNews?channel=T1456112189138&size=20&offset=0
     *
     * @param offset 起始页码
     * @return
     */
    @Headers(CACHE_CONTROL_NETWORK)
    @GET("recommend/getChanListNews?channel=T1456112189138&size=20")
    Observable<Map<String, List<BeautyPhotoInfo>>> getBeautyPhoto(@Query("offset") int offset);

    /**
     * 获取视频列表
     * eg: http://c.3g.163.com/nc/video/list/V9LG4B3A0/n/10-10.html
     *
     * @param id  video ID
     * @param startPage 起始页码
     * @return
     */
    @Headers(AVOID_HTTP403_FORBIDDEN)
    @GET("nc/video/list/{id}/n/{startPage}-10.html")
    Observable<Map<String, List<VideoInfo>>> getVideoList(@Path("id") String id,
                                                          @Path("startPage") int startPage);
}
