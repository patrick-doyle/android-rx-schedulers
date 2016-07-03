package com.twistedequations.mvl.lifecycle;


/**
 * Lifecycle that only receives the resume, and pause events
 */
public interface ResumeLifecycle extends Lifecycle {

    void onResume();

    void onPause();
}
