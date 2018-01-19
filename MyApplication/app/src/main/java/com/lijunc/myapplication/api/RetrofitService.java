package com.lijunc.myapplication.api;

import android.app.Application;
import android.support.annotation.NonNull;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.lijunc.myapplication.MvpApplication;
import com.lijunc.myapplication.api.bean.NewsInfo;
import com.lijunc.myapplication.api.bean.SpecialInfo;
import com.lijunc.myapplication.injector.components.ApplicationComponent;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lijunc on 2017/12/27.
 */

public class RetrofitService {

    private static final String NEW_HOST="http://c.3g.163.com/";

    private static final String HEAD_LINE_NEWS = "T1348647909107";

    //查询网络的Cache-Control设置
    //如果请求在a时刻返回响应结果，则在max-age规定的时间内，浏览器不会发送请求，消息从缓存中获取
    public static final String CACHE_CONTROL_NETWORK="Cache-Control: public, max-age=3600";
    // 避免出现 HTTP 403 Forbidden，参考：http://stackoverflow.com/questions/13670692/403-forbidden-with-java-but-not-web-browser
    static final String AVOID_HTTP403_FORBIDDEN = "User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11";
    private static INewApi mNewsService;
    private static final String WELFARE_HOST = "http://gank.io/";
    private static IWelfareApi mWelfareService;
    private static final int INCREASE_PAGE = 20;


    private RetrofitService() {
        throw new AssertionError();
    }

    public static void init() {
        Cache cache = new Cache(new File(MvpApplication.getApplication().getCacheDir(), "HttpCache")
                , 1024 * 1024 * 100);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().cache(cache)
                .retryOnConnectionFailure(true)
                .addInterceptor(mLoggingInterceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder().client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(NEW_HOST)
                .build();
        mNewsService = retrofit.create(INewApi.class);

        retrofit = new Retrofit.Builder().client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(NEW_HOST)
                .build();
        mWelfareService = retrofit.create(IWelfareApi.class);
    }

    private static final Interceptor mRWriteCacheControInterceptor=new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            return null;
        }
    };


    private static final Interceptor mLoggingInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Buffer requestBuffer = new Buffer();
            if (request.body() != null) {
                request.body().writeTo(requestBuffer);
            } else {
                Logger.d("LogTAG", "request.body()==null");
            }
            Logger.w(request.url() + (request.body() != null ? "?" + _parseParams(request.body(), requestBuffer) : ""));
            Response response = chain.proceed(request);
            return response;
        }
    };

    @NonNull
    private static String _parseParams(RequestBody body, Buffer buffer)
            throws UnsupportedEncodingException {
        if(body.contentType()!=null&&!body.contentType().toString().contains("multipart")){
            return URLDecoder.decode(buffer.readUtf8(),"UTF-8");
        }
        return "null";
    }


    //获取新闻消息
    public static Observable<NewsInfo> getNewsList(final String newsId, int page){
        String type;
        if(newsId.equals(HEAD_LINE_NEWS)){
            type = "headline";
        } else {
            type = "list";
        }
        return mNewsService.getNewsList(type,newsId,page*INCREASE_PAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<Map<String, List<NewsInfo>>, Observable<NewsInfo>>() {
                    @Override
                    public Observable<NewsInfo> apply(@io.reactivex.annotations.NonNull Map<String, List<NewsInfo>> map) throws Exception {
                        return Observable.fromIterable(map.get(newsId));
                    }
                });
    }

    //获取专题数据
    public static Observable<SpecialInfo> getSpecialList(final String specialId){
        return mNewsService.getSpecial(specialId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<Map<String, SpecialInfo>, Observable<SpecialInfo>>() {
                    @Override
                    public Observable<SpecialInfo> apply(@io.reactivex.annotations.NonNull Map<String, SpecialInfo> map) throws Exception {
                        return Observable.just(map.get(specialId));
                    }
                });
    }

}
