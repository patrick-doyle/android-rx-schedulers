package com.twistedequations.mvl.rx;

import com.twistedequations.mvl.rx.internal.AndroidThreadFactory;

import java.util.concurrent.Executors;

import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Android implementation of AndroidSchedulers. This uses a custom thread factory and thread pools
 * for greater efficiency of Android limited resources such as bandwidth
 *
 * Network - Limited to 4 threads, supports Priority
 * IO - All non network IO, Capped at 6 threads
 * Computation - Limited CPU core count, at least 2 threads
 * Immediate - On the current thread
 */
public class DefaultAndroidSchedulers implements AndroidSchedulers {

  //Limit the threads to prevent cpu thread overloading and to help with context switching
  private static final int PROCESSOR_THREADS = Math.min(2, Runtime.getRuntime().availableProcessors()); //Limit to availableProcessors cores
  private static final int NETWORK_THREADS = 4; //Prevent network overloading, number taken from volley library thread count
  private static final int IO_THREADS = 6; //Prevent overloading the disk IO

  private static final Scheduler NETWORK_EXECUTOR = Schedulers.from(Executors.newFixedThreadPool(NETWORK_THREADS, new AndroidThreadFactory("network")));
  private static final Scheduler IO_SCHEDULER = Schedulers.from(Executors.newFixedThreadPool(IO_THREADS, new AndroidThreadFactory("i/o")));
  private static final Scheduler COMPUTATION_SCHEDULER = Schedulers.from(Executors.newFixedThreadPool(PROCESSOR_THREADS, new AndroidThreadFactory("computation")));

  @Override
  public Scheduler network() {
    return NETWORK_EXECUTOR;
  }

  @Override
  public Scheduler io() {
    return IO_SCHEDULER;
  }

  @Override
  public Scheduler computation() {
    return COMPUTATION_SCHEDULER;
  }

  @Override
  public Scheduler newThread() {
    return Schedulers.from(Executors.newSingleThreadExecutor(new AndroidThreadFactory("newThread")));
  }

  @Override
  public Scheduler immediate() {
    return Schedulers.from(Executors.newFixedThreadPool(NETWORK_THREADS, new AndroidThreadFactory("network")));
  }

  @Override
  public Scheduler mainThread() {
    return rx.android.schedulers.AndroidSchedulers.mainThread();
  }
}
