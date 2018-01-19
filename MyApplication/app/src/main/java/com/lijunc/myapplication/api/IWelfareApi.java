package com.lijunc.myapplication.api;

import com.lijunc.myapplication.api.bean.WelfarePhotoList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

import static com.lijunc.myapplication.api.RetrofitService.CACHE_CONTROL_NETWORK;

/**
 * Created by lijunc on 2017/12/27.
 */

public interface IWelfareApi {

    @Headers(CACHE_CONTROL_NETWORK)
    @GET("/api/data/福利/10/{page}")
    Observable<WelfarePhotoList> getWelfarePhoto(@Path("page") int page);

}
