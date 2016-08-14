package com.twistedequations.mvl.rx;

import com.twistedequations.mvl.rx.internal.AndroidRxSchedulersHook;

import rx.plugins.RxJavaPlugins;

public class AndroidRxPlugin {

    public static void applyRxJavaPlugins() {
        applyRxJavaPlugins(new DefaultAndroidRxSchedulers());
    }

    public static void applyRxJavaPlugins(AndroidRxSchedulers androidRxSchedulers) {
        RxJavaPlugins.getInstance().registerSchedulersHook(new AndroidRxSchedulersHook(androidRxSchedulers));
    }
}
