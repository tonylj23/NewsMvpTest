package com.lijunc.myapplication;

import android.app.Application;
import android.content.Context;

import com.dl7.downloaderlib.DownloadConfig;
import com.dl7.downloaderlib.FileDownloader;
import com.google.gson.stream.MalformedJsonException;
import com.lijunc.myapplication.api.RetrofitService;
import com.lijunc.myapplication.engine.DownloaderWrapper;
import com.lijunc.myapplication.injector.components.ApplicationComponent;
import com.lijunc.myapplication.injector.components.DaggerApplicationComponent;
import com.lijunc.myapplication.injector.modules.ApplicationModule;
import com.lijunc.myapplication.local.dao.NewsTypeDao;
import com.lijunc.myapplication.local.table.DaoMaster;
import com.lijunc.myapplication.local.table.DaoSession;
import com.lijunc.myapplication.rxbus.RxBus;
import com.lijunc.myapplication.utils.DownloadUtils;
import com.lijunc.myapplication.utils.GsonHelper;
import com.lijunc.myapplication.utils.PreferencesUtils;
import com.lijunc.myapplication.utils.ToastUtils;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;

import org.greenrobot.greendao.AbstractDaoMaster;
import org.greenrobot.greendao.database.Database;

import java.io.File;

/**
 * Created by lijunc on 2017/12/25.
 */

public class MvpApplication extends Application{

    private static final String DB_NAME="news-db";
    private static Application mApplication;
    private static Context mContext;
    private DaoSession mDaoSession;
    private RxBus mRxBus=new RxBus();
    private static ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication=this;
        _initDatabase();
        _initInjector();
        _initConfig();
    }
    public static Application getApplication(){
        return mApplication;
    }

    public static ApplicationComponent getAppComponent(){
        return mApplicationComponent;
    }

//    public static Application getApplication(){
//        if(mApplication==null){
//            synchronized (MvpApplication.class){
//                if(mApplication==null){
//                    mApplication=getApplication();
//                }
//            }
//        }
//        return mApplication;
//    }

//    public static Context getContext(){
//        if(mContext==null){
//            synchronized (MvpApplication.class){
//                if(mContext==null){
//                    mContext=getContext();
//                }
//            }
//        }
//        return mContext;
//    }

    private void _initDatabase() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, DB_NAME);
        Database db = helper.getWritableDb();
        mDaoSession = new DaoMaster(db).newSession();
        NewsTypeDao.updateLocalData(this,mDaoSession);
        DownloadUtils.init(mDaoSession.getBeautyPhotoInfoDao());
    }

    private void _initInjector() {
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this, mDaoSession, mRxBus))
                .build();
    }

    private void _initConfig() {
        if(BuildConfig.DEBUG){
            LeakCanary.install(this);
            Logger.init("LogTAG");
        }
        RetrofitService.init();
        ToastUtils.init(this);
        DownloaderWrapper.init(mRxBus,mDaoSession.getVideoInfoDao());
        FileDownloader.init(this);
        DownloadConfig build = new DownloadConfig.Builder()
                .setDownloadDir(PreferencesUtils.getSavePath(this) + File.separator + "video/")
                .build();
        FileDownloader.setConfig(build);

    }
}
