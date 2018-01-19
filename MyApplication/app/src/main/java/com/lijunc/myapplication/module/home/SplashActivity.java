package com.lijunc.myapplication.module.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.lijunc.myapplication.MainActivity;
import com.lijunc.myapplication.R;
import com.lijunc.myapplication.module.base.BaseActivity;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lijunc on 2017/12/27.
 */

public class SplashActivity extends BaseActivity{

    private final static int MAX_VALUE=5;
    @BindView(R.id.sp_tv)
    TextView spTextView;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void updateViews(boolean isRefresh) {
        RxView.clicks(spTextView).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                    }
                });
        Observable.interval(1,TimeUnit.SECONDS,AndroidSchedulers.mainThread()).take(5)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Long aLong) {
                        try {
                            RxTextView.text(spTextView).accept("跳过 "+(5-aLong));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }



                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                    }
                });
    }

}
