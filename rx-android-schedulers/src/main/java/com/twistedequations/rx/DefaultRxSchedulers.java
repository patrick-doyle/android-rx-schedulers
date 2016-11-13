package com.twistedequations.rx;

import com.twistedequations.rx.AndroidRxSchedulers;

import rx.Scheduler;
import rx.schedulers.Schedulers;

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
    return Schedulers.immediate();
  }

  @Override
  public Scheduler mainThread() {
    return rx.android.schedulers.AndroidSchedulers.mainThread();
  }
}
