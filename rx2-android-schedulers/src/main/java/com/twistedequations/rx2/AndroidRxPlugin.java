package com.twistedequations.rx2;

import io.reactivex.plugins.RxJavaPlugins;

public class AndroidRxPlugin {

    public static void applyDefaultAndoridRxJavaPlugins() {
        applyRxJavaPlugins(new DefaultAndroidRxSchedulers());
    }

    public static void applyTestRxJavaPlugins() {
        applyRxJavaPlugins(new TestAndroidRxSchedulers());
    }

    public static void applyDefaultRxSchedulers() {
        RxJavaPlugins.reset();
    }

    public static void applyRxJavaPlugins(AndroidRxSchedulers androidRxSchedulers) {
        RxJavaPlugins.onComputationScheduler(androidRxSchedulers.computation());
        RxJavaPlugins.onIoScheduler(androidRxSchedulers.io());
        RxJavaPlugins.onNewThreadScheduler(androidRxSchedulers.newThread());
        RxJavaPlugins.onSingleScheduler(androidRxSchedulers.immediate());
    }
}
