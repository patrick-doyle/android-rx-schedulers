package com.twistedequations.rx2;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * TestAndroidRxSchedulers for testing rx function chains, forces all tasks to execute on the current thread
 */
public class TestAndroidRxSchedulers implements AndroidRxSchedulers {

  @Override
  public Scheduler network() {
    return Schedulers.trampoline();
  }

  @Override
  public Scheduler io() {
    return Schedulers.trampoline();
  }

  @Override
  public Scheduler computation() {
    return Schedulers.trampoline();
  }

  @Override
  public Scheduler newThread() {
    return Schedulers.trampoline();
  }

  @Override
  public Scheduler immediate() {
    return Schedulers.trampoline();
  }

  @Override
  public Scheduler mainThread() {
    return Schedulers.trampoline();
  }
}
