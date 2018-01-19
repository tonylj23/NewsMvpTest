package com.lijunc.myapplication.engine;

import android.text.TextUtils;

import com.dl7.downloaderlib.DownloadListener;
import com.dl7.downloaderlib.FileDownloader;
import com.dl7.downloaderlib.entity.FileInfo;
import com.dl7.downloaderlib.model.DownloadStatus;
import com.lijunc.myapplication.local.table.VideoInfo;
import com.lijunc.myapplication.local.table.VideoInfoDao;
import com.lijunc.myapplication.rxbus.RxBus;
import com.lijunc.myapplication.rxbus.event.VideoEvent;
import com.lijunc.myapplication.utils.StringUtils;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lijunc on 2017/12/27.
 */

public final class DownloaderWrapper {
    private static List<VideoInfo> mDlVideoList=new ArrayList<>();
    private static RxBus mRxBus;
    private static VideoInfoDao mDbDao;

    public DownloaderWrapper() {
        throw new AssertionError();
    }
    public static void init(RxBus rxBus,VideoInfoDao videoInfoDao){
        mRxBus=rxBus;
        mDbDao=videoInfoDao;
    }

    public static void start(VideoInfo info){
        //真正处理前状态设置为等待
        info.setDownloadSpeed(DownloadStatus.WAIT);
        //优先选择高清
        if(TextUtils.isEmpty(info.getMp4Hd_url())){
            info.setVideoUrl(info.getMp4_url());
        }else{
            info.setVideoUrl(info.getMp4Hd_url());
        }
        mDbDao.insertOrReplace(info);
        mDlVideoList.add(info);
        mRxBus.post(new VideoEvent());
        FileDownloader.start(info.getVideoUrl(), StringUtils.clipFileName(info.getVideoUrl()),new ListenerWrapper());
    }

    //暂停下载
    public static void stop(VideoInfo info){
        FileDownloader.stop(info.getVideoUrl());
    }

    //删除下载完成的
    public static void delete(VideoInfo info){
        String url = FileDownloader.getFilePathByUrl(info.getVideoUrl());
        FileDownloader.cancel(info.getVideoUrl());
        mDbDao.delete(info);
        mRxBus.post(new VideoEvent());
        mDlVideoList.remove(_findAPP(info.getVideoUrl()));
        File file = new File(url);
        if(file.exists()){
            file.delete();
        }
    }

    static class ListenerWrapper implements DownloadListener{

        @Override
        public void onStart(FileInfo fileInfo) {
            _updateVideoInfo(fileInfo);
            mRxBus.post(fileInfo);
        }

        @Override
        public void onUpdate(FileInfo fileInfo) {
            _updateVideoInfo(fileInfo);
            mRxBus.post(fileInfo);
        }

        @Override
        public void onStop(FileInfo fileInfo) {
            _updateVideoInfo(fileInfo);
            mRxBus.post(fileInfo);
            mDlVideoList.remove(_findAPP(fileInfo.getUrl()));
        }

        @Override
        public void onComplete(FileInfo fileInfo) {
            _updateVideoInfo(fileInfo);
            mRxBus.post(fileInfo);
            // 通知 Video 主界面刷新下载数
            mRxBus.post(new VideoEvent());
            Logger.e("onComplete " + fileInfo.toString());
        }

        @Override
        public void onCancel(FileInfo fileInfo) {
            _updateVideoInfo(fileInfo);
            mRxBus.post(fileInfo);
            mDlVideoList.remove(_findAPP(fileInfo.getUrl()));
        }

        @Override
        public void onError(FileInfo fileInfo, String errorMsg) {
            _updateVideoInfo(fileInfo);
            Logger.e(errorMsg);
            mRxBus.post(fileInfo);
        }
    }

    private static void _updateVideoInfo(FileInfo fileInfo){
        VideoInfo info = _findAPP(fileInfo.getUrl());
        if(info!=null){
            if(fileInfo.getTotalBytes()!=0){
                info.setTotalSize(fileInfo.getTotalBytes());
                info.setLoadedSize(fileInfo.getLoadBytes());
                info.setDownloadSpeed(fileInfo.getSpeed());
            }
            info.setDownloadStatus(fileInfo.getStatus());
            mDbDao.update(info);
        }
    }

    //查找APP
    private static VideoInfo _findAPP(String url){
        for(VideoInfo appInfo:mDlVideoList){
            if(appInfo.getVideoUrl().equals(url)){
                return appInfo;
            }
        }
        return null;
    }
}
