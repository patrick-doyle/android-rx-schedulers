package com.twistedequations.reddit.rsvp.rx;

import rx.Scheduler;

public interface RxSchedulers {

  Scheduler network();

  Scheduler io();

  Scheduler computation();

  Scheduler immediate();

  Scheduler mainThread();
}
