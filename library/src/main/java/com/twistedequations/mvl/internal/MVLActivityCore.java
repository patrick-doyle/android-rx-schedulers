package com.twistedequations.mvl.internal;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.twistedequations.mvl.MVLActivity;
import com.twistedequations.mvl.lifecycle.CreateLifecycle;
import com.twistedequations.mvl.lifecycle.Lifecycle;
import com.twistedequations.mvl.lifecycle.MemoryLifecycle;
import com.twistedequations.mvl.lifecycle.ResumeLifecycle;
import com.twistedequations.mvl.lifecycle.StartLifecycle;

import java.util.HashSet;
import java.util.Set;

import rx.Observable;
import rx.functions.Action1;
import rx.subjects.ReplaySubject;

public class MVLActivityCore implements MVLActivity {
    
    private final MVLActivity mvlActivity;
    private final Bundle state = new Bundle();
    private final ReplaySubject<Bundle> prevStateSubject = ReplaySubject.create(1);
    private final Set<Lifecycle> lifecycles = new HashSet<>();

    public MVLActivityCore(MVLActivity mvlActivity) {
        this.mvlActivity = mvlActivity;
    }

    @Override
    public Observable<Bundle> getPrevState() {
        return prevStateSubject.take(1);
    }

    @Override
    public void updateSaveState(Action1<Bundle> updateFunction) {
        updateFunction.call(state);
    }

    @Override
    public Set<Lifecycle> getLifecycles() {
        return lifecycles;
    }

    @Override
    public boolean registerLifecycle(Lifecycle lifecycle) {
        return lifecycles.add(lifecycle);
    }

    @Override
    public boolean unRegisterLifecycle(Lifecycle lifecycle) {
        return lifecycles.remove(lifecycle);
    }

    @Override
    public void onRegisterLifecycles() {
        mvlActivity.onRegisterLifecycles();
    }

    public final Bundle onSaveInstanceState() {
        return state;
    }

    public final void onCreate(@Nullable Bundle savedInstanceState) {
        Observable.just(savedInstanceState)
                .filter(state -> state != null)
                .subscribe(prevStateSubject);
        onRegisterLifecycles();
        CollectionFuncs.forEachType(lifecycles, CreateLifecycle.class, CreateLifecycle::onCreate);
    }
    
    public final void onStart() {
        CollectionFuncs.forEachType(lifecycles, StartLifecycle.class, StartLifecycle::onStart);
    }

    public final void onRestart() {
        CollectionFuncs.forEachType(lifecycles, StartLifecycle.class, StartLifecycle::onRestart);
    }
    
    public final void onResume() {
        CollectionFuncs.forEachType(lifecycles, ResumeLifecycle.class, ResumeLifecycle::onResume);
    }

    public final void onPause() {
        CollectionFuncs.forEachType(lifecycles, ResumeLifecycle.class,  ResumeLifecycle::onPause);
    }

    public final void onStop() {
        CollectionFuncs.forEachType(lifecycles, StartLifecycle.class, StartLifecycle::onStop);
    }
    
    public final void onDestroy() {
        CollectionFuncs.forEachType(lifecycles, CreateLifecycle.class, CreateLifecycle::onDestroy);
    }
    
    public final void onLowMemory() {
        CollectionFuncs.forEachType(lifecycles, MemoryLifecycle.class, MemoryLifecycle::onLowMemory);
    }

    public final void onTrimMemory(int level) {
        CollectionFuncs.forEachType(lifecycles, MemoryLifecycle.class, memoryLifecycle -> memoryLifecycle.onTrimMemory(level));
    }
}
