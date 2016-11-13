package com.twistedequations.rx;

import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * TestAndroidRxSchedulers for testing rx function chains, forces all tasks to execute on the current thread
 */
public class TestAndroidRxSchedulers implements AndroidRxSchedulers {

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
  public Scheduler newThread() {
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
