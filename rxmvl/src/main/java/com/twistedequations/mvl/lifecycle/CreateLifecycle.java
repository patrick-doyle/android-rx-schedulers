package com.twistedequations.mvl.lifecycle;

/**
 * Subset of the lifecycle that only receives the onCreate and onDestroy events
 */
public interface CreateLifecycle extends Lifecycle {

    void onCreate();

    void onDestroy();
}
