package com.twistedequations.mvl.lifecycle;

/**
 * Lifecycle that receives the low memory and trim memory callbacks
 */
public interface MemoryLifecycle extends Lifecycle {

    void onLowMemory();

    void onTrimMemory(int level);
}
