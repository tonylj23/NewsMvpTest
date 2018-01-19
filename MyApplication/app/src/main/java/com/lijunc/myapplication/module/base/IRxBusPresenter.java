package com.lijunc.myapplication.module.base;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by lijunc on 2018/1/6.
 */

public interface IRxBusPresenter extends IBasePresenter{

    <T> void registerRxBus(Class<T> eventType, Consumer<T> action);

    void unregisterRxBus();

}
