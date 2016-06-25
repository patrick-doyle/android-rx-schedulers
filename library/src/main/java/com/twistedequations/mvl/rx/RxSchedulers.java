package com.twistedequations.mvl.rx;


import rx.Scheduler;

public interface RxSchedulers {

  Scheduler network();

  Scheduler io();

  Scheduler computation();

  Scheduler immediate();

  Scheduler mainThread();
}
