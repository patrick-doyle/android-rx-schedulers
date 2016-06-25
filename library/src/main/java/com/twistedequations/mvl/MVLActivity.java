package com.twistedequations.mvl;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.twistedequations.mvl.lifecycle.CreateLifecycle;
import com.twistedequations.mvl.lifecycle.Lifecycle;
import com.twistedequations.mvl.lifecycle.MemoryLifecycle;
import com.twistedequations.mvl.lifecycle.ResumeLifecycle;
import com.twistedequations.mvl.lifecycle.StartLifecycle;
import com.twistedequations.mvl.internal.CollectionFuncs;

import java.util.HashSet;
import java.util.Set;

import rx.Observable;
import rx.functions.Action1;
import rx.subjects.ReplaySubject;

public abstract class MVLActivity extends Activity {

    private final Bundle state = new Bundle();
    private final ReplaySubject<Bundle> prevStateSubject = ReplaySubject.create(1);
    private final Set<Lifecycle> lifecycles = new HashSet<>();

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Observable.just(savedInstanceState)
                .filter(state -> state != null)
                .subscribe(prevStateSubject);
        onRegisterLifecycles();
        CollectionFuncs.forEachType(lifecycles, CreateLifecycle.class, CreateLifecycle::onCreate);
    }

    @Override
    protected final void onStart() {
        super.onStart();
        CollectionFuncs.forEachType(lifecycles, StartLifecycle.class, StartLifecycle::onStart);
    }

    @Override
    protected final void onRestart() {
        super.onRestart();
        CollectionFuncs.forEachType(lifecycles, StartLifecycle.class, StartLifecycle::onRestart);
    }

    @Override
    protected final void onResume() {
        super.onResume();
        CollectionFuncs.forEachType(lifecycles, ResumeLifecycle.class, ResumeLifecycle::onResume);
    }

    @Override
    protected final void onPause() {
        super.onPause();
        CollectionFuncs.forEachType(lifecycles, ResumeLifecycle.class,  ResumeLifecycle::onPause);
    }

    @Override
    protected final void onStop() {
        super.onStop();
        CollectionFuncs.forEachType(lifecycles, StartLifecycle.class, StartLifecycle::onStop);
    }

    @Override
    protected final void onDestroy() {
        super.onDestroy();
        CollectionFuncs.forEachType(lifecycles, CreateLifecycle.class, CreateLifecycle::onDestroy);
    }

    @Override
    public final void onLowMemory() {
        super.onLowMemory();
        CollectionFuncs.forEachType(lifecycles, MemoryLifecycle.class, MemoryLifecycle::onLowMemory);
    }

    @Override
    public final void onTrimMemory(int level) {
        super.onTrimMemory(level);
        CollectionFuncs.forEachType(lifecycles, MemoryLifecycle.class, memoryLifecycle -> memoryLifecycle.onTrimMemory(level));
    }

    /**
     * @return Observable that emits the restored state or will call on complete ir no item exists
     */
    public Observable<Bundle> getPrevState() {
        return prevStateSubject.asObservable().take(1);
    }

    /**
     * Updates the state to be saved on rotation
     * <code>
     *     <pre>
     *         updateSaveState(bundle -> bundle.putParcelable("data_key", data))
     *     </pre>
     * </code>
     * @param updateFunction the function called into order to save the state
     */
    public void updateSaveState(Action1<Bundle> updateFunction) {
        updateFunction.call(state);
    }

    /**
     * Subclasses should register any lifecycles here
     * <code>
     *     <pre>
     *         registerLifecycle(exampleLifecycle);
     *     </pre>
     * </code>
     */
    protected void onRegisterLifecycles() {

    }

    @Override
    public final void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putAll(state);
    }

    public Set<Lifecycle> getLifecycles() {
        return lifecycles;
    }

    /**
     * To be called to register lifecycles to be tied to the activity lifecycle
     * @return true if the lifecycle was registered
     * @throws IllegalArgumentException if lifecycle is not one of CreateLifecycle,
     * StartLifecycle, ResumeLifecycle or MemoryLifecycle
     */
    public boolean registerLifecycle(Lifecycle lifecycle) {
        return lifecycles.add(lifecycle);
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    /**
     * Subclasses should unregister any lifecycles here
     * <code>
     *     <pre>
     *         unRegisterLifecycle(exampleLifecycle);
     *     </pre>
     * </code>
     * @return true if the lifecycle was found and removed
     */
    public boolean unRegisterLifecycle(Lifecycle lifecycle) {
        return lifecycles.remove(lifecycle);
    }
}
