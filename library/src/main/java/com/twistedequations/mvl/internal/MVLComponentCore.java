package com.twistedequations.mvl.internal;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.twistedequations.mvl.MVLComponent;
import com.twistedequations.mvl.lifecycle.CreateLifecycle;
import com.twistedequations.mvl.lifecycle.Lifecycle;
import com.twistedequations.mvl.lifecycle.ResumeLifecycle;
import com.twistedequations.mvl.lifecycle.StartLifecycle;

import java.util.HashSet;
import java.util.Set;

import rx.Observable;
import rx.functions.Action1;
import rx.subjects.ReplaySubject;

public class MVLComponentCore implements MVLComponent {
    
    private final MVLComponent mvlComponent;
    private final Bundle state = new Bundle();
    private final ReplaySubject<Bundle> prevStateSubject = ReplaySubject.create(1);
    private final Set<Lifecycle> lifecycles = new HashSet<>();
    private final RelayOnSubscribe<Void> backButtonRelay = new RelayOnSubscribe<>();

    public MVLComponentCore(MVLComponent mvlComponent) {
        this.mvlComponent = mvlComponent;
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
    public void main() {
        mvlComponent.main();
    }

    @Override
    public Observable<Void> backClicks() {
        return Observable.create(backButtonRelay);
    }

    public final Bundle currentState() {
        return state;
    }

    public final void onCreate(@Nullable Bundle savedInstanceState) {
        Observable.just(savedInstanceState)
                .filter(state -> state != null)
                .subscribe(prevStateSubject);
        main();
        CollectionFuncs.forEachType(lifecycles, CreateLifecycle.class, CreateLifecycle::onCreate);
    }
    
    public final void onStart() {
        CollectionFuncs.forEachType(lifecycles, StartLifecycle.class, StartLifecycle::onStart);
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

    /**
     * Return true if there is a subscriber
     */
    public final boolean onBack() {
        return backButtonRelay.sendEvent(null);
    }
}
