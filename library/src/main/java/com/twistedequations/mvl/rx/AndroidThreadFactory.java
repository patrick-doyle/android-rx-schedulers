package com.twistedequations.mvl.rx;

import android.os.Process;
import android.support.annotation.NonNull;

import java.util.Locale;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class AndroidThreadFactory implements ThreadFactory {

  private final AtomicInteger threadCounter = new AtomicInteger(0);
  private final String name;

  public AndroidThreadFactory(String name) {
    this.name = name;
  }

  @Override
  public Thread newThread(@NonNull Runnable runnable) {
    Thread thread = new Thread(runnable);
    thread.setName(String.format(Locale.ENGLISH, "android-" +name + "-thread-%s", threadCounter.incrementAndGet()));
    thread.setPriority(Process.THREAD_PRIORITY_BACKGROUND);
    return thread;
  }
}
