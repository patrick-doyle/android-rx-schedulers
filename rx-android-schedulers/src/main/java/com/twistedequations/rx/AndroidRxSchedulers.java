package com.twistedequations.rx;

import rx.Scheduler;


public interface AndroidRxSchedulers {


  Scheduler network();

  /**
   * Io thread for all non network io
   */
  Scheduler io();

  /**
   * computation thread
   */
  Scheduler computation();

  Scheduler newThread();

  Scheduler immediate();

  Scheduler mainThread();
}
