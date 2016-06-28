package com.twistedequations.mvl.lifecycle;

/**
 * Lifecycle that only receives the start, restart and stop events
 */
public interface StartLifecycle extends Lifecycle {

    void onStart();

    void onStop();
}
