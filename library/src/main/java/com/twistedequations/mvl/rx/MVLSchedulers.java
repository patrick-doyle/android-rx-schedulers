package com.twistedequations.mvl.rx;


import rx.Scheduler;

public interface MVLSchedulers {

  Scheduler network();

  Scheduler io();

  Scheduler computation();

  Scheduler immediate();

  Scheduler mainThread();
}
