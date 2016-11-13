package com.twistedequations.rx2;


import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Same schedulers as standard RxJava and RxAndroid
 */
public class DefaultRxSchedulers implements AndroidRxSchedulers {

    @Override
    public Scheduler network() {
        return Schedulers.io();
    }

    @Override
    public Scheduler io() {
        return Schedulers.io();
    }

    @Override
    public Scheduler computation() {
        return Schedulers.computation();
    }

    @Override
    public Scheduler newThread() {
        return Schedulers.newThread();
    }

    @Override
    public Scheduler immediate() {
        return Schedulers.trampoline();
    }

    @Override
    public Scheduler mainThread() {
        return AndroidSchedulers.mainThread();
    }
}
