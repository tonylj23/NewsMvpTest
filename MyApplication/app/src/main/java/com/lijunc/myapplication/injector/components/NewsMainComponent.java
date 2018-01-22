package com.lijunc.myapplication.injector.components;

import com.lijunc.myapplication.injector.PerFragment;
import com.lijunc.myapplication.injector.modules.NewsMainModule;
import com.lijunc.myapplication.module.news.main.NewsMainFragment;

import dagger.Component;

/**
 * Created by Administrator on 2018/1/21.
 */
@PerFragment
@Component(dependencies = ApplicationComponent.class,modules = NewsMainModule.class)
public interface NewsMainComponent {
    void inject(NewsMainFragment fragment);
}
