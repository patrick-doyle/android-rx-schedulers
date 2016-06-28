package com.twistedequations.mvl.mock;

import com.twistedequations.mvl.lifecycle.CompleteLifecycle;

public class MockLifecycle implements CompleteLifecycle {

    public boolean onStart = false;
    public boolean onResume = false;
    public boolean onStop = false;
    public boolean onPause = false;
    public boolean onCreate = false;
    public boolean onDestroy = false;

    @Override
    public void onStart() {
        onStart = true;
    }

    @Override
    public void onStop() {
        onStop = true;
    }

    @Override
    public void onResume() {
        onResume = true;
    }

    @Override
    public void onPause() {
        onPause = true;
    }

    @Override
    public void onCreate() {
        onCreate = true;
    }

    @Override
    public void onDestroy() {
        onDestroy = true;
    }
}
