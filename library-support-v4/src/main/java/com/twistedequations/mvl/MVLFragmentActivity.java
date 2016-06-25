package com.twistedequations.mvl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.twistedequations.mvl.internal.MVLActivityCore;
import com.twistedequations.mvl.lifecycle.Lifecycle;

import java.util.Set;

import rx.Observable;
import rx.functions.Action1;

public abstract class MVLFragmentActivity extends FragmentActivity  implements MVLActivity {

    private final MVLActivityCore mvlActivityCore = new MVLActivityCore(this);

    @Override
    protected final void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        mvlActivityCore.onCreate(bundle);
    }

    @Override
    protected final void onStart() {
        super.onStart();
        mvlActivityCore.onStart();
    }

    @Override
    protected final void onRestart() {
        super.onRestart();
        mvlActivityCore.onRestart();
    }

    @Override
    protected final void onResume() {
        super.onResume();
        mvlActivityCore.onResume();
    }

    @Override
    protected final void onPause() {
        super.onPause();
        mvlActivityCore.onPause();
    }

    @Override
    protected final void onStop() {
        super.onStop();
        mvlActivityCore.onStop();
    }

    @Override
    protected final void onDestroy() {
        super.onDestroy();
        mvlActivityCore.onDestroy();
    }

    @Override
    public final void onLowMemory() {
        super.onLowMemory();
        mvlActivityCore.onLowMemory();
    }

    @Override
    public final void onTrimMemory(int level) {
        super.onTrimMemory(level);
        mvlActivityCore.onTrimMemory(level);
    }

    /**
     * @return Observable that emits the restored state or will call on complete ir no item exists
     */
    @Override
    public Observable<Bundle> getPrevState() {
        return mvlActivityCore.getPrevState();
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
    @Override
    public void updateSaveState(Action1<Bundle> updateFunction) {
        mvlActivityCore.updateSaveState(updateFunction);
    }

    /**
     * Subclasses should register any lifecycles here
     * <code>
     *     <pre>
     *         registerLifecycle(exampleLifecycle);
     *     </pre>
     * </code>
     */
    public void onRegisterLifecycles() {

    }

    @Override
    public final void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(mvlActivityCore.onSaveInstanceState());
    }

    @Override
    public Set<Lifecycle> getLifecycles() {
        return mvlActivityCore.getLifecycles();
    }

    /**
     * To be called to register lifecycles to be tied to the activity lifecycle
     * @return true if the lifecycle was registered
     * @throws IllegalArgumentException if lifecycle is not one of CreateLifecycle,
     * StartLifecycle, ResumeLifecycle or MemoryLifecycle
     */
    @Override
    public boolean registerLifecycle(Lifecycle lifecycle) {
        return mvlActivityCore.registerLifecycle(lifecycle);
    }

    /**
     * Subclasses should unregister any lifecycles here
     * <code>
     *     <pre>
     *         unRegisterLifecycle(exampleLifecycle);
     *     </pre>
     * </code>
     * @return true if the lifecycle was found and removed
     */
    @Override
    public boolean unRegisterLifecycle(Lifecycle lifecycle) {
        return mvlActivityCore.unRegisterLifecycle(lifecycle);
    }
}
