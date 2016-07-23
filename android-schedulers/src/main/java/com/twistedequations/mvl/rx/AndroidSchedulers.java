package com.twistedequations.mvl.rx;

import rx.Scheduler;


public interface AndroidSchedulers {


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
