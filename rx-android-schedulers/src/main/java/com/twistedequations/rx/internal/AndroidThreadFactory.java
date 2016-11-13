package com.twistedequations.rx.internal;

import android.os.Process;
import android.support.annotation.NonNull;

import java.util.Locale;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Thread Factory for naming threads in a manageable way and for setting the
 * correct priority on the threads to make them background on andorid
 */
public class AndroidThreadFactory implements ThreadFactory {

  private final AtomicInteger threadCounter = new AtomicInteger(0);
  private final String name;

  public AndroidThreadFactory(String name) {
    this.name = name;
  }

  @Override
  public Thread newThread(@NonNull Runnable runnable) {
    Thread thread = new Thread(runnable);
    thread.setName(String.format(Locale.ENGLISH, "tx-%s-thread-%d", name, threadCounter.incrementAndGet()));
    thread.setPriority(Process.THREAD_PRIORITY_BACKGROUND);
    return thread;
  }
}
