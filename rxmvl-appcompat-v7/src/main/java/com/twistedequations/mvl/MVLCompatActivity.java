package com.twistedequations.mvl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.twistedequations.mvl.internal.MVLComponentCore;
import com.twistedequations.mvl.lifecycle.Lifecycle;

import java.util.Set;

import rx.Observable;
import rx.functions.Action1;

public abstract class MVLCompatActivity extends AppCompatActivity implements MVLComponent {

    private final MVLComponentCore delegate = new MVLComponentCore(this);

    @Override
    protected final void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        delegate.onCreate(bundle);
    }

    @Override
    protected final void onStart() {
        super.onStart();
        delegate.onStart();
    }

    @Override
    protected final void onResume() {
        super.onResume();
        delegate.onResume();
    }

    @Override
    protected final void onPause() {
        super.onPause();
        delegate.onPause();
    }

    @Override
    protected final void onStop() {
        super.onStop();
        delegate.onStop();
    }

    @Override
    protected final void onDestroy() {
        super.onDestroy();
        delegate.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if(!delegate.onBack()) {
            super.onBackPressed();
        }
    }

    @Override
    public Observable<Void> backClicks() {
        return delegate.backClicks();
    }

    /**
     * @return Observable that emits the restored state or will call on complete ir no item exists
     */
    @Override
    public Observable<Bundle> getPrevState() {
        return delegate.getPrevState();
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
        delegate.updateSaveState(updateFunction);
    }

    /**
     * Subclasses should register any lifecycles here
     * <code>
     *     <pre>
     *         registerLifecycle(exampleLifecycle);
     *     </pre>
     * </code>
     */
    public void main() {

    }

    @Override
    public final void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(delegate.currentState());
    }

    @Override
    public Set<Lifecycle> getLifecycles() {
        return delegate.getLifecycles();
    }

    /**
     * To be called to register lifecycles to be tied to the activity lifecycle
     * @return true if the lifecycle was registered
     * @throws IllegalArgumentException if lifecycle is not one of CreateLifecycle,
     * StartLifecycle, ResumeLifecycle or MemoryLifecycle
     */
    @Override
    public boolean registerLifecycle(Lifecycle lifecycle) {
        return delegate.registerLifecycle(lifecycle);
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
        return delegate.unRegisterLifecycle(lifecycle);
    }
}
