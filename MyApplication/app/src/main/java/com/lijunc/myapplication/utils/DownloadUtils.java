package com.lijunc.myapplication.utils;

import android.bluetooth.le.ScanSettings;
import android.util.SparseBooleanArray;

import com.lijunc.myapplication.local.table.BeautyPhotoInfo;
import com.lijunc.myapplication.local.table.BeautyPhotoInfoDao;

import org.w3c.dom.ls.LSInput;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by lijunc on 2017/12/25.
 */

public class DownloadUtils {

    //记录下载完的图片
    private static SparseBooleanArray sDlPhotos = new SparseBooleanArray();
    //记录下载中的图片
    private static SparseBooleanArray sDoDlPhotos = new SparseBooleanArray();

    public DownloadUtils() {
        throw new RuntimeException("DownloadUtils cannot be initialized!");
    }

    public static void init(BeautyPhotoInfoDao infoDao) {
        List<BeautyPhotoInfo> list = infoDao.queryBuilder().list();
        Observable.fromIterable(list).filter(new Predicate<BeautyPhotoInfo>() {
            @Override
            public boolean test(@NonNull BeautyPhotoInfo info) throws Exception {
                return info.getIsDownload();
            }
        }).subscribe(new Consumer<BeautyPhotoInfo>() {
            @Override
            public void accept(@NonNull BeautyPhotoInfo info) throws Exception {
                sDlPhotos.put(info.getImgsrc().hashCode(),true);
            }
        });
    }


}
