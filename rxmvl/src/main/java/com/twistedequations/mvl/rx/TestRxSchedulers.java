package com.twistedequations.mvl.rx;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * TestRxSchedulers for testing rx function chains,
 * forces all Schedulers to execute on the current thread
 */
public class TestRxSchedulers implements MVLSchedulers {

  @Override
  public Scheduler network() {
    return Schedulers.immediate();
  }

  @Override
  public Scheduler io() {
    return Schedulers.immediate();
  }

  @Override
  public Scheduler computation() {
    return Schedulers.immediate();
  }

  @Override
  public Scheduler immediate() {
    return Schedulers.immediate();
  }

  @Override
  public Scheduler mainThread() {
    return Schedulers.immediate();
  }
}
